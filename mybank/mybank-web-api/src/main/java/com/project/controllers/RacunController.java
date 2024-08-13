package com.project.controllers;

import com.project.dtos.racun.RacunResponseDto;
import com.project.dtos.racun.RacunUpdateDto;
import com.project.exceptions.EntityStatusException;
import com.project.serviceinterfaces.RacunService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/racuni")
public class RacunController {

    RacunService racunService;

    @Autowired
    public RacunController(RacunService racunService) {
        this.racunService = racunService;
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

    //Racun moze da se update samo ako je u statusu KREIRAN
    @PutMapping("/update/{brojRacuna}")
    public ResponseEntity<RacunUpdateDto> update(@RequestBody RacunUpdateDto racunUpdateDto, @PathVariable("brojRacuna") String brojRacuna) {
        try {
            RacunUpdateDto racunUpdate = racunService.update(racunUpdateDto, brojRacuna);

            return new ResponseEntity<>(racunUpdate, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (EntityStatusException e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
