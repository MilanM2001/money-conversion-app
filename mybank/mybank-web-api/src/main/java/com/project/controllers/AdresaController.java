package com.project.controllers;

import com.project.dtos.adresa.AdresaRequestDto;
import com.project.dtos.adresa.AdresaResponseDto;
import com.project.serviceinterfaces.AdresaService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
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
    public ResponseEntity<List<AdresaResponseDto>> findAll() {
        try {
            List<AdresaResponseDto> adreseDtos = adresaService.findAll();

            return new ResponseEntity<>(adreseDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<AdresaResponseDto> findById(@PathVariable("id") Integer id) {
        try {
            AdresaResponseDto adresaResponseDto = adresaService.findOneById(id);


            return new ResponseEntity<>(adresaResponseDto, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<AdresaRequestDto> create(@RequestBody @Valid AdresaRequestDto adresaRequestDto) {
        try {
            AdresaRequestDto adresa = adresaService.create(adresaRequestDto);

            return new ResponseEntity<>(adresa, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
