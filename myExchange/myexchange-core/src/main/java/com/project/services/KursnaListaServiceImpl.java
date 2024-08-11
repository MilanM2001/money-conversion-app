package com.project.services;

import com.project.domain.entities.Kurs;
import com.project.domain.entities.KursnaLista;
import com.project.domain.repositoryinterfaces.KursRepository;
import com.project.domain.repositoryinterfaces.KursnaListaRepository;
import com.project.dtos.konverzija.KonverzijaRequestDto;
import com.project.dtos.konverzija.KonverzijaResponseDto;
import com.project.dtos.kursnalista.KursnaListaRequestDto;
import com.project.dtos.kursnalista.KursnaListaResponseDto;
import com.project.enums.StatusKursneListe;
import com.project.serviceinterface.KursnaListaService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class KursnaListaServiceImpl implements KursnaListaService {

    KursnaListaRepository kursnaListaRepository;
    KursRepository kursRepository;
    ModelMapper modelMapper;

    @Autowired
    public KursnaListaServiceImpl(KursnaListaRepository kursnaListaRepository, KursRepository kursRepository, ModelMapper modelMapper) {
        this.kursnaListaRepository = kursnaListaRepository;
        this.kursRepository = kursRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<KursnaListaResponseDto> findAll() {
        List<KursnaLista> kursneListe = kursnaListaRepository.findAll();
        List<KursnaListaResponseDto> kursneListeDto = modelMapper.map(kursneListe, new TypeToken<List<KursnaListaResponseDto>>() {}.getType());

        return kursneListeDto;
    }

    @Override
    public KursnaListaResponseDto findById(Integer id) {
        KursnaLista kursnaLista = kursnaListaRepository.findById(id).orElse(null);

        if (kursnaLista == null) {
            throw new EntityNotFoundException("Cannot find kursna lista with id: " + id);
        }

        KursnaListaResponseDto kursnaListaDto = modelMapper.map(kursnaLista, KursnaListaResponseDto.class);

        return kursnaListaDto;
    }

    @Override
    public KursnaListaRequestDto create(KursnaListaRequestDto kursnaListaRequestDto) {
        KursnaLista kursnaLista = new KursnaLista();

        modelMapper.map(kursnaListaRequestDto, kursnaLista);
        kursnaLista.setDatum(LocalDate.now());
        kursnaLista.setStatus(StatusKursneListe.KREIRANA);

        kursnaListaRepository.save(kursnaLista);

        return kursnaListaRequestDto;
    }

    @Override
    public KursnaListaResponseDto activate(Integer id) {
        KursnaLista kursnaLista = kursnaListaRepository.findById(id).orElse(null);
        Optional<KursnaLista> activeKursnaLista = kursnaListaRepository.findActive();

        if (kursnaLista == null) {
            throw new EntityNotFoundException("Cannot find kursna lista with id: " + id);
        }

        if (activeKursnaLista.isPresent()) {
            throw new EntityExistsException("An activated kursna lista with id: " + id + " already exists");
        }

        kursnaLista.setStatus(StatusKursneListe.AKTIVNA);
        kursnaListaRepository.save(kursnaLista);
        KursnaListaResponseDto kursnaListaDto = modelMapper.map(kursnaLista, KursnaListaResponseDto.class);

        return kursnaListaDto;
    }

    @Override
    public KursnaListaResponseDto deactivate(Integer id) {
        KursnaLista kursnaLista = kursnaListaRepository.findById(id).orElse(null);

        if (kursnaLista == null) {
            throw new EntityNotFoundException("Cannot find kursna lista with id: " + id);
        }

        kursnaLista.setStatus(StatusKursneListe.NEAKTIVNA);
        kursnaListaRepository.save(kursnaLista);
        KursnaListaResponseDto kursnaListaDto = modelMapper.map(kursnaLista, KursnaListaResponseDto.class);

        return kursnaListaDto;
    }

    @Override
    public KonverzijaResponseDto exchange(KonverzijaRequestDto konverzijaRequestDto) {

        double iznosTransakcije = konverzijaRequestDto.getIznosTransakcije();
        String valutaTransakcije = konverzijaRequestDto.getValutaTransakcije().toString();
        String valutaRacunaUplate = konverzijaRequestDto.getValutaRacunaUplate().toString();

        KonverzijaResponseDto konverzijaResponseDto = konverzija(iznosTransakcije, valutaTransakcije, valutaRacunaUplate);

        return konverzijaResponseDto;
    }

    //Valuta transkacije je ista kao valuta racuna isplate, valuta racuna uplate je drugacija i u nju konvertujemo
    private KonverzijaResponseDto konverzija(double iznosTransakcije, String valutaTransakcije, String valutaRacunaUplate) {
        KonverzijaResponseDto konverzijaResponseDto = new KonverzijaResponseDto();
        double konvertovaniIznos;

        KursnaLista kursnaLista = kursnaListaRepository.findActive().orElseThrow(() ->
                new EntityNotFoundException("Cannot find an active kursna lista"));

        // If the transaction currency is RSD
        if (Objects.equals(valutaTransakcije, "RSD")) {
            Kurs kursUplate = kursRepository.findByKursnaListaAndValuta(kursnaLista.getId(), valutaRacunaUplate);

            if (kursUplate == null) {
                throw new EntityNotFoundException("Cannot find a kurs for valuta: " + valutaRacunaUplate);
            }

            // Convert RSD to EUR/USD
            konvertovaniIznos = iznosTransakcije / kursUplate.getProdajniKurs();

        } else if (Objects.equals(valutaRacunaUplate, "RSD")) {
            // If the account currency is RSD
            Kurs kursTransakcije = kursRepository.findByKursnaListaAndValuta(kursnaLista.getId(), valutaTransakcije);

            if (kursTransakcije == null) {
                throw new EntityNotFoundException("Cannot find a kurs for valuta: " + valutaTransakcije);
            }

            // Convert EUR/USD to RSD
            konvertovaniIznos = iznosTransakcije * kursTransakcije.getKupovniKurs();

        } else {
            // Converting EUR to USD or USD to EUR via RSD as an intermediary
            Kurs kursTransakcije = kursRepository.findByKursnaListaAndValuta(kursnaLista.getId(), valutaTransakcije);

            if (kursTransakcije == null) {
                throw new EntityNotFoundException("Cannot find a kurs for valuta: " + valutaTransakcije);
            }

            Kurs kursUplate = kursRepository.findByKursnaListaAndValuta(kursnaLista.getId(), valutaRacunaUplate);

            if (kursUplate == null) {
                throw new EntityNotFoundException("Cannot find a kurs for valuta: " + valutaRacunaUplate);
            }

            double iznosRSD = iznosTransakcije * kursTransakcije.getKupovniKurs();

            konvertovaniIznos = iznosRSD / kursUplate.getProdajniKurs();
        }

        konverzijaResponseDto.setKonvertovaniIznos(konvertovaniIznos);
        konverzijaResponseDto.setKoeficijent(10); // Set the coefficient as needed

        return konverzijaResponseDto;
    }

}
