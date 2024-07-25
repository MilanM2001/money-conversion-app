package com.project.services;

import com.project.domain.entities.Racun;
import com.project.domain.repositoryinterfaces.RacunRepository;
import com.project.dtos.racun.RacunResponseDto;
import com.project.serviceinterfaces.RacunService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RacunServiceImpl implements RacunService {

    RacunRepository racunRepository;
    ModelMapper modelMapper;

    public RacunServiceImpl(RacunRepository racunRepository, ModelMapper modelMapper) {
        this.racunRepository = racunRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<RacunResponseDto> findAll() {
        List<Racun> racuni = racunRepository.findAll();
        List<RacunResponseDto> racuniDto = modelMapper.map(racuni, new TypeToken<List<RacunResponseDto>>() {}.getType());

        return racuniDto;
    }

    @Override
    public List<RacunResponseDto> findByClientsEmail(String email) {
        List<Racun> racuni = racunRepository.findByClientsEmail(email);
        List<RacunResponseDto> racuniDto = modelMapper.map(racuni, new TypeToken<List<RacunResponseDto>>() {}.getType());

        return racuniDto;
    }
}
