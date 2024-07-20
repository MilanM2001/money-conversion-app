package com.project.controllers;

import com.project.dtos.klijent.KlijentDto;
import com.project.dtos.klijentInfo.KlijentInfoDto;
import com.project.dtos.klijentInfo.UpdateKlijentInfoDto;
import com.project.dtos.operater.OperaterDto;
import com.project.serviceinterfaces.KlijentInfoService;
import com.project.serviceinterfaces.KlijentService;
import com.project.serviceinterfaces.OperaterService;
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
    public ResponseEntity<List<OperaterDto>> findAll() {
        List<OperaterDto> operateriDto = operaterService.findAll();

        return new ResponseEntity<>(operateriDto, HttpStatus.OK);
    }

    @GetMapping("/find/{email}")
    public ResponseEntity<OperaterDto> findByEmail(@PathVariable("email") String email) {
        OperaterDto operaterDto = operaterService.findOneByEmail(email);
        if (operaterDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(operaterDto, HttpStatus.OK);
    }

    //Kreiranje operatera
    @PostMapping("/create")
    public ResponseEntity<OperaterDto> create(@RequestBody OperaterDto operaterDto) {
        OperaterDto operater = operaterService.create(operaterDto);

        if (operater == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(operater, HttpStatus.CREATED);
    }

    //Operater kreira informacije o klijentu
    @PostMapping("/createClientInfo")
    public ResponseEntity<KlijentInfoDto> createClientInfo(@RequestBody KlijentInfoDto klijentInfoDto) {
        KlijentInfoDto klijentInfo = klijentInfoService.create(klijentInfoDto);

        if (klijentInfo == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(klijentInfo, HttpStatus.CREATED);
    }

    //Operater kreira nalog klijenta
    @PostMapping("/createClient")
    public ResponseEntity<KlijentDto> createClient(@RequestBody KlijentDto klijentDto) {
        KlijentDto klijent = klijentService.create(klijentDto);

        if (klijent == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(klijent, HttpStatus.CREATED);
    }

    //Operater upravlja podacima klijenta
    @PutMapping("/updateClientInfo/{jmbg}")
    public ResponseEntity<UpdateKlijentInfoDto> updateClientInfo(@RequestBody UpdateKlijentInfoDto updateKlijentInfoDto, @PathVariable("jmbg") String jmbg) {
        UpdateKlijentInfoDto updateKlijent = klijentInfoService.update(updateKlijentInfoDto, jmbg);

        if (updateKlijent == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(updateKlijent, HttpStatus.OK);
    }


}
