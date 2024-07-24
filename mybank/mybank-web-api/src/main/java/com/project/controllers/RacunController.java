package com.project.controllers;

import com.project.dtos.racun.RacunDto;
import com.project.serviceinterfaces.RacunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<List<RacunDto>> getAll() {
        List<RacunDto> racuniDto = racunService.findAll();

        return new ResponseEntity<>(racuniDto, HttpStatus.OK);
    }

}
