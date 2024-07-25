package com.project.serviceinterfaces;

import com.project.dtos.operater.OperaterRequestDto;
import com.project.dtos.operater.OperaterResponseDto;

import java.util.List;

public interface OperaterService {
    List<OperaterResponseDto> findAll();

    OperaterResponseDto findOneByEmail(String email);

    OperaterRequestDto create(OperaterRequestDto operaterRequestDto);
}
