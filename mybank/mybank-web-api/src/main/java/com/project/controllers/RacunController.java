package com.project.controllers;

import com.project.dtos.racun.RacunResponseDto;
import com.project.serviceinterfaces.RacunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("/allByCllientEmail/{email}")
    public ResponseEntity<List<RacunResponseDto>> getAllByClientEmail(@PathVariable("email") String email) {
        try {
            List<RacunResponseDto> racuniDto = racunService.findByClientsEmail(email);

            return new ResponseEntity<>(racuniDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
