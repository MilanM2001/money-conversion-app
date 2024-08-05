package com.project.controllers;

import com.project.dtos.TestDto;
import com.project.dtos.adresa.AdresaResponseDto;
import com.project.dtos.racun.RacunDepositDto;
import com.project.dtos.racun.RacunResponseDto;
import com.project.dtos.racun.RacunWithdrawDto;
import com.project.exceptions.EntityNotFoundException;
import com.project.serviceinterfaces.RacunService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/racuni")
public class RacunController {

    RacunService racunService;
    @Value("${MY_EXCHANGE_URL}")
    private String MyExchangeURL;
    RestClient restClient;

    @Autowired
    public RacunController(RacunService racunService, RestClient restClient) {
        this.racunService = racunService;
        this.restClient = restClient;
    }

    //Operater ima pregled svih racuna
    @GetMapping("/all")
    public ResponseEntity<List<RacunResponseDto>> findAll() {
        try {
            List<RacunResponseDto> racuniDto = racunService.findAll();

            return new ResponseEntity<>(racuniDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Operater ima pregled svih racuna i moze da filtrira po klijentu, statusu i datumu promene
    @GetMapping("/allFiltered")
    public ResponseEntity<List<RacunResponseDto>> findAllFiltered(
            @RequestParam(name = "klijent_email", required = false) String klijentEmail,
            @RequestParam(name = "status_racuna", required = false) String statusRacuna,
            @RequestParam(name = "datum_poslednje_promene", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate datumPoslednjePromene) {
        try {
            List<RacunResponseDto> racuniDto = racunService.findAllFiltered(klijentEmail, statusRacuna, datumPoslednjePromene);

            return new ResponseEntity<>(racuniDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Klijent ima pregled svojih racuna
    @GetMapping("/allByClientEmail/{email}")
    public ResponseEntity<List<RacunResponseDto>> getAllByClientEmail(@PathVariable("email") String email) {
        try {
            List<RacunResponseDto> racuniDto = racunService.findByClientsEmail(email);

            return new ResponseEntity<>(racuniDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Klijent moze da deponuje novac na racun
    @PutMapping("/deposit/{brojRacuna}")
    public ResponseEntity<RacunDepositDto> deposit(@RequestBody @Valid RacunDepositDto racunDepositDto, @PathVariable("brojRacuna") String brojRacuna) {
        try {
            RacunDepositDto racun = racunService.deposit(racunDepositDto, brojRacuna);

            if (racun == null) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            return new ResponseEntity<>(racun, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Klijent moze da podigne novac sa racuna
    @PutMapping("/withdraw/{brojRacuna}")
    public ResponseEntity<RacunWithdrawDto> withdraw(@RequestBody @Valid RacunWithdrawDto racunWithdrawDto, @PathVariable("brojRacuna") String brojRacuna) {
        try {
            RacunWithdrawDto racun = racunService.withdraw(racunWithdrawDto, brojRacuna);

            if (racun == null) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            return new ResponseEntity<>(racun, HttpStatus.OK);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getHello")
    public TestDto getHello() {
        String path = MyExchangeURL + "/api/kursevi/hello";
        TestDto test = new TestDto("Ime", "Email");
        TestDto newTest =  restClient.post()
                .uri(path)
                .body(test)
                .retrieve()
                .body(TestDto.class);
        return newTest;
    }

}
