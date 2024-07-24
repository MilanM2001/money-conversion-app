package com.project.services;

import com.project.domain.entities.Racun;
import com.project.domain.repositoryinterfaces.RacunRepository;
import com.project.dtos.racun.RacunDto;
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
    public List<RacunDto> findAll() {
        List<Racun> racuni = racunRepository.findAll();
        List<RacunDto> racuniDto = modelMapper.map(racuni, new TypeToken<List<RacunDto>>() {}.getType());

        return racuniDto;
    }
}
