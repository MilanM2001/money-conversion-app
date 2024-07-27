package com.project.services;

import com.project.domain.entities.Racun;
import com.project.domain.repositoryinterfaces.RacunRepository;
import com.project.dtos.racun.RacunDepositDto;
import com.project.dtos.racun.RacunResponseDto;
import com.project.exceptions.EntityNotFoundException;
import com.project.serviceinterfaces.RacunService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RacunServiceImpl implements RacunService {

    RacunRepository racunRepository;
    ModelMapper modelMapper;

    @Autowired
    public RacunServiceImpl(RacunRepository racunRepository, ModelMapper modelMapper) {
        this.racunRepository = racunRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<RacunResponseDto> findAll() {
        List<Racun> racuni = racunRepository.findAll();
        List<RacunResponseDto> racuniDto = modelMapper.map(racuni, new TypeToken<List<RacunResponseDto>>() {}.getType());

        return racuniDto;
    }

    @Override
    public List<RacunResponseDto> findByClientsEmail(String email) {
        List<Racun> racuni = racunRepository.findByClientsEmail(email);
        List<RacunResponseDto> racuniDto = modelMapper.map(racuni, new TypeToken<List<RacunResponseDto>>() {}.getType());

        return racuniDto;
    }


    //TODO
    //Proveriti da li je racun aktivan i da li pripada osobi koja deponuje
    @Override
    public RacunDepositDto deposit(RacunDepositDto racunDepositDto, String brojRacuna) {
        Racun racun = racunRepository.findByBrojRacuna(brojRacuna);

        if (racun == null) {
            throw new EntityNotFoundException("Racun with the provided broj racuna does not exist: " + brojRacuna);
        }

        racun.setTrenutniIznos(+racunDepositDto.getIznos());
        racunRepository.save(racun);

        return racunDepositDto;
    }
}
