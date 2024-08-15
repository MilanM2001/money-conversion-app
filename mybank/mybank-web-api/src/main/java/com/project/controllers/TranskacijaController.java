package com.project.controllers;

import com.project.dtos.transakcija.TransakcijaRequestDto;
import com.project.dtos.transakcija.TransakcijaResponseDto;
import com.project.exceptions.CurrencyException;
import com.project.exceptions.NegativeBalanceException;
import com.project.serviceinterfaces.TransakcijaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientResponseException;

import java.util.List;

@RestController
@RequestMapping("/api/transakcije")
public class TranskacijaController {

    TransakcijaService transkacijaService;

    @Autowired
    public TranskacijaController(TransakcijaService transkacijaService) {
        this.transkacijaService = transkacijaService;
    }

    //Operater prati sve transakcije
    @GetMapping("/all")
    public ResponseEntity<List<TransakcijaResponseDto>> findAll() {
        try {
            List<TransakcijaResponseDto> transkacijeDto = transkacijaService.findAll();

            return new ResponseEntity<>(transkacijeDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/allByClientEmail/{email}")
    public ResponseEntity<List<TransakcijaResponseDto>> findAllByKlijentEmail(@PathVariable("email") String email) {
        try {
            List<TransakcijaResponseDto> transakcijeDto = transkacijaService.findAllByKlijentEmail(email);

            return new ResponseEntity<>(transakcijeDto, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/allByClientEmailSorted/{email}")
    public ResponseEntity<List<TransakcijaResponseDto>> findAllByKlijentEmail(@RequestParam(name = "sortBy", defaultValue = "datumTransakcije") String sortBy,
                                                                              @PathVariable("email") String email) {
        try {
            List<TransakcijaResponseDto> transakcijeDto = transkacijaService.findAllByKlijentEmail(email, sortBy);

            return new ResponseEntity<>(transakcijeDto, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Zahtev za transakciju, salju se email klijenta, brojevi racuna isplate i uplate
    //Na osnovu tipa transakcije gleda se da li je uplata, isplata ili prenos izmedju racuna
    @PostMapping("/transakcija/{klijentEmail}/{brojRacunaUplate}/{brojRacunaIsplate}")
    public ResponseEntity<TransakcijaResponseDto> transakcija(@RequestBody TransakcijaRequestDto transakcijaRequestDto,
                                                             @PathVariable(name = "klijentEmail") String klijentEmail,
                                                             @PathVariable(name = "brojRacunaUplate", required = false) String brojRacunaUplate,
                                                             @PathVariable(name = "brojRacunaIsplate", required = false) String brojRacunaIsplate) {
        try {
            TransakcijaResponseDto transakcijaResponseDto = transkacijaService.transakcija(transakcijaRequestDto, klijentEmail, brojRacunaUplate, brojRacunaIsplate);


            return new ResponseEntity<>(transakcijaResponseDto, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (CurrencyException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (EnumConstantNotPresentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NegativeBalanceException e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        } catch (RestClientResponseException e) {
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
