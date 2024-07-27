package com.project.controllers;

import com.project.dtos.transakcija.TranskacijaResponseDto;
import com.project.serviceinterfaces.TransakcijaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/transakcije")
public class TranskacijaController {

    TransakcijaService transkacijaService;

    @Autowired
    public TranskacijaController(TransakcijaService transkacijaService) {
        this.transkacijaService = transkacijaService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<TranskacijaResponseDto>> findAll() {
        try {
            List<TranskacijaResponseDto> transkacijeDto = transkacijaService.findAll();

            return new ResponseEntity<>(transkacijeDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/allByClientEmail/{email}")
    public ResponseEntity<List<TranskacijaResponseDto>> findAllByKlijentEmail(@PathVariable("email") String email) {
        try {
            List<TranskacijaResponseDto> transakcijeDto = transkacijaService.findAllByKlijentEmail(email);

            return new ResponseEntity<>(transakcijeDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
