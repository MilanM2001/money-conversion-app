package com.project.controllers;

import com.project.dtos.klijent.KlijentDto;
import com.project.dtos.klijentInfo.KlijentInfoRequestDto;
import com.project.dtos.klijentInfo.UpdateKlijentInfoDto;
import com.project.dtos.operater.OperaterRequestDto;
import com.project.dtos.operater.OperaterResponseDto;
import com.project.exceptions.ClientAlreadyExistsException;
import com.project.exceptions.ClientInfoNotFoundException;
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
            if (operaterResponseDto == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(operaterResponseDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Kreiranje operatera
    @PostMapping("/create")
    public ResponseEntity<OperaterRequestDto> create(@RequestBody OperaterRequestDto operaterRequestDto) {
        try {
            OperaterRequestDto operater = operaterService.create(operaterRequestDto);

            if (operater == null) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            return new ResponseEntity<>(operater, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/createClientInfo")
    public ResponseEntity<KlijentInfoRequestDto> createClientInfo(@RequestBody KlijentInfoRequestDto klijentInfoRequestDto) {
        try {
            KlijentInfoRequestDto klijentInfo = klijentInfoService.create(klijentInfoRequestDto);

            if (klijentInfo == null) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            return new ResponseEntity<>(klijentInfo, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Operater kreira nalog klijenta
    @PostMapping("/createClient")
    public ResponseEntity<KlijentDto> createClient(@RequestBody KlijentDto klijentDto) {
        try {
            KlijentDto klijent = klijentService.create(klijentDto);

            return new ResponseEntity<>(klijent, HttpStatus.CREATED);

        } catch (ClientInfoNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ClientAlreadyExistsException ex) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Operater upravlja podacima klijenta
    @PutMapping("/updateClientInfo/{jmbg}")
    public ResponseEntity<UpdateKlijentInfoDto> updateClientInfo(@RequestBody UpdateKlijentInfoDto updateKlijentInfoDto, @PathVariable("jmbg") String jmbg) {
        try {
            UpdateKlijentInfoDto updateKlijent = klijentInfoService.update(updateKlijentInfoDto, jmbg);

            return new ResponseEntity<>(updateKlijent, HttpStatus.OK);
        } catch (ClientInfoNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
