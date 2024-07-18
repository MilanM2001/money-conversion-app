package com.project.serviceinterfaces;

import com.project.dtos.klijentInfo.KlijentInfoDto;
import com.project.dtos.klijentInfo.UpdateKlijentInfoDto;

import java.util.List;

public interface KlijentInfoService {
    List<KlijentInfoDto> findAll();
    KlijentInfoDto findOneByJmbg(String jmbg);
    KlijentInfoDto create(KlijentInfoDto klijentInfoDto);
    UpdateKlijentInfoDto update(UpdateKlijentInfoDto updateKlijentInfoDto, String jmbg);
}
