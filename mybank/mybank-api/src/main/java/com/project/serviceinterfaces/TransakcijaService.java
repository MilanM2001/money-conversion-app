package com.project.serviceinterfaces;

import com.project.dtos.transakcija.TranskacijaResponseDto;

import java.util.List;

public interface TransakcijaService {
    List<TranskacijaResponseDto> findAll();

    List<TranskacijaResponseDto> findAllByKlijentEmail(String email);
}
