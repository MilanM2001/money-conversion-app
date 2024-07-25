package com.project.serviceinterfaces;

import com.project.dtos.racun.RacunResponseDto;

import java.util.List;

public interface RacunService {
    List<RacunResponseDto> findAll();

    List<RacunResponseDto> findByClientsEmail(String email);
}
