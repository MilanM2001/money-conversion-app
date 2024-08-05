package com.project.services;

import com.project.domain.entities.KursnaLista;
import com.project.domain.repositoryinterfaces.KursnaListaRepository;
import com.project.dtos.konverzija.KonverzijaRequestDto;
import com.project.dtos.konverzija.KonverzijaResponseDto;
import com.project.dtos.kursnalista.KursnaListaRequestDto;
import com.project.dtos.kursnalista.KursnaListaResponseDto;
import com.project.enums.StatusKursneListe;
import com.project.exceptions.EntityAlreadyExistsException;
import com.project.exceptions.EntityNotFoundException;
import com.project.serviceinterface.KursnaListaService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class KursnaListaServiceImpl implements KursnaListaService {

    KursnaListaRepository kursnaListaRepository;
    ModelMapper modelMapper;

    @Autowired
    public KursnaListaServiceImpl(KursnaListaRepository kursnaListaRepository, ModelMapper modelMapper) {
        this.kursnaListaRepository = kursnaListaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<KursnaListaResponseDto> findAll() {
        List<KursnaLista> kursneListe = kursnaListaRepository.findAll();
        List<KursnaListaResponseDto> kursneListeDto = modelMapper.map(kursneListe, new TypeToken<List<KursnaListaResponseDto>>() {}.getType());

        return kursneListeDto;
    }

    @Override
    public KursnaListaResponseDto findById(Integer id) {
        KursnaLista kursnaLista = kursnaListaRepository.findById(id).orElse(null);

        if (kursnaLista == null) {
            throw new EntityNotFoundException("Cannot find kursna lista with id: " + id);
        }

        KursnaListaResponseDto kursnaListaDto = modelMapper.map(kursnaLista, KursnaListaResponseDto.class);

        return kursnaListaDto;
    }

    @Override
    public KursnaListaRequestDto create(KursnaListaRequestDto kursnaListaRequestDto) {
        KursnaLista kursnaLista = new KursnaLista();

        modelMapper.map(kursnaListaRequestDto, kursnaLista);
        kursnaLista.setDatum(LocalDate.now());
        kursnaLista.setStatus(StatusKursneListe.KREIRANA);

        kursnaListaRepository.save(kursnaLista);

        return kursnaListaRequestDto;
    }

    @Override
    public KursnaListaResponseDto activate(Integer id) {
        KursnaLista kursnaLista = kursnaListaRepository.findById(id).orElse(null);
        Optional<KursnaLista> activeKursnaLista = kursnaListaRepository.findActive();

        if (kursnaLista == null) {
            throw new EntityNotFoundException("Cannot find kursna lista with id: " + id);
        }

        if (activeKursnaLista.isPresent()) {
            throw new EntityAlreadyExistsException("An activated kursna lista with id: " + id + " already exists");
        }

        kursnaLista.setStatus(StatusKursneListe.AKTIVNA);
        kursnaListaRepository.save(kursnaLista);
        KursnaListaResponseDto kursnaListaDto = modelMapper.map(kursnaLista, KursnaListaResponseDto.class);

        return kursnaListaDto;
    }

    @Override
    public KursnaListaResponseDto deactivate(Integer id) {
        KursnaLista kursnaLista = kursnaListaRepository.findById(id).orElse(null);

        if (kursnaLista == null) {
            throw new EntityNotFoundException("Cannot find kursna lista with id: " + id);
        }

        kursnaLista.setStatus(StatusKursneListe.NEAKTIVNA);
        kursnaListaRepository.save(kursnaLista);
        KursnaListaResponseDto kursnaListaDto = modelMapper.map(kursnaLista, KursnaListaResponseDto.class);

        return kursnaListaDto;
    }

    @Override
    public KonverzijaResponseDto exchange(KonverzijaRequestDto konverzijaRequestDto) {
        KursnaLista kursnaLista = kursnaListaRepository.findActive().orElse(null);

        if (kursnaLista == null) {
            throw new EntityNotFoundException("Cannot find an active kursna lista");
        }



        return null;
    }


}
