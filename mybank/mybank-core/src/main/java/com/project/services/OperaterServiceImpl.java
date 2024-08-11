package com.project.services;

import com.project.domain.entities.Operater;
import com.project.domain.repositoryinterfaces.OperaterRepository;
import com.project.dtos.operater.OperaterRequestDto;
import com.project.dtos.operater.OperaterResponseDto;
import com.project.serviceinterfaces.OperaterService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
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
    public List<OperaterResponseDto> findAll() {
        List<Operater> operateri = operaterRepository.findAll();

        return modelMapper.map(operateri, new TypeToken<List<OperaterResponseDto>>() {}.getType());
    }

    @Override
    public OperaterResponseDto findOneByEmail(String email) {
        Operater operater = operaterRepository.findOneByEmail(email);

        if (operater == null) {
            throw new EntityNotFoundException("Operater not found for email: " + email);
        }

        return modelMapper.map(operater, OperaterResponseDto.class);
    }

    @Override
    public OperaterRequestDto create(OperaterRequestDto operaterRequestDto) {

        Operater existingOperater = operaterRepository.findOneByEmail(operaterRequestDto.getEmail());

        if (existingOperater != null) {
            throw new EntityExistsException("Operater already exists");
        }

        Operater operater = modelMapper.map(operaterRequestDto, Operater.class);

        operaterRepository.save(operater);
        return operaterRequestDto;
    }

}
