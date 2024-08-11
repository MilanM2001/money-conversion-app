package com.project.controllers;

import com.project.dtos.zahtev.ZahtevRequestDto;
import com.project.dtos.zahtev.ZahtevResponseDto;
import com.project.serviceinterfaces.ZahtevService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/zahtevi")
public class ZahtevController {

    ZahtevService zahtevService;

    @Autowired
    public ZahtevController(ZahtevService zahtevService) {
        this.zahtevService = zahtevService;
    }

    //Operater ima pregled svih zahteva klijenata
    @GetMapping("/all")
    public ResponseEntity<List<ZahtevResponseDto>> findAll() {
        try {
            List<ZahtevResponseDto> zahteviDto = zahtevService.findAll();

            return new ResponseEntity<>(zahteviDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Operater ima pregled zahteva na cekanju
    @GetMapping("/allNonDecided")
    public ResponseEntity<List<ZahtevResponseDto>> findAllNonDecided() {
        try {
            List<ZahtevResponseDto> zahteviDto = zahtevService.findAllNonDecided();

            return new ResponseEntity<>(zahteviDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Klijent ima pregled svojih zahteva
    @GetMapping("/allByClientEmail/{email}")
    public ResponseEntity<List<ZahtevResponseDto>> findAllByClientEmail(@PathVariable("email") String email) {
        try {
            List<ZahtevResponseDto> zahteviDto = zahtevService.findByClientsEmail(email);

            return new ResponseEntity<>(zahteviDto, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Klijent kreira zahtev za otvaranje racuna
    @PostMapping("/open/{email}")
    public ResponseEntity<ZahtevRequestDto> open(@RequestBody ZahtevRequestDto zahtevRequestDto, @PathVariable("email") String email) {
        try {
            ZahtevRequestDto zahtevDto = zahtevService.openRequest(zahtevRequestDto, email);

            return new ResponseEntity<>(zahtevDto, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (EntityExistsException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Klijent kreira zahtev za zatvaranje racuna
    @PostMapping("/close/{email}/{brojRacuna}")
    public ResponseEntity<ZahtevResponseDto> close(@PathVariable("email") String email, @PathVariable("brojRacuna") String brojRacuna) {
        try {
            ZahtevResponseDto zahtevDto = zahtevService.closeRequest(email, brojRacuna);

            if (zahtevDto == null) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            return new ResponseEntity<>(zahtevDto, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Operater donosi odluku o zahtevu
    @PutMapping("/decide/{brojRacuna}/{decision}")
    public ResponseEntity<ZahtevResponseDto> decide(@PathVariable("brojRacuna") String brojRacuna, @PathVariable("decision") String decision) {
        try {
            ZahtevResponseDto zahtevResponseDto = zahtevService.decide(brojRacuna, decision);

            return new ResponseEntity<>(zahtevResponseDto, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
