package com.project.services;

import com.project.domain.entities.Klijent;
import com.project.domain.entities.Zahtev;
import com.project.domain.repositoryinterfaces.KlijentRepository;
import com.project.domain.repositoryinterfaces.ZahtevRepository;
import com.project.dtos.zahtev.PostZahtevDto;
import com.project.dtos.zahtev.ZahtevDto;
import com.project.enums.StatusZahteva;
import com.project.enums.TipZahteva;
import com.project.serviceinterfaces.ZahtevService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class ZahtevServiceImpl implements ZahtevService {


    ZahtevRepository zahtevRepository;
    ModelMapper modelMapper;
    KlijentRepository klijentRepository;

    @Autowired
    public ZahtevServiceImpl(ZahtevRepository zahtevRepository, ModelMapper modelMapper, KlijentRepository klijentRepository) {
        this.zahtevRepository = zahtevRepository;
        this.modelMapper = modelMapper;
        this.klijentRepository = klijentRepository;
    }


    @Override
    public List<ZahtevDto> findAll() {
        List<Zahtev> zahtevi = zahtevRepository.findAll();
        List<ZahtevDto> zahteviDto = modelMapper.map(zahtevi, new TypeToken<List<ZahtevDto>>() {}.getType());

        return zahteviDto;
    }

    @Override
    public List<ZahtevDto> findByClientsEmail(String email) {
        List<Zahtev> zahtevi = zahtevRepository.findByClientEmail(email);
        List<ZahtevDto> zahteviDto = modelMapper.map(zahtevi, new TypeToken<List<ZahtevDto>>() {}.getType());

        return zahteviDto;
    }

    @Override
    public PostZahtevDto create(PostZahtevDto postZahtevDto, String email) {

        Klijent klijent = klijentRepository.findOneByEmail(email);

        if (klijent == null) {
            return null;
        }

        Zahtev zahtev = modelMapper.map(postZahtevDto, Zahtev.class);

        zahtev.setTipZahteva(TipZahteva.OTVARANJE);
        zahtev.setKreditniLimit(0);
        zahtev.setStatusZahteva(StatusZahteva.KREIRAN);
        zahtev.setPodnosilacZahteva(klijent);
        zahtev.setDatumZahteva(LocalDate.now());
        zahtev.setDatumOdluke(null);

        zahtevRepository.save(zahtev);

        return postZahtevDto;
    }

    @Override
    public ZahtevDto decide(String brojRacuna, String decision) {
        Zahtev zahtev = zahtevRepository.findByBrojRacuna(brojRacuna);
        ZahtevDto zahtevDto = modelMapper.map(zahtev, ZahtevDto.class);

        if (zahtev == null) {
            return null;
        }

        if (Objects.equals(decision, "ODBIJEN")) {
            zahtev.setDatumOdluke(LocalDate.now());
            zahtev.setStatusZahteva(StatusZahteva.valueOf(decision));
            zahtevRepository.save(zahtev);
            //TODO
            //Dodati logiku da se kreira racun nakon sto je odobren i sacuvati
        } else if (Objects.equals(decision, "ODOBREN")) {
            zahtev.setDatumOdluke(LocalDate.now());
            zahtev.setStatusZahteva(StatusZahteva.valueOf(decision));
            zahtevRepository.save(zahtev);
        }

        return zahtevDto;
    }
}
