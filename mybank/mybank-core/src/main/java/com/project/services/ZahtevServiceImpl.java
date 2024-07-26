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
import com.project.exceptions.EntityNotFoundException;
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
    public ZahtevRequestDto openRequest(ZahtevRequestDto zahtevRequestDto, String email) {

        Klijent klijent = klijentRepository.findOneByEmail(email);

        if (klijent == null) {
            throw new EntityNotFoundException("Client with the given email does not exist: " + email);
        }

        Zahtev zahtev = modelMapper.map(zahtevRequestDto, Zahtev.class);

        if (zahtev.getTipRacuna().equals(TipRacuna.TRANSAKCIONI)) {
            zahtev.setKreditniLimit(0);
        }

        zahtev.setTipZahteva(TipZahteva.OTVARANJE);
        zahtev.setStatusZahteva(StatusZahteva.KREIRAN);
        zahtev.setPodnosilacZahteva(klijent);
        zahtev.setDatumZahteva(LocalDate.now());
        zahtev.setDatumOdluke(null);

        zahtevRepository.save(zahtev);

        return zahtevRequestDto;
    }

    //Klijent pravi zahtev za zatvaranje racuna
    //TODO
    //Zahtev ne moze da se napravi ako iznos na racunu nije 0
    @Override
    public ZahtevResponseDto closeRequest(String email, String brojRacuna) {
        Klijent klijent = klijentRepository.findOneByEmail(email);
        Racun racun = racunRepository.findByBrojRacuna(brojRacuna);
        Zahtev zahtev = new Zahtev();


        if (klijent == null) {
            throw new EntityNotFoundException("Client with the given email does not exist: " + email);
        }

        if (racun == null) {
            throw new EntityNotFoundException("Racun with the given broj racuna does not exist: " + brojRacuna);
        }

        if (racun.getTrenutniIznos() != 0) {
            return null;
        }

        zahtev.setTipZahteva(TipZahteva.ZATVARANJE);
        zahtev.setTipRacuna(racun.getTipRacuna());
        zahtev.setValuta(racun.getValuta());
        zahtev.setKreditniLimit(racun.getKreditniLimit());
        zahtev.setBrojRacuna(brojRacuna);
        zahtev.setStatusZahteva(StatusZahteva.KREIRAN);
        zahtev.setPodnosilacZahteva(klijent);
        zahtev.setOperaterBanke(null);
        zahtev.setDatumZahteva(LocalDate.now());
        zahtev.setDatumOdluke(null);

        zahtevRepository.save(zahtev);

        ZahtevResponseDto zahtevDto = modelMapper.map(zahtev, ZahtevResponseDto.class);

        return zahtevDto;
    }

    //Operater donosi odluku o zahtevu za zatvaranje ili otvaranje racuna
    @Override
    public ZahtevResponseDto decide(String brojRacuna, String decision) {
        Zahtev zahtev = zahtevRepository.findByBrojRacuna(brojRacuna);
        ZahtevResponseDto zahtevResponseDto = modelMapper.map(zahtev, ZahtevResponseDto.class);

        if (zahtev == null) {
            throw new EntityNotFoundException("Zahtev with the given broj racuna does not exist: " + brojRacuna);
        }

        //Ukoliko je zahtev odbijen, samo se postavlja vreme odluke i menja se status zahteva na "ODBIJEN"
        if (Objects.equals(decision, "ODBIJEN")) {
            zahtev.setDatumOdluke(LocalDate.now());
            zahtev.setStatusZahteva(StatusZahteva.valueOf(decision));

            zahtevRepository.save(zahtev);

        //Ukoliko je zahtev odobren onda se gleda da li je zahtev bio za zatvaranje ili otvaranje racuna
        } else if (Objects.equals(decision, "ODOBREN")) {

            //Ukoliko je zahtev za otvaranje odobren, zahtev se menja i pravi se
            //novi racun sa podacima iz zahteva
            if (zahtev.getTipZahteva().equals(TipZahteva.OTVARANJE)) {
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

                //Ukoliko je zahtev za zatvaranje odobren, zahtev se menja a racun
                //koji se zatvara menja status u "ZATVOREN"
            } else if (zahtev.getTipZahteva().equals(TipZahteva.ZATVARANJE)) {
                zahtev.setDatumOdluke(LocalDate.now());
                zahtev.setStatusZahteva(StatusZahteva.valueOf(decision));

                //TODO
                //Ukoliko je zahtev za zatvaranje racuna odobren, racun bi trebalo da promeni status

                Racun racun = racunRepository.findByBrojRacuna(brojRacuna);

                racun.setStatusRacuna(StatusRacuna.ZATVOREN);
                racunRepository.save(racun);
            }
        }

        return zahtevResponseDto;
    }
}
