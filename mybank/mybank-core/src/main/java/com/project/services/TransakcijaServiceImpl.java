package com.project.services;

import com.project.domain.entities.Klijent;
import com.project.domain.entities.Racun;
import com.project.domain.entities.Transakcija;
import com.project.domain.repositoryinterfaces.KlijentRepository;
import com.project.domain.repositoryinterfaces.RacunRepository;
import com.project.domain.repositoryinterfaces.TransakcijaRepository;
import com.project.dtos.konverzija.KonverzijaRequestDto;
import com.project.dtos.konverzija.KonverzijaResponseDto;
import com.project.dtos.transakcija.TransakcijaRequestDto;
import com.project.dtos.transakcija.TransakcijaResponseDto;
import com.project.enums.StatusRacuna;
import com.project.enums.StatusTransakcije;
import com.project.enums.TipTransakcije;
import com.project.exceptions.CurrencyException;
import com.project.exceptions.EntityNotFoundException;
import com.project.exceptions.NegativeBalanceException;
import com.project.exceptions.RacunClosedException;
import com.project.serviceinterfaces.TransakcijaService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransakcijaServiceImpl implements TransakcijaService {

    private final RestClient restClient;
    KlijentRepository klijentRepository;
    RacunRepository racunRepository;
    TransakcijaRepository transakcijaRepository;
    ModelMapper modelMapper;
    @Value("${MY_EXCHANGE_URL}")
    private String MyExchangeURL;

    @Autowired
    public TransakcijaServiceImpl(TransakcijaRepository transakcijaRepository, ModelMapper modelMapper, KlijentRepository klijentRepository, RacunRepository racunRepository, RestClient restClient) {
        this.transakcijaRepository = transakcijaRepository;
        this.modelMapper = modelMapper;
        this.klijentRepository = klijentRepository;
        this.racunRepository = racunRepository;
        this.restClient = restClient;
    }

    @Override
    public List<TransakcijaResponseDto> findAll() {
        List<Transakcija> transakcije = transakcijaRepository.findAll();
        List<TransakcijaResponseDto> transakcijeDto = modelMapper.map(transakcije, new TypeToken<List<TransakcijaResponseDto>>() {}.getType());

        return transakcijeDto;
    }

    @Override
    public List<TransakcijaResponseDto> findAllByKlijentEmail(String email) {
        if(klijentRepository.findOneByEmail(email) == null) {
            throw new EntityNotFoundException("Cannot find klijent with the given email: " + email);
        }

        List<Transakcija> transakcije = transakcijaRepository.findAllByKlijentEmail(email);
        List<TransakcijaResponseDto> transkacijeDto = modelMapper.map(transakcije, new TypeToken<List<TransakcijaResponseDto>>() {}.getType());

        return transkacijeDto;
    }

    @Override
    public List<TransakcijaResponseDto> findAllByKlijentEmail(String email, String sortBy) {
        Sort sort = getSortByParameter(sortBy);
        Klijent klijent = klijentRepository.findOneByEmail(email);
        List<Transakcija> transakcije = transakcijaRepository.findAllByKlijentEmail(klijent, sort);
        List<TransakcijaResponseDto> transakcijeDto = modelMapper.map(transakcije, new TypeToken<List<TransakcijaResponseDto>>() {}.getType());

        return transakcijeDto;
    }



    //Glavna metoda za transkaciju, na osnovu tipa transkacije radi se dalja logika
    @Override
    public TransakcijaResponseDto transakcija(TransakcijaRequestDto transakcijaRequestDto, String klijentEmail, String brojRacunaUplate, String brojRacunaIsplate) {
        Klijent klijent = klijentRepository.findOneByEmail(klijentEmail);

        if (klijent == null) {
            throw new EntityNotFoundException("Klijent with the provided email does not exist: " + klijentEmail);
        }

        TipTransakcije tipTranskacije = transakcijaRequestDto.getTipTransakcije();

        if (tipTranskacije == TipTransakcije.UPLATA) {
            TransakcijaResponseDto transakcijaResponseDto = deposit(transakcijaRequestDto, klijent, brojRacunaUplate);

            return transakcijaResponseDto;
        } else if (tipTranskacije == TipTransakcije.ISPLATA) {
            TransakcijaResponseDto transakcijaResponseDto = withdraw(transakcijaRequestDto, klijent, brojRacunaIsplate);

            return transakcijaResponseDto;
        } else if (tipTranskacije == TipTransakcije.PRENOS) {
            TransakcijaResponseDto transakcijaResponseDto = transfer(transakcijaRequestDto, klijent, brojRacunaUplate, brojRacunaIsplate);

            return transakcijaResponseDto;
        } else {
            throw new EnumConstantNotPresentException(TipTransakcije.class, "tipTransakcije");
        }

    }





    //TODO
    //Proveriti i uporediti valute racuna uplate i transakcije i onda kasnije konvertovati ako treba
    private TransakcijaResponseDto deposit(TransakcijaRequestDto transakcijaRequestDto, Klijent klijent, String brojRacunaUplate) {
        Racun racunUplate = findActiveRacunByBrojRacuna(brojRacunaUplate);

        if (transakcijaRequestDto.getValutaTransakcije() != racunUplate.getValuta()) {
            throw new CurrencyException("Currencies for transakcija and racun are not the same");
        }

        Transakcija transakcija = new Transakcija();

        transakcija.setTipTransakcije(transakcijaRequestDto.getTipTransakcije());
        transakcija.setIznos(transakcijaRequestDto.getIznosTransakcije());
        transakcija.setValuta(transakcijaRequestDto.getValutaTransakcije());
        transakcija.setRacunUplate(racunUplate);
        transakcija.setRacunIsplate(null);
        transakcija.setKoeficijentKonverzije(0);
        transakcija.setDatumTransakcije(LocalDate.now());
        transakcija.setKlijentEmail(klijent);
        transakcija.setStatusTransakcije(StatusTransakcije.REALIZOVANA);

        transakcijaRepository.save(transakcija);

        TransakcijaResponseDto transakcijaResponseDto = modelMapper.map(transakcija, TransakcijaResponseDto.class);

        double noviIznos = racunUplate.getTrenutniIznos() + transakcijaRequestDto.getIznosTransakcije();

        racunUplate.setTrenutniIznos(noviIznos);
        racunUplate.setDatumPoslednjePromene(LocalDate.now());

        racunRepository.save(racunUplate);

        return transakcijaResponseDto;
    }

    private TransakcijaResponseDto withdraw(TransakcijaRequestDto transakcijaRequestDto, Klijent klijent, String brojRacunaIsplate) {
        Racun racunIsplate = findActiveRacunByBrojRacuna(brojRacunaIsplate);

        if (transakcijaRequestDto.getValutaTransakcije() != racunIsplate.getValuta()) {
            throw new CurrencyException("Currencies for transakcija and racun are not the same");
        }

        Transakcija transakcija = new Transakcija();

        transakcija.setTipTransakcije(TipTransakcije.ISPLATA);
        transakcija.setIznos(transakcijaRequestDto.getIznosTransakcije());
        transakcija.setValuta(transakcijaRequestDto.getValutaTransakcije());
        transakcija.setRacunUplate(null);
        transakcija.setRacunIsplate(racunIsplate);
        transakcija.setKoeficijentKonverzije(0);
        transakcija.setDatumTransakcije(LocalDate.now());
        transakcija.setKlijentEmail(klijent);
        transakcija.setStatusTransakcije(StatusTransakcije.REALIZOVANA);

        transakcijaRepository.save(transakcija);

        TransakcijaResponseDto transakcijaResponseDto = modelMapper.map(transakcija, TransakcijaResponseDto.class);

        double noviIznos = racunIsplate.getTrenutniIznos() - transakcijaRequestDto.getIznosTransakcije();

        if (noviIznos < 0) {
            throw new NegativeBalanceException("Not enough balance");
        }

        racunIsplate.setTrenutniIznos(noviIznos);
        racunIsplate.setDatumPoslednjePromene(LocalDate.now());

        racunRepository.save(racunIsplate);

        return transakcijaResponseDto;
    }


    //Racun uplate je onaj na koji se uplacuje novac, racun isplate je onaj koji salje novac
    private TransakcijaResponseDto transfer(TransakcijaRequestDto transakcijaRequestDto, Klijent klijent, String brojRacunaUplate, String brojRacunaIsplate) {
        Racun racunUplate = findActiveRacunByBrojRacuna(brojRacunaUplate);
        Racun racunIsplate = findActiveRacunByBrojRacuna(brojRacunaIsplate);

        //Valuta transakcije i valuta racuna isplate moraju biti iste
        if (transakcijaRequestDto.getValutaTransakcije() != racunIsplate.getValuta()) {
            throw new CurrencyException("Transakcija currency and racun isplate currency are different");
        }

        if (racunIsplate.getTrenutniIznos() < transakcijaRequestDto.getIznosTransakcije()) {
            throw new NegativeBalanceException("Not enough balance");
        }

        KonverzijaRequestDto konverzijaRequestDto = new KonverzijaRequestDto();

        konverzijaRequestDto.setIznosTransakcije(transakcijaRequestDto.getIznosTransakcije());
        konverzijaRequestDto.setValutaTransakcije(transakcijaRequestDto.getValutaTransakcije());
        konverzijaRequestDto.setValutaRacunaUplate(racunUplate.getValuta());

        //Saljemo zahtev na drugi servis, vracamo konvertovanu sumu koju onda dodajemo racunu uplate

        KonverzijaResponseDto konverzijaResponseDto = restClient.post()
                .uri(MyExchangeURL + "/api/kursneListe/exchange")
                .body(konverzijaRequestDto)
                .retrieve()
                .body(KonverzijaResponseDto.class);

        double noviIznosRacunUplate = konverzijaResponseDto.getKonvertovaniIznos() + racunUplate.getTrenutniIznos();
        double noviIznosRacunIsplate = racunIsplate.getTrenutniIznos() - transakcijaRequestDto.getIznosTransakcije();

        //Racun uplate se menja, dodaje mu se konvertovana suma
        racunUplate.setTrenutniIznos(noviIznosRacunUplate);
        racunUplate.setDatumPoslednjePromene(LocalDate.now());
        racunRepository.save(racunUplate);

        //Racun isplate se menja, oduzima mu se vrednost iz transakcije
        racunIsplate.setTrenutniIznos(noviIznosRacunIsplate);
        racunIsplate.setDatumPoslednjePromene(LocalDate.now());
        racunRepository.save(racunIsplate);

        System.out.println("RACUN UPLATE: " + racunUplate.getTrenutniIznos());
        System.out.println("RACUN ISPLATE: " + racunIsplate.getTrenutniIznos());

        //Transakcija se kreira i dodaje
        Transakcija transakcija = new Transakcija();

        transakcija.setTipTransakcije(TipTransakcije.PRENOS);
        transakcija.setIznos(transakcijaRequestDto.getIznosTransakcije());
        transakcija.setValuta(transakcijaRequestDto.getValutaTransakcije());
        transakcija.setRacunUplate(racunUplate);
        transakcija.setRacunIsplate(racunIsplate);
        transakcija.setKoeficijentKonverzije(konverzijaResponseDto.getKoeficijent());
        transakcija.setDatumTransakcije(LocalDate.now());
        transakcija.setKlijentEmail(klijent);
        transakcija.setStatusTransakcije(StatusTransakcije.REALIZOVANA);

        transakcijaRepository.save(transakcija);

        TransakcijaResponseDto transakcijaResponseDto = modelMapper.map(transakcija, TransakcijaResponseDto.class);

        return transakcijaResponseDto;
    }

    private Racun findActiveRacunByBrojRacuna(String brojRacuna) {
        Racun racun = racunRepository.findByBrojRacuna(brojRacuna);

        if (racun == null) {
            throw new EntityNotFoundException("Racun with the provided broj does not exist: " + brojRacuna);
        } else if (!racun.getStatusRacuna().equals(StatusRacuna.AKTIVAN)) {
            throw new RacunClosedException("Racun with broj racuna: " + brojRacuna + " is not active");
        }

        return racun;
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
