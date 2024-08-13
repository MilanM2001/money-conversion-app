package com.project.controllers;

import com.project.dtos.klijent.KlijentRequestDto;
import com.project.dtos.klijentInfo.KlijentInfoRequestDto;
import com.project.dtos.klijentInfo.KlijentInfoUpdateDto;
import com.project.dtos.operater.OperaterRequestDto;
import com.project.dtos.operater.OperaterResponseDto;
import com.project.serviceinterfaces.KlijentInfoService;
import com.project.serviceinterfaces.KlijentService;
import com.project.serviceinterfaces.OperaterService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/operateri")
public class OperaterController {

    OperaterService operaterService;
    KlijentInfoService klijentInfoService;
    KlijentService klijentService;

    @Autowired
    public OperaterController(OperaterService operaterService, KlijentInfoService klijentInfoService, KlijentService klijentService) {
        this.operaterService = operaterService;
        this.klijentInfoService = klijentInfoService;
        this.klijentService = klijentService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<OperaterResponseDto>> findAll() {
        try {
            List<OperaterResponseDto> operateriDto = operaterService.findAll();

            return new ResponseEntity<>(operateriDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/find/{email}")
    public ResponseEntity<OperaterResponseDto> findByEmail(@PathVariable("email") String email) {
        try {
            OperaterResponseDto operaterResponseDto = operaterService.findOneByEmail(email);

            return new ResponseEntity<>(operaterResponseDto, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Kreiranje operatera
    @PostMapping("/create")
    public ResponseEntity<OperaterRequestDto> create(@RequestBody OperaterRequestDto operaterRequestDto) {
        try {
            OperaterRequestDto operater = operaterService.create(operaterRequestDto);

            return new ResponseEntity<>(operater, HttpStatus.CREATED);
        } catch (EntityExistsException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
