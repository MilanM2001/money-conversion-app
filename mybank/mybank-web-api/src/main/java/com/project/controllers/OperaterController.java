package com.project.controllers;

import com.project.dtos.operater.OperaterDto;
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

    @Autowired
    public OperaterController(OperaterService operaterService) {
        this.operaterService = operaterService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<OperaterDto>> findAll() {
        List<OperaterDto> operateriDto = operaterService.findAll();

        return new ResponseEntity<>(operateriDto, HttpStatus.OK);
    }

    @GetMapping("/find/{email}")
    public ResponseEntity<OperaterDto> findByEmail(@PathVariable String email) {
        OperaterDto operaterDto = operaterService.findOneByEmail(email);
        if (operaterDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(operaterDto, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<OperaterDto> create(@RequestBody OperaterDto operaterDto) {
        OperaterDto operater = operaterService.create(operaterDto);

        return new ResponseEntity<>(operater, HttpStatus.CREATED);
    }

}
