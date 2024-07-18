package com.project.serviceinterfaces;

import com.project.dtos.adresa.AdresaDto;

import java.util.List;

public interface AdresaService {
    List<AdresaDto> findAll();
    AdresaDto findOneById(Integer id);
    AdresaDto create(AdresaDto adresaDto);
}
