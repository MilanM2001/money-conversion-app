package com.project.controllers;

import com.project.dtos.adresa.AdresaDto;
import com.project.serviceinterfaces.AdresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/adrese")
public class AdresaController {

    AdresaService adresaService;

    @Autowired
    public AdresaController(AdresaService adresaService) {
        this.adresaService = adresaService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<AdresaDto>> findAll() {
        List<AdresaDto> adreseDtos = adresaService.findAll();

        return new ResponseEntity<>(adreseDtos, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<AdresaDto> findById(@PathVariable("id") Integer id) {
        AdresaDto adresaDto = adresaService.findOneById(id);
        if (adresaDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(adresaDto, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<AdresaDto> create(@RequestBody AdresaDto adresaDto) {
        AdresaDto adresa = adresaService.create(adresaDto);

        return new ResponseEntity<>(adresa, HttpStatus.CREATED);
    }

}
