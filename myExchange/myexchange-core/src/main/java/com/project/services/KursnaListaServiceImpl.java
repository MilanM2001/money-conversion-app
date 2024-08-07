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
import com.project.enums.Valuta;
import com.project.exceptions.EntityAlreadyExistsException;
import com.project.exceptions.EntityNotFoundException;
import com.project.serviceinterface.KursnaListaService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
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
            throw new EntityAlreadyExistsException("An activated kursna lista with id: " + id + " already exists");
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
        KonverzijaResponseDto konverzijaResponseDto = konverzija(iznosTransakcije, konverzijaRequestDto.getValutaTransakcije(), konverzijaRequestDto.getValutaRacunaUplate());

        return konverzijaResponseDto;
    }

    //Valuta transkacije je ista kao valuta racuna isplate, valuta racuna uplate je drugacija i u nju konvertujemo
    private KonverzijaResponseDto konverzija(double iznosTransakcije, Valuta valutaTransakcije, Valuta valutaRacunaUplate) {
        KonverzijaResponseDto konverzijaResponseDto = new KonverzijaResponseDto();
        double konvertovaniIznos = 0.0;

        KursnaLista kursnaLista = kursnaListaRepository.findActive().orElse(null);

        if (kursnaLista == null) {
            throw new EntityNotFoundException("Cannot find an active kursna lista");
        }


        //RSD --> EUR/USD
        //Ako je valuta transkacije RSD onda je valuta racuna uplate EUR/USD
        if (valutaTransakcije == Valuta.RSD) {
            //Kurs se nadje za aktivnu kursnu listu i za valutu racuna uplate(onaj u koji se radi konverzija)
            Kurs kurs = kursRepository.findByKursnaListaAndValuta(kursnaLista.getId(), valutaRacunaUplate);

            if (kurs == null) {
                throw new EntityNotFoundException("Cannot find a kurs with kursna lista id: " + kursnaLista.getId() + " and valuta: " + valutaRacunaUplate);
            }

            konvertovaniIznos = iznosTransakcije / kurs.getProdajniKurs();

        //EUR/USD --> RSD
        //Ako je valuta racuna uplate RSD onda je valuta transakcije EUR/USD
        } else if (valutaRacunaUplate == Valuta.RSD) {
            Kurs kursTransakcije = kursRepository.findByKursnaListaAndValuta(kursnaLista.getId(), valutaTransakcije);

            if (kursTransakcije == null) {
                throw new EntityNotFoundException("Cannot find a kurs with kursna lista id: " + kursnaLista.getId() + " and valuta: " + valutaRacunaUplate);
            }

            konvertovaniIznos = iznosTransakcije * kursTransakcije.getKupovniKurs();
        } else {
            // USD to EUR or EUR to USD via RSD
            Kurs kursTransakcije = kursRepository.findByKursnaListaAndValuta(kursnaLista.getId(), valutaTransakcije);

            if (kursTransakcije == null) {
                throw new EntityNotFoundException("Cannot find a kurs for the transaction currency");
            }

            double iznosRSD = iznosTransakcije * kursTransakcije.getKupovniKurs();
            Kurs kursUplate = kursRepository.findByKursnaListaAndValuta(kursnaLista.getId(), valutaRacunaUplate);

            if (kursUplate == null) {
                throw new EntityNotFoundException("Cannot find a kurs for the target currency");
            }

            konvertovaniIznos = iznosRSD / kursUplate.getProdajniKurs();
        }

        konverzijaResponseDto.setKonvertovaniIznos(konvertovaniIznos);
        konverzijaResponseDto.setKoeficijent(10);

        return konverzijaResponseDto;
    }


}
