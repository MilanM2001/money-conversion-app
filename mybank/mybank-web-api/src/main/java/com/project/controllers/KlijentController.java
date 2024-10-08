package com.project.controllers;



import com.project.dtos.klijent.KlijentRequestDto;
import com.project.dtos.klijent.KlijentResponseDto;
import com.project.exceptions.EntityCannotBeDeletedException;
import com.project.serviceinterfaces.KlijentService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
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
    public ResponseEntity<List<KlijentResponseDto>> findAll() {
        try {
            List<KlijentResponseDto> klijentiDto = klijentService.findAll();

            return new ResponseEntity<>(klijentiDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/find/{email}")
    public ResponseEntity<KlijentResponseDto> findById(@PathVariable("email") String email) {
        try {
            KlijentResponseDto klijentResponseDto = klijentService.findOneByEmail(email);

            return new ResponseEntity<>(klijentResponseDto, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Operater kreira nalog klijenta
    @PostMapping("/createClient")
    public ResponseEntity<KlijentRequestDto> createClient(@RequestBody @Valid KlijentRequestDto klijentRequestDto) {
        try {
            KlijentRequestDto klijent = klijentService.create(klijentRequestDto);

            return new ResponseEntity<>(klijent, HttpStatus.CREATED);

        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (EntityExistsException ex) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Operater brise klijenta, ne moze da ga brise ako postoje racuni koji nisu u statusu ZATVOREN
    @DeleteMapping("/delete/{jmbg}")
    public ResponseEntity<String> deleteByJMBG(@PathVariable("jmbg") String jmbg) {
        try {
            klijentService.deleteByJMBG(jmbg);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (EntityCannotBeDeletedException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
