package com.project.services;

import com.project.domain.entities.Klijent;
import com.project.domain.entities.KlijentInfo;
import com.project.domain.repositoryinterfaces.KlijentInfoRepository;
import com.project.domain.repositoryinterfaces.KlijentRepository;

import com.project.dtos.klijent.KlijentDto;
import com.project.serviceinterfaces.KlijentService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KlijentServiceImpl implements KlijentService {

    KlijentRepository klijentRepository;
    ModelMapper modelMapper;
    KlijentInfoRepository klijentInfoRepository;

    @Autowired
    public KlijentServiceImpl(KlijentRepository klijentRepository, ModelMapper modelMapper, KlijentInfoRepository klijentInfoRepository) {
        this.klijentRepository = klijentRepository;
        this.modelMapper = modelMapper;
        this.klijentInfoRepository = klijentInfoRepository;
    }


    @Override
    public List<KlijentDto> findAll() {
        List<Klijent> klijenti = klijentRepository.findAll();
        List<KlijentDto> klijentiDto = modelMapper.map(klijenti, new TypeToken<List<KlijentDto>>() {}.getType());
        return klijentiDto;
    }

    @Override
    public KlijentDto findOneByEmail(String email) {
        Klijent klijent = klijentRepository.findOneByEmail(email);
        if (klijent == null) {
            return null;
        }
        KlijentDto klijentDto = modelMapper.map(klijent, KlijentDto.class);
        return klijentDto;
    }

    @Override
    public KlijentDto create(KlijentDto klijentDto) {


        if (klijentInfoRepository.findOneByJmbg(klijentDto.getJmbg()) == null) {
            return null;
        }

        //Ne sme dva ista emaila
//        if (klijentRepository.findOneByEmail(klijentDto.getEmail()) != null) {
//            return null;
//        }

        KlijentInfo klijentInfo = klijentInfoRepository.findOneByJmbg(klijentDto.getJmbg());
        Klijent klijent = new Klijent();

        klijent.setKlijentInfo(klijentInfo);
        klijent.setEmail(klijentDto.getEmail());
        klijent.setPassword(klijentDto.getPassword());
//        Klijent klijent = modelMapper.map(klijentDto, Klijent.class);

        klijentRepository.save(klijent);
        return klijentDto;
    }
}
