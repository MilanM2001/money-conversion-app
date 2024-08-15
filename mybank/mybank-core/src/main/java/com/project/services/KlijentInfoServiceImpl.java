package com.project.services;

import com.project.domain.entities.KlijentInfo;
import com.project.domain.entities.Racun;
import com.project.domain.repositoryinterfaces.KlijentInfoRepository;
import com.project.domain.repositoryinterfaces.KlijentRepository;
import com.project.domain.repositoryinterfaces.RacunRepository;
import com.project.dtos.klijentInfo.KlijentInfoRequestDto;
import com.project.dtos.klijentInfo.KlijentInfoResponseDto;
import com.project.dtos.klijentInfo.KlijentInfoUpdateDto;
import com.project.enums.StatusKlijenta;
import com.project.serviceinterfaces.KlijentInfoService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class KlijentInfoServiceImpl implements KlijentInfoService {

    KlijentRepository klijentRepository;
    KlijentInfoRepository klijentInfoRepository;
    ModelMapper modelMapper;
    RacunRepository racunRepository;

    @Autowired
    public KlijentInfoServiceImpl(KlijentInfoRepository klijentInfoRepository, KlijentRepository klijentRepository, RacunRepository racunRepository) {
        this.klijentInfoRepository = klijentInfoRepository;
        this.modelMapper = new ModelMapper();
        this.klijentRepository = klijentRepository;
        this.racunRepository = racunRepository;
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
        } else if (klijentInfoRequestDto.getAdresaId() == null) {
            throw new EntityNotFoundException("Adresa Id not found");
        }


        KlijentInfo klijentInfo = modelMapper.map(klijentInfoRequestDto, KlijentInfo.class);

        klijentInfo.setVersion(0);
        klijentInfo.setDatumKreiranja(LocalDate.now());
        klijentInfo.setDatumPromene(LocalDate.now());
        klijentInfo.setStatus(StatusKlijenta.AKTIVAN);
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
        double newVersion = klijentInfo.getVersion() + 1;
        klijentInfo.setVersion(newVersion);
        klijentInfo.setDatumPromene(LocalDate.now());

        klijentInfoRepository.save(klijentInfo);

        return klijentInfoUpdateDto;
    }
}
