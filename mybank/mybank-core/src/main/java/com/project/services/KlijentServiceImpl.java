package com.project.services;

import com.project.domain.entities.Klijent;
import com.project.domain.entities.KlijentInfo;
import com.project.domain.entities.Racun;
import com.project.domain.repositoryinterfaces.KlijentInfoRepository;
import com.project.domain.repositoryinterfaces.KlijentRepository;

import com.project.domain.repositoryinterfaces.RacunRepository;
import com.project.dtos.klijent.KlijentResponseDto;
import com.project.dtos.klijent.KlijentRequestDto;
import com.project.enums.StatusRacuna;
import com.project.exceptions.EntityCannotBeDeletedException;
import com.project.serviceinterfaces.KlijentService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class KlijentServiceImpl implements KlijentService {

    KlijentRepository klijentRepository;
    ModelMapper modelMapper;
    KlijentInfoRepository klijentInfoRepository;
    RacunRepository racunRepository;

    @Autowired
    public KlijentServiceImpl(KlijentRepository klijentRepository, ModelMapper modelMapper, KlijentInfoRepository klijentInfoRepository, RacunRepository racunRepository) {
        this.klijentRepository = klijentRepository;
        this.modelMapper = modelMapper;
        this.klijentInfoRepository = klijentInfoRepository;
        this.racunRepository = racunRepository;
    }


    @Override
    public List<KlijentResponseDto> findAll() {
        List<Klijent> klijenti = klijentRepository.findAll();

        return modelMapper.map(klijenti, new TypeToken<List<KlijentResponseDto>>() {}.getType());
    }

    @Override
    public KlijentResponseDto findOneByEmail(String email) {
        Klijent klijent = klijentRepository.findOneByEmail(email);

        if (klijent == null) {
            throw new EntityNotFoundException("Client with the given email does not exist: " + email);
        }

        return modelMapper.map(klijent, KlijentResponseDto.class);
    }

    @Override
    public KlijentRequestDto create(KlijentRequestDto klijentRequestDto) {

        //Throw exception if Client Info with the given JMBG does not exist
        if (klijentInfoRepository.findOneByJmbg(klijentRequestDto.getJmbg()) == null) {
            throw new EntityNotFoundException("Client with the given jmbg does not exist: " + klijentRequestDto.getJmbg());
        }

        //Throw exception if a Client already exists with the given email
        if (klijentRepository.findOneByEmail(klijentRequestDto.getEmail()) != null) {
            throw new EntityExistsException("Client with the given email already exists: " + klijentRequestDto.getEmail());
        }

        KlijentInfo klijentInfo = klijentInfoRepository.findOneByJmbg(klijentRequestDto.getJmbg());
        Klijent klijent = new Klijent();

        klijent.setKlijentInfo(klijentInfo);
        klijent.setEmail(klijentRequestDto.getEmail());
        klijent.setPassword(klijentRequestDto.getPassword());
//        klijentInfo.setOperater(null);

        klijentRepository.save(klijent);

        return klijentRequestDto;
    }

    @Override
    public void deleteByJMBG(String jmbg) {
        Klijent klijent = klijentRepository.findOneByEmail(jmbg);

        if (klijent == null) {
            throw new EntityNotFoundException("Client with the given jmbg does not exist: " + jmbg);
        }

        List<Racun> racuni = racunRepository.findByClientsEmail(klijent.getEmail());

        for (Racun eachRacun: racuni) {
            if (!eachRacun.getStatusRacuna().equals(StatusRacuna.ZATVOREN)) {
                throw new EntityCannotBeDeletedException("Cannot delete client because a non closed racun exists");
            }
        }

        KlijentInfo klijentInfo = klijentInfoRepository.findOneByJmbg(jmbg);

        double newVersion = klijentInfo.getVersion() + 1;
        klijentInfo.setVersion(newVersion);
        klijentInfo.setDatumPromene(LocalDate.now());

        klijentInfoRepository.save(klijentInfo);
    }
}
