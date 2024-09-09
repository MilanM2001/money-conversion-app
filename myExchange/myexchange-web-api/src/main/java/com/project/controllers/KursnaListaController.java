package com.project.controllers;

import com.project.dtos.konverzija.KonverzijaRequestDto;
import com.project.dtos.konverzija.KonverzijaResponseDto;
import com.project.dtos.kursnalista.KursnaListaRequestDto;
import com.project.dtos.kursnalista.KursnaListaResponseDto;
import com.project.serviceinterface.KursnaListaService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kursneListe")
public class KursnaListaController {

    KursnaListaService kursnaListaService;

    @Autowired
    public KursnaListaController(KursnaListaService kursnaListaService) {
        this.kursnaListaService = kursnaListaService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<KursnaListaResponseDto>> findAll() {
        try {
            List<KursnaListaResponseDto> kursneListeDto = kursnaListaService.findAll();

            return new ResponseEntity<>(kursneListeDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<KursnaListaResponseDto> findById(@PathVariable("id") Integer id) {
        try {
            KursnaListaResponseDto kursnaListaDto = kursnaListaService.findById(id);

            return new ResponseEntity<>(kursnaListaDto, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<KursnaListaRequestDto> create(@RequestBody @Valid KursnaListaRequestDto kursnaListaRequestDto) {
        try {
            KursnaListaRequestDto kursnaListaDto = kursnaListaService.create(kursnaListaRequestDto);

            return new ResponseEntity<>(kursnaListaDto, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/activate/{id}")
    public ResponseEntity<KursnaListaResponseDto> activate(@PathVariable("id") Integer id) {
        try {
            KursnaListaResponseDto kursnaListaDto = kursnaListaService.activate(id);

            return new ResponseEntity<>(kursnaListaDto, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (EntityExistsException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<KursnaListaResponseDto> deactivate(@PathVariable("id") Integer id) {
        try {
            KursnaListaResponseDto kursnaListaDto = kursnaListaService.deactivate(id);

            return new ResponseEntity<>(kursnaListaDto, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/exchange")
    public KonverzijaResponseDto exchange(@RequestBody KonverzijaRequestDto konverzijaRequestDto) {
        try {
            return kursnaListaService.exchange(konverzijaRequestDto);
        } catch (EntityNotFoundException e) {
            e.getMessage();
            return null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            return null;
        }
    }

}
