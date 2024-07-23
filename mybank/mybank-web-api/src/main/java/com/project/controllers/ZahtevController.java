package com.project.controllers;

import com.project.dtos.zahtev.PostZahtevDto;
import com.project.dtos.zahtev.ZahtevDto;
import com.project.serviceinterfaces.ZahtevService;
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

    @GetMapping("/all")
    public ResponseEntity<List<ZahtevDto>> getAll() {
        List<ZahtevDto> zahteviDto = zahtevService.findAll();

        return new ResponseEntity<>(zahteviDto, HttpStatus.OK);
    }

    //Klijent ima pregled svojih zahteva
    @GetMapping("/allByClientEmail/{email}")
    public ResponseEntity<List<ZahtevDto>> getAllMy(@PathVariable("email") String email) {
        List<ZahtevDto> zahteviDto = zahtevService.findByClientsEmail(email);

        return new ResponseEntity<>(zahteviDto, HttpStatus.OK);
    }

    //Klijent kreira zahtev
    @PostMapping("/create/{email}")
    public ResponseEntity<PostZahtevDto> create(@RequestBody PostZahtevDto postZahtevDto, @PathVariable("email") String email) {
        PostZahtevDto zahtevDto = zahtevService.create(postZahtevDto, email);

        if (zahtevDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(zahtevDto, HttpStatus.CREATED);
    }

    //Operater donosi odluku o zahtevu
    @PutMapping("/decide/{brojRacuna}/{decision}")
    public ResponseEntity<ZahtevDto> decide(@PathVariable("brojRacuna") String brojRacuna, @PathVariable("decision") String decision) {
        ZahtevDto zahtevDto = zahtevService.decide(brojRacuna, decision);

        if (zahtevDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(zahtevDto, HttpStatus.OK);

    }

}
