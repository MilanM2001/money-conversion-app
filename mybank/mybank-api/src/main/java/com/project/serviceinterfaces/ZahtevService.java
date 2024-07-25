package com.project.serviceinterfaces;

import com.project.dtos.zahtev.ZahtevRequestDto;
import com.project.dtos.zahtev.ZahtevResponseDto;

import java.util.List;

public interface ZahtevService {
    List<ZahtevResponseDto> findAll();

    List<ZahtevResponseDto> findByClientsEmail(String email);

    ZahtevRequestDto create(ZahtevRequestDto zahtevRequestDto, String email);

    ZahtevResponseDto decide(String brojRacuna, String decision);
}
