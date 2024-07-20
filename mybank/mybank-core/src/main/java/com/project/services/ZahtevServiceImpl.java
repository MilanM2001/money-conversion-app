package com.project.services;

import com.project.domain.entities.Zahtev;
import com.project.domain.repositoryinterfaces.ZahtevRepository;
import com.project.dtos.zahtev.ZahtevDto;
import com.project.serviceinterfaces.ZahtevService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZahtevServiceImpl implements ZahtevService {

    ZahtevRepository zahtevRepository;
    ModelMapper modelMapper;

    @Autowired
    public ZahtevServiceImpl(ZahtevRepository zahtevRepository) {
        this.zahtevRepository = zahtevRepository;
        this.modelMapper = new ModelMapper();
    }


    @Override
    public List<ZahtevDto> findAll() {
        List<Zahtev> zahtevi = zahtevRepository.findAll();
        List<ZahtevDto> zahteviDto = modelMapper.map(zahtevi, new TypeToken<List<ZahtevDto>>() {}.getType());

        return zahteviDto;
    }

    @Override
    public List<ZahtevDto> findMyByEmail(String email) {
        List<Zahtev> zahtevi = zahtevRepository.findAllByPodnosilacZahteva(email);
        List<ZahtevDto> zahteviDto = modelMapper.map(zahtevi, new TypeToken<List<ZahtevDto>>() {}.getType());

        return zahteviDto;
    }
}
