package com.project.serviceinterfaces;

import com.project.dtos.adresa.AdresaRequestDto;
import com.project.dtos.adresa.AdresaResponseDto;

import java.util.List;

public interface AdresaService {
    List<AdresaResponseDto> findAll();
    AdresaResponseDto findOneById(Integer id);
    AdresaRequestDto create(AdresaRequestDto adresaRequestDto);
}
