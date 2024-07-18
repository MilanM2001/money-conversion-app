package com.project.services;

import com.project.domain.entities.Operater;
import com.project.domain.repositoryinterfaces.OperaterRepository;
import com.project.dtos.operater.OperaterDto;
import com.project.serviceinterfaces.OperaterService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperaterServiceImpl implements OperaterService {

    OperaterRepository operaterRepository;
    ModelMapper modelMapper;

    @Autowired
    public OperaterServiceImpl(OperaterRepository operaterRepository, ModelMapper modelMapper) {
        this.operaterRepository = operaterRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<OperaterDto> findAll() {
        List<Operater> operateri = operaterRepository.findAll();
        List<OperaterDto> operateriDto = modelMapper.map(operateri, new TypeToken<List<OperaterDto>>() {}.getType());

        return operateriDto;
    }

    @Override
    public OperaterDto findOneByEmail(String email) {
        Operater operater = operaterRepository.findOneByEmail(email);
        if (operater == null) {
            return null;
        }

        OperaterDto operaterDto = modelMapper.map(operater, OperaterDto.class);
        return operaterDto;
    }

    @Override
    public OperaterDto create(OperaterDto operaterDto) {
        Operater operater = modelMapper.map(operaterDto, Operater.class);

        operaterRepository.save(operater);
        return operaterDto;
    }
}
