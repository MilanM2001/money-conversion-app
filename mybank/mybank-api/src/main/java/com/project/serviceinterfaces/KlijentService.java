package com.project.serviceinterfaces;



import com.project.dtos.klijent.KlijentResponseDto;
import com.project.dtos.klijent.KlijentRequestDto;

import java.util.List;

public interface KlijentService {
    List<KlijentResponseDto> findAll();

    KlijentResponseDto findOneByEmail(String email);

    KlijentRequestDto create(KlijentRequestDto klijentRequestDto);
}
