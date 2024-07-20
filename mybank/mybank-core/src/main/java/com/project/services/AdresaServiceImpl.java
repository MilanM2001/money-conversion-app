package com.project.services;

import com.project.domain.entities.Adresa;
import com.project.domain.repositoryinterfaces.AdresaRepository;
import com.project.dtos.adresa.AdresaDto;
import com.project.serviceinterfaces.AdresaService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdresaServiceImpl implements AdresaService {

    AdresaRepository adresaRepository;
    ModelMapper modelMapper;

    @Autowired
    public AdresaServiceImpl(AdresaRepository adresaRepository, ModelMapper modelMapper) {
        this.adresaRepository = adresaRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<AdresaDto> findAll() {
        List<Adresa> adrese = adresaRepository.findAll();
        List<AdresaDto> adreseDto = modelMapper.map(adrese, new TypeToken<List<AdresaDto>>() {}.getType());

        return adreseDto;
    }

    @Override
    public AdresaDto findOneById(Integer id) {
        Adresa adresa = adresaRepository.findOneById(id);
        if (adresa == null) {
            return null;
        }
        AdresaDto adresaDto = modelMapper.map(adresa, AdresaDto.class);

        return adresaDto;
    }

    @Override
    public AdresaDto create(AdresaDto adresaDto) {
        Adresa adresa = modelMapper.map(adresaDto, Adresa.class);
        adresa = adresaRepository.save(adresa);
        return adresaDto;
    }
}
