package com.project.services;

import com.project.domain.entities.Transakcija;
import com.project.domain.repositoryinterfaces.TranskacijaRepository;
import com.project.dtos.transakcija.TranskacijaResponseDto;
import com.project.serviceinterfaces.TransakcijaService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TranskacijaServiceImpl implements TransakcijaService {

    TranskacijaRepository transkacijaRepository;
    ModelMapper modelMapper;

    @Autowired
    public TranskacijaServiceImpl(TranskacijaRepository transkacijaRepository, ModelMapper modelMapper) {
        this.transkacijaRepository = transkacijaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<TranskacijaResponseDto> findAll() {
        List<Transakcija> transakcije = transkacijaRepository.findAll();
        List<TranskacijaResponseDto> transakcijeDto = modelMapper.map(transakcije, new TypeToken<List<TranskacijaResponseDto>>() {}.getType());

        return transakcijeDto;
    }

    @Override
    public List<TranskacijaResponseDto> findAllByKlijentEmail(String email) {
        List<Transakcija> transakcije = transkacijaRepository.findAllByClientsEmail(email);
        List<TranskacijaResponseDto> transakcijeDto = modelMapper.map(transakcije, new TypeToken<List<TranskacijaResponseDto>>() {}.getType());

        return transakcijeDto;
    }
}
