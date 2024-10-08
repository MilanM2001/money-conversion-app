package com.project.serviceinterfaces;

import com.project.dtos.zahtev.ZahtevRequestDto;
import com.project.dtos.zahtev.ZahtevResponseDto;

import java.util.List;

public interface ZahtevService {
    List<ZahtevResponseDto> findAll();

    List<ZahtevResponseDto> findAllNonDecided();

    List<ZahtevResponseDto> findByClientsEmail(String email);

    ZahtevRequestDto openRequest(ZahtevRequestDto zahtevRequestDto, String email);

    ZahtevResponseDto closeRequest(String email, String brojRacuna);

    ZahtevResponseDto decide(String brojRacuna, String decision);
}
