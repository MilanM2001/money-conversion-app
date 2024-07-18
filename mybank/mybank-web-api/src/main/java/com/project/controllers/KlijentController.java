package com.project.controllers;



import com.project.dtos.klijent.KlijentDto;
import com.project.serviceinterfaces.KlijentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/klijenti")
public class KlijentController {

    KlijentService klijentService;

    @Autowired
    public KlijentController(KlijentService klijentService) {
        this.klijentService = klijentService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<KlijentDto>> findAll() {
        List<KlijentDto> klijentiDto = klijentService.findAll();

        return new ResponseEntity<>(klijentiDto, HttpStatus.OK);
    }

    @GetMapping("/find/{email}")
    public ResponseEntity<KlijentDto> findById(@PathVariable("email") String email) {
        KlijentDto klijentDto = klijentService.findOneByEmail(email);
        if (klijentDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(klijentDto, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<KlijentDto> create(@RequestBody KlijentDto klijentDto) {
        KlijentDto klijent = klijentService.create(klijentDto);

        return new ResponseEntity<>(klijent, HttpStatus.CREATED);
    }

}
