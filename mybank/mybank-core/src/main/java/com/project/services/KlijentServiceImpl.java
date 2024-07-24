package com.project.services;

import com.project.domain.entities.Klijent;
import com.project.domain.entities.KlijentInfo;
import com.project.domain.repositoryinterfaces.KlijentInfoRepository;
import com.project.domain.repositoryinterfaces.KlijentRepository;

import com.project.dtos.klijent.KlijentDto;
import com.project.exceptions.ClientAlreadyExistsException;
import com.project.exceptions.ClientInfoNotFoundException;
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

        //If Client Info with the given JMBG does not exist
        if (klijentInfoRepository.findOneByJmbg(klijentDto.getJmbg()) == null) {
            throw new ClientInfoNotFoundException("Client with the given jmbg does not exist: " + klijentDto.getJmbg());
        }

        //If a Client already exists with the given email
        if (klijentRepository.findOneByEmail(klijentDto.getEmail()) != null) {
            throw new ClientAlreadyExistsException("Client with the given email already exists: " + klijentDto.getEmail());
        }

        KlijentInfo klijentInfo = klijentInfoRepository.findOneByJmbg(klijentDto.getJmbg());
        Klijent klijent = new Klijent();

        klijent.setKlijentInfo(klijentInfo);
        klijent.setEmail(klijentDto.getEmail());
        klijent.setPassword(klijentDto.getPassword());

        klijentRepository.save(klijent);
        return klijentDto;
    }
}
