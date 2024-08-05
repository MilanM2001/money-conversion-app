package com.project.serviceinterface;

import com.project.dtos.kurs.KursRequestDto;
import com.project.dtos.kurs.KursResponseDto;

import java.util.List;

public interface KursService {
    List<KursResponseDto> findAll();

    KursResponseDto findById(Integer id);

    KursRequestDto create(KursRequestDto kursRequestDto, Integer listaId);
}
