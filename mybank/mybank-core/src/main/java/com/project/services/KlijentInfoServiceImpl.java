package com.project.services;

import com.project.domain.entities.KlijentInfo;
import com.project.domain.repositoryinterfaces.KlijentInfoRepository;
import com.project.domain.repositoryinterfaces.KlijentRepository;
import com.project.dtos.klijentInfo.KlijentInfoRequestDto;
import com.project.dtos.klijentInfo.KlijentInfoResponseDto;
import com.project.dtos.klijentInfo.KlijentInfoUpdateDto;
import com.project.serviceinterfaces.KlijentInfoService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KlijentInfoServiceImpl implements KlijentInfoService {

    KlijentRepository klijentRepository;
    KlijentInfoRepository klijentInfoRepository;
    ModelMapper modelMapper;

    @Autowired
    public KlijentInfoServiceImpl(KlijentInfoRepository klijentInfoRepository, KlijentRepository klijentRepository) {
        this.klijentInfoRepository = klijentInfoRepository;
        this.modelMapper = new ModelMapper();
        this.klijentRepository = klijentRepository;
    }

    @Override
    public List<KlijentInfoResponseDto> findAll() {
        List<KlijentInfo> klijentInfos = klijentInfoRepository.findAll();

        return modelMapper.map(klijentInfos, new TypeToken<List<KlijentInfoResponseDto>>() {}.getType());
    }

    @Override
    public KlijentInfoResponseDto findOneByJmbg(String jmbg) {
        KlijentInfo klijentInfo = klijentInfoRepository.findOneByJmbg(jmbg);

        if (klijentInfo == null) {
            throw new EntityNotFoundException("Klijent with JMBG " + jmbg + " not found");
        }

        return modelMapper.map(klijentInfo, KlijentInfoResponseDto.class);
    }

    @Override
    public KlijentInfoRequestDto create(KlijentInfoRequestDto klijentInfoRequestDto) {

        if (klijentInfoRepository.findOneByJmbg(klijentInfoRequestDto.getJmbg()) != null) {
            throw new EntityExistsException("Client with jmbg " + klijentInfoRequestDto.getJmbg() + " already exists");
        }

        KlijentInfo klijentInfo = modelMapper.map(klijentInfoRequestDto, KlijentInfo.class);
        klijentInfoRepository.save(klijentInfo);
        return klijentInfoRequestDto;
    }

    @Override
    public KlijentInfoUpdateDto update(KlijentInfoUpdateDto klijentInfoUpdateDto, String jmbg) {

        KlijentInfo klijentInfo = klijentInfoRepository.findOneByJmbg(jmbg);

        if (klijentInfo == null) {
            throw new EntityNotFoundException("Client with the given jmbg does not exist: " + jmbg);
        }

        klijentInfo.setIme(klijentInfoUpdateDto.getIme());
        klijentInfo.setPrezime(klijentInfoUpdateDto.getPrezime());
        klijentInfo.setBrojTelefona(klijentInfoUpdateDto.getBrojTelefona());

        klijentInfoRepository.save(klijentInfo);

        return klijentInfoUpdateDto;
    }
}
