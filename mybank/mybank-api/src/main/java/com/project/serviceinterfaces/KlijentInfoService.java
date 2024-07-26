package com.project.serviceinterfaces;

import com.project.dtos.klijentInfo.KlijentInfoRequestDto;
import com.project.dtos.klijentInfo.KlijentInfoResponseDto;
import com.project.dtos.klijentInfo.KlijentInfoUpdateDto;

import java.util.List;

public interface KlijentInfoService {
    List<KlijentInfoResponseDto> findAll();
    KlijentInfoResponseDto findOneByJmbg(String jmbg);
    KlijentInfoRequestDto create(KlijentInfoRequestDto klijentInfoRequestDto);
    KlijentInfoUpdateDto update(KlijentInfoUpdateDto klijentInfoUpdateDto, String jmbg);
}
