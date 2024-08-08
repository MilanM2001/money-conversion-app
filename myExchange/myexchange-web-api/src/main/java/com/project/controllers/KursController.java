package com.project.controllers;

import com.project.dtos.TestDto;
import com.project.dtos.kurs.KursRequestDto;
import com.project.dtos.kurs.KursResponseDto;
import com.project.enums.Valuta;
import com.project.exceptions.EntityNotAccessibleException;
import com.project.exceptions.EntityNotFoundException;
import com.project.serviceinterface.KursService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kursevi")
public class KursController {

    KursService kursService;

    @Autowired
    public KursController(KursService kursService) {
        this.kursService = kursService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<KursResponseDto>> findAll() {
        try {
            List<KursResponseDto> kurseviDto = kursService.findAll();

            return new ResponseEntity<>(kurseviDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<KursResponseDto> findById(@PathVariable("id") Integer id) {
        try {
            KursResponseDto kurseviDto = kursService.findById(id);

            return new ResponseEntity<>(kurseviDto, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create/{lista_id}")
    public ResponseEntity<KursRequestDto> create(@RequestBody @Valid KursRequestDto kursRequestDto, @PathVariable("lista_id") Integer lista_id) {
        try {
            KursRequestDto kursDto = kursService.create(kursRequestDto, lista_id);

            return new ResponseEntity<>(kursDto, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (EntityNotAccessibleException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //---------------------------------------------------------

    @GetMapping("/findActiveValuta/{valuta}")
    public ResponseEntity<KursResponseDto> findActiveValuta(@PathVariable("valuta") String valuta) {
        try {
            Integer value = 1;
            KursResponseDto kursResponseDto = kursService.findByKursnaListaAndValuta(value, valuta);

            return new ResponseEntity<>(kursResponseDto, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/hello")
    public TestDto hello(@RequestBody TestDto testDto) {
        testDto.setName("uspeo");
        testDto.setEmail("uspeo@uspeo.com");
        return testDto;
    }

}
