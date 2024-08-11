package com.project.controllers;

import com.project.dtos.klijentInfo.KlijentInfoResponseDto;
import com.project.serviceinterfaces.KlijentInfoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/klijentInfos")
public class KlijentInfoController {

    KlijentInfoService klijentInfoService;

    @Autowired
    public void setKlijentInfoService(KlijentInfoService klijentInfoService) {
        this.klijentInfoService = klijentInfoService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<KlijentInfoResponseDto>> findAll() {
        try {
            List<KlijentInfoResponseDto> klijentInfoResponseDtos = klijentInfoService.findAll();

            return new ResponseEntity<>(klijentInfoResponseDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/find/{jmbg}")
    public ResponseEntity<KlijentInfoResponseDto> findByJmbg(@PathVariable("jmbg") String jmbg) {
        try {
            KlijentInfoResponseDto klijentInfoResponseDto = klijentInfoService.findOneByJmbg(jmbg);

            return new ResponseEntity<>(klijentInfoResponseDto, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
