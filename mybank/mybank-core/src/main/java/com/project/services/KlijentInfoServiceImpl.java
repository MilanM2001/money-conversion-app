package com.project.services;

import com.project.domain.entities.KlijentInfo;
import com.project.domain.repositoryinterfaces.KlijentInfoRepository;
import com.project.dtos.klijentInfo.KlijentInfoDto;
import com.project.serviceinterfaces.KlijentInfoService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KlijentInfoServiceImpl implements KlijentInfoService {

    KlijentInfoRepository klijentInfoRepository;
    ModelMapper modelMapper;

    @Autowired
    public KlijentInfoServiceImpl(KlijentInfoRepository klijentInfoRepository) {
        this.klijentInfoRepository = klijentInfoRepository;
        this.modelMapper = new ModelMapper();
    }

    @Override
    public List<KlijentInfoDto> findAll() {
        List<KlijentInfo> klijentInfos = klijentInfoRepository.findAll();
        List<KlijentInfoDto> klijentInfoDtos = modelMapper.map(klijentInfos, new TypeToken<List<KlijentInfoDto>>() {}.getType());
        return klijentInfoDtos;
    }

    @Override
    public KlijentInfoDto findOneByJmbg(String jmbg) {
        KlijentInfo klijentInfo = klijentInfoRepository.findOneByJmbg(jmbg);
        if (klijentInfo == null) {
            return null;
        }
        KlijentInfoDto klijentInfoDto = modelMapper.map(klijentInfo, KlijentInfoDto.class);
        return klijentInfoDto;
    }

    @Override
    public KlijentInfoDto create(KlijentInfoDto klijentInfoDto) {
        KlijentInfo klijentInfo = modelMapper.map(klijentInfoDto, KlijentInfo.class);
        klijentInfo = klijentInfoRepository.save(klijentInfo);
        return klijentInfoDto;
    }
}
