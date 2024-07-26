package com.project.services;

import com.project.domain.entities.Klijent;
import com.project.domain.entities.KlijentInfo;
import com.project.domain.repositoryinterfaces.KlijentInfoRepository;
import com.project.domain.repositoryinterfaces.KlijentRepository;

import com.project.dtos.klijent.KlijentResponseDto;
import com.project.dtos.klijent.KlijentRequestDto;
import com.project.exceptions.EntityAlreadyExistsException;
import com.project.exceptions.EntityNotFoundException;
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
    public List<KlijentResponseDto> findAll() {
        List<Klijent> klijenti = klijentRepository.findAll();
        List<KlijentResponseDto> klijentiDto = modelMapper.map(klijenti, new TypeToken<List<KlijentResponseDto>>() {}.getType());
        return klijentiDto;
    }

    @Override
    public KlijentResponseDto findOneByEmail(String email) {
        Klijent klijent = klijentRepository.findOneByEmail(email);
        if (klijent == null) {
            return null;
        }
        KlijentResponseDto klijentResponseDto = modelMapper.map(klijent, KlijentResponseDto.class);
        return klijentResponseDto;
    }

    @Override
    public KlijentRequestDto create(KlijentRequestDto klijentRequestDto) {

        //Throw exception if Client Info with the given JMBG does not exist
        if (klijentInfoRepository.findOneByJmbg(klijentRequestDto.getJmbg()) == null) {
            throw new EntityNotFoundException("Client with the given jmbg does not exist: " + klijentRequestDto.getJmbg());
        }

        //Throw exception if a Client already exists with the given email
        if (klijentRepository.findOneByEmail(klijentRequestDto.getEmail()) != null) {
            throw new EntityAlreadyExistsException("Client with the given email already exists: " + klijentRequestDto.getEmail());
        }

        KlijentInfo klijentInfo = klijentInfoRepository.findOneByJmbg(klijentRequestDto.getJmbg());
        Klijent klijent = new Klijent();

        klijent.setKlijentInfo(klijentInfo);
        klijent.setEmail(klijentRequestDto.getEmail());
        klijent.setPassword(klijentRequestDto.getPassword());

        klijentRepository.save(klijent);
        return klijentRequestDto;
    }
}
