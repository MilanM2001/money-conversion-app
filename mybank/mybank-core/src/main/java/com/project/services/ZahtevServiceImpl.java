package com.project.services;

import com.project.domain.entities.Klijent;
import com.project.domain.entities.Racun;
import com.project.domain.entities.Zahtev;
import com.project.domain.repositoryinterfaces.KlijentRepository;
import com.project.domain.repositoryinterfaces.RacunRepository;
import com.project.domain.repositoryinterfaces.ZahtevRepository;
import com.project.dtos.zahtev.ZahtevRequestDto;
import com.project.dtos.zahtev.ZahtevResponseDto;
import com.project.enums.StatusRacuna;
import com.project.enums.StatusZahteva;
import com.project.enums.TipRacuna;
import com.project.enums.TipZahteva;
import com.project.serviceinterfaces.ZahtevService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class ZahtevServiceImpl implements ZahtevService {


    ZahtevRepository zahtevRepository;
    ModelMapper modelMapper;
    KlijentRepository klijentRepository;
    RacunRepository racunRepository;


    @Autowired
    public ZahtevServiceImpl(ZahtevRepository zahtevRepository, ModelMapper modelMapper, KlijentRepository klijentRepository, RacunRepository racunRepository) {
        this.zahtevRepository = zahtevRepository;
        this.modelMapper = modelMapper;
        this.klijentRepository = klijentRepository;
        this.racunRepository = racunRepository;
    }


    @Override
    public List<ZahtevResponseDto> findAll() {
        List<Zahtev> zahtevi = zahtevRepository.findAll();
        List<ZahtevResponseDto> zahteviDto = modelMapper.map(zahtevi, new TypeToken<List<ZahtevResponseDto>>() {}.getType());

        return zahteviDto;
    }

    @Override
    public List<ZahtevResponseDto> findByClientsEmail(String email) {
        List<Zahtev> zahtevi = zahtevRepository.findByClientEmail(email);
        List<ZahtevResponseDto> zahteviDto = modelMapper.map(zahtevi, new TypeToken<List<ZahtevResponseDto>>() {}.getType());

        return zahteviDto;
    }

    @Override
    public ZahtevRequestDto create(ZahtevRequestDto zahtevRequestDto, String email) {

        Klijent klijent = klijentRepository.findOneByEmail(email);

        if (klijent == null) {
            return null;
        }

        Zahtev zahtev = modelMapper.map(zahtevRequestDto, Zahtev.class);

        zahtev.setTipZahteva(TipZahteva.OTVARANJE);
        zahtev.setKreditniLimit(0);
        zahtev.setStatusZahteva(StatusZahteva.KREIRAN);
        zahtev.setPodnosilacZahteva(klijent);
        zahtev.setDatumZahteva(LocalDate.now());
        zahtev.setDatumOdluke(null);

        zahtevRepository.save(zahtev);

        return zahtevRequestDto;
    }

    @Override
    public ZahtevResponseDto decide(String brojRacuna, String decision) {
        Zahtev zahtev = zahtevRepository.findByBrojRacuna(brojRacuna);
        ZahtevResponseDto zahtevResponseDto = modelMapper.map(zahtev, ZahtevResponseDto.class);

        if (zahtev == null) {
            return null;
        }

        if (Objects.equals(decision, "ODBIJEN")) {
            zahtev.setDatumOdluke(LocalDate.now());
            zahtev.setStatusZahteva(StatusZahteva.valueOf(decision));

            zahtevRepository.save(zahtev);
        } else if (Objects.equals(decision, "ODOBREN")) {
            zahtev.setDatumOdluke(LocalDate.now());
            zahtev.setStatusZahteva(StatusZahteva.valueOf(decision));

            zahtevRepository.save(zahtev);

            Racun racun = new Racun();

            racun.setNazivBanke("Banka Intesa");
            racun.setTipRacuna(zahtev.getTipRacuna());
            racun.setBrojRacuna(zahtev.getBrojRacuna());
            racun.setTrenutniIznos(0);

            if (racun.getTipRacuna().equals(TipRacuna.TRANSAKCIONI)) {
                racun.setKreditniLimit(0);
            } else if (racun.getTipRacuna().equals(TipRacuna.KREDITNI)) {
                racun.setKreditniLimit(zahtev.getKreditniLimit());
            }

            racun.setValuta(zahtev.getValuta());
            racun.setStatusRacuna(StatusRacuna.AKTIVAN);
            racun.setDatumKreiranja(LocalDate.now());
            racun.setDatumPoslednjePromene(LocalDate.now());
            racun.setVersion("0");
            racun.setKlijent(zahtev.getPodnosilacZahteva());

            racunRepository.save(racun);
        }

        return zahtevResponseDto;
    }
}
