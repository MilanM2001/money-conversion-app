package com.project.services;

import com.project.domain.entities.Kurs;
import com.project.domain.entities.KursnaLista;
import com.project.domain.repositoryinterfaces.KursRepository;
import com.project.domain.repositoryinterfaces.KursnaListaRepository;
import com.project.dtos.kurs.KursRequestDto;
import com.project.dtos.kurs.KursResponseDto;
import com.project.enums.StatusKursneListe;
import com.project.enums.Valuta;
import com.project.exceptions.EntityNotAccessibleException;
import com.project.exceptions.EntityNotFoundException;
import com.project.serviceinterface.KursService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.MethodNotAllowedException;

import java.util.ArrayList;
import java.util.List;

@Service
public class KursServiceImpl implements KursService {

    KursRepository kursRepository;
    KursnaListaRepository kursnaListaRepository;
    ModelMapper modelMapper;

    @Autowired
    public KursServiceImpl(KursRepository kursRepository, KursnaListaRepository kursnaListaRepository, ModelMapper modelMapper) {
        this.kursRepository = kursRepository;
        this.kursnaListaRepository = kursnaListaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<KursResponseDto> findAll() {
        List<Kurs> kursevi = kursRepository.findAll();
        List<KursResponseDto> kurseviDto = modelMapper.map(kursevi, new TypeToken<List<KursResponseDto>>() {}.getType());

        return kurseviDto;
    }

    @Override
    public KursResponseDto findById(Integer id) {
        Kurs kurs = kursRepository.findById(id).orElse(null);

        if (kurs == null) {
            throw new EntityNotFoundException("Cannot find kurs with id " + id);
        }

        KursResponseDto kursDto = modelMapper.map(kurs, KursResponseDto.class);

        return kursDto;
    }

    @Override
    public KursResponseDto findByKursnaListaAndValuta(Integer kursnaListaId, String valuta) {
        Kurs kurs = kursRepository.findByKursnaListaAndValuta(kursnaListaId, valuta);

        if (kurs == null) {
            throw new EntityNotFoundException("Cannot find kurs with id " + kursnaListaId + " and valuta " + valuta);
        }

        KursResponseDto kursDto = modelMapper.map(kurs, KursResponseDto.class);

        return kursDto;
    }

    @Override
    public KursRequestDto create(KursRequestDto kursRequestDto, Integer listaId) {
        KursnaLista kursnaLista = kursnaListaRepository.findById(listaId).orElse(null);

        if (kursnaLista == null) {
            throw new EntityNotFoundException("Cannot find kursna lista with id " + listaId);
        }

        if (!kursnaLista.getStatus().equals(StatusKursneListe.KREIRANA)) {
            throw new EntityNotAccessibleException("Kursna Lista is not accessible because the status is not 'KREIRANA'");
        }

        Kurs kurs = new Kurs();

        modelMapper.map(kursRequestDto, kurs);
        kurs.setKursnaLista(kursnaLista);

        kursRepository.save(kurs);

        return kursRequestDto;
    }
}
