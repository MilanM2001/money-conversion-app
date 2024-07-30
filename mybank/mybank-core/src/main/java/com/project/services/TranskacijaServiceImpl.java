package com.project.services;

import com.project.domain.entities.Klijent;
import com.project.domain.entities.Racun;
import com.project.domain.entities.Transakcija;
import com.project.domain.repositoryinterfaces.KlijentRepository;
import com.project.domain.repositoryinterfaces.RacunRepository;
import com.project.domain.repositoryinterfaces.TranskacijaRepository;
import com.project.dtos.transakcija.TransakcijaRequestDto;
import com.project.dtos.transakcija.TransakcijaResponseDto;
import com.project.enums.StatusRacuna;
import com.project.enums.StatusTransakcije;
import com.project.enums.TipTransakcije;
import com.project.exceptions.EntityNotFoundException;
import com.project.serviceinterfaces.TransakcijaService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TranskacijaServiceImpl implements TransakcijaService {

    KlijentRepository klijentRepository;
    RacunRepository racunRepository;
    TranskacijaRepository transkacijaRepository;
    ModelMapper modelMapper;

    @Autowired
    public TranskacijaServiceImpl(TranskacijaRepository transkacijaRepository, ModelMapper modelMapper, KlijentRepository klijentRepository, RacunRepository racunRepository) {
        this.transkacijaRepository = transkacijaRepository;
        this.modelMapper = modelMapper;
        this.klijentRepository = klijentRepository;
        this.racunRepository = racunRepository;
    }

    @Override
    public List<TransakcijaResponseDto> findAll() {
        List<Transakcija> transakcije = transkacijaRepository.findAll();
        List<TransakcijaResponseDto> transakcijeDto = modelMapper.map(transakcije, new TypeToken<List<TransakcijaResponseDto>>() {}.getType());

        return transakcijeDto;
    }

    @Override
    public List<TransakcijaResponseDto> findAllByKlijentEmail(String email) {
        if(klijentRepository.findOneByEmail(email) == null) {
            throw new EntityNotFoundException("Cannot find klijent with the given email: " + email);
        }

        List<Transakcija> transakcije = transkacijaRepository.findAllByKlijentEmail(email);
        List<TransakcijaResponseDto> transkacijeDto = modelMapper.map(transakcije, new TypeToken<List<TransakcijaResponseDto>>() {}.getType());

        return transkacijeDto;
    }

    @Override
    public List<TransakcijaResponseDto> findAllByKlijentEmail(String email, String sortBy) {
        Sort sort = getSortByParameter(sortBy);
        Klijent klijent = klijentRepository.findOneByEmail(email);
        List<Transakcija> transakcije = transkacijaRepository.findAllByKlijentEmail(klijent, sort);
        List<TransakcijaResponseDto> transakcijeDto = modelMapper.map(transakcije, new TypeToken<List<TransakcijaResponseDto>>() {}.getType());

        return transakcijeDto;
    }

    //TODO
    //Proveriti i uporediti valute racuna uplate i transakcije i onda kasnije konvertovati ako treba
    @Override
    public TransakcijaRequestDto deposit(TransakcijaRequestDto transakcijaDto, String klijentEmail, String brojRacunaUplate) {
        Racun racunUplate = racunRepository.findByBrojRacuna(brojRacunaUplate);
        Klijent klijent = klijentRepository.findOneByEmail(klijentEmail);

        if (racunUplate == null) {
            throw new EntityNotFoundException("Racun with the provided broj does not exist: " + brojRacunaUplate);
        } else if (klijent == null) {
            throw new EntityNotFoundException("Klijent with the provided email does not exist: " + klijentEmail);
        }

        if (!racunUplate.getStatusRacuna().equals(StatusRacuna.AKTIVAN)) {
            return null;
        }

        Transakcija transakcija = new Transakcija();

        transakcija.setTipTransakcije(TipTransakcije.UPLATA);
        transakcija.setIznos(transakcijaDto.getIznos());
        transakcija.setValuta(transakcijaDto.getValuta());
        transakcija.setRacunUplate(racunUplate);
        transakcija.setRacunIsplate(null);
        transakcija.setKoeficijentKonverzije(0);
        transakcija.setDatumTransakcije(LocalDate.now());
        transakcija.setKlijentEmail(klijent);
        transakcija.setStatusTransakcije(StatusTransakcije.REALIZOVANA);

        transkacijaRepository.save(transakcija);

        int noviIznos = racunUplate.getTrenutniIznos() + transakcijaDto.getIznos();

        racunUplate.setTrenutniIznos(noviIznos);
        racunUplate.setDatumPoslednjePromene(LocalDate.now());

        racunRepository.save(racunUplate);

        return transakcijaDto;
    }

    public TransakcijaRequestDto withdraw(TransakcijaRequestDto transakcijaDto, String klijentEmail, String brojRacunaIsplate) {
        Racun racunIsplate = racunRepository.findByBrojRacuna(brojRacunaIsplate);
        Klijent klijent = klijentRepository.findOneByEmail(klijentEmail);

        if (racunIsplate == null) {
            throw new EntityNotFoundException("Racun with the provided broj does not exist: " + brojRacunaIsplate);
        } else if (klijent == null) {
            throw new EntityNotFoundException("Klijent with the provided email does not exist: " + klijentEmail);
        }

        if (!racunIsplate.getStatusRacuna().equals(StatusRacuna.AKTIVAN)) {
            return null;
        }

        Transakcija transakcija = new Transakcija();

        transakcija.setTipTransakcije(TipTransakcije.ISPLATA);
        transakcija.setIznos(transakcijaDto.getIznos());
        transakcija.setValuta(transakcijaDto.getValuta());
        transakcija.setRacunUplate(null);
        transakcija.setRacunIsplate(racunIsplate);
        transakcija.setKoeficijentKonverzije(0);
        transakcija.setDatumTransakcije(LocalDate.now());
        transakcija.setKlijentEmail(klijent);
        transakcija.setStatusTransakcije(StatusTransakcije.REALIZOVANA);

        transkacijaRepository.save(transakcija);

        int noviIznos = racunIsplate.getTrenutniIznos() - transakcijaDto.getIznos();

        if (noviIznos < 0) {
            return null;
        }

        racunIsplate.setTrenutniIznos(noviIznos);
        racunIsplate.setDatumPoslednjePromene(LocalDate.now());

        racunRepository.save(racunIsplate);

        return transakcijaDto;
    }

    private Sort getSortByParameter(String sortBy) {
        switch (sortBy) {
            case "racunUplate":
                return Sort.by(Sort.Direction.ASC, "racunUplate.brojRacuna");
            case "racunIsplate":
                return Sort.by(Sort.Direction.ASC, "racunIsplate.brojRacuna");
            case "datumTransakcije":
                return Sort.by(Sort.Direction.ASC, "datumTransakcije");
            case "statusTransakcije":
                return Sort.by(Sort.Direction.ASC, "statusTransakcije");
            default:
                return Sort.by(Sort.Direction.ASC, "datumTransakcije"); // Default sorting
        }
    }
}
