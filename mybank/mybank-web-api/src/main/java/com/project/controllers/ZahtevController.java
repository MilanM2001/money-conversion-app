package com.project.controllers;

import com.project.dtos.zahtev.ZahtevDto;
import com.project.serviceinterfaces.ZahtevService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/allMy/{email}")
    public ResponseEntity<List<ZahtevDto>> getAllMy(@PathVariable("email") String email) {
        List<ZahtevDto> zahteviDto = zahtevService.findMyByEmail(email);

        return new ResponseEntity<>(zahteviDto, HttpStatus.OK);
    }

}
