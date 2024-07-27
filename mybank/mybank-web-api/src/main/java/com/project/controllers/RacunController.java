package com.project.controllers;

import com.project.dtos.racun.RacunDepositDto;
import com.project.dtos.racun.RacunResponseDto;
import com.project.exceptions.EntityNotFoundException;
import com.project.serviceinterfaces.RacunService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/racuni")
public class RacunController {

    RacunService racunService;

    @Autowired
    public RacunController(RacunService racunService) {
        this.racunService = racunService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<RacunResponseDto>> getAll() {
        try {
            List<RacunResponseDto> racuniDto = racunService.findAll();

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

            return new ResponseEntity<>(racun, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
