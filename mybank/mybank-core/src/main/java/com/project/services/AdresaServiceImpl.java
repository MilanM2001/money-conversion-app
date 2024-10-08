package com.project.services;

import com.project.domain.entities.Adresa;
import com.project.domain.repositoryinterfaces.AdresaRepository;
import com.project.dtos.adresa.AdresaRequestDto;
import com.project.dtos.adresa.AdresaResponseDto;
import com.project.serviceinterfaces.AdresaService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<AdresaResponseDto> findAll() {
        List<Adresa> adrese = adresaRepository.findAll();

        return modelMapper.map(adrese, new TypeToken<List<AdresaResponseDto>>() {}.getType());
    }

    @Override
    public AdresaResponseDto findOneById(Integer id) {
        Adresa adresa = adresaRepository.findOneById(id);

        if (adresa == null) {
            throw new EntityNotFoundException("Cannot find adresa with id " + id);
        }

        return modelMapper.map(adresa, AdresaResponseDto.class);
    }

    @Override
    public AdresaRequestDto create(AdresaRequestDto adresaRequestDto) {
        Adresa adresa = modelMapper.map(adresaRequestDto, Adresa.class);
        adresaRepository.save(adresa);

        return adresaRequestDto;
    }
}
