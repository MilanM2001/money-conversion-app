package com.project.serviceinterfaces;

import com.project.dtos.klijentInfo.KlijentInfoDto;

import java.util.List;

public interface KlijentInfoService {
    List<KlijentInfoDto> findAll();
    KlijentInfoDto findOneByJmbg(String jmbg);
    KlijentInfoDto create(KlijentInfoDto klijentInfoDto);
}
