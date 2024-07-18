package com.project.controllers;

import com.project.dtos.klijentInfo.KlijentInfoDto;
import com.project.serviceinterfaces.KlijentInfoService;
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
    public ResponseEntity<List<KlijentInfoDto>> findAll() {
        List<KlijentInfoDto> klijentInfoDtos = klijentInfoService.findAll();

        return new ResponseEntity<>(klijentInfoDtos, HttpStatus.OK);
    }

    @GetMapping("/find/{jmbg}")
    public ResponseEntity<KlijentInfoDto> findByJmbg(@PathVariable("jmbg") String jmbg) {
        KlijentInfoDto klijentInfoDto = klijentInfoService.findOneByJmbg(jmbg);
        if (klijentInfoDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(klijentInfoDto, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<KlijentInfoDto> create(@RequestBody KlijentInfoDto klijentInfoDto) {
        KlijentInfoDto klijentInfo = klijentInfoService.create(klijentInfoDto);

        return new ResponseEntity<>(klijentInfo, HttpStatus.CREATED);
    }


}
