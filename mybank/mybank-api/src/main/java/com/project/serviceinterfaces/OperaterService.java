package com.project.serviceinterfaces;

import com.project.dtos.klijentInfo.KlijentInfoDto;
import com.project.dtos.operater.OperaterDto;

import java.util.List;

public interface OperaterService {
    List<OperaterDto> findAll();

    OperaterDto findOneByEmail(String email);

    OperaterDto create(OperaterDto operaterDto);
}
