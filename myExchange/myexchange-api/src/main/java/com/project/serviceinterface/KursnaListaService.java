package com.project.serviceinterface;

import com.project.dtos.konverzija.KonverzijaRequestDto;
import com.project.dtos.konverzija.KonverzijaResponseDto;
import com.project.dtos.kursnalista.KursnaListaRequestDto;
import com.project.dtos.kursnalista.KursnaListaResponseDto;

import java.util.List;

public interface KursnaListaService {
    List<KursnaListaResponseDto> findAll();

    KursnaListaResponseDto findById(Integer id);

    KursnaListaRequestDto create(KursnaListaRequestDto kursnaListaRequestDto);

    KursnaListaResponseDto activate(Integer id);

    KursnaListaResponseDto deactivate(Integer id);

    KonverzijaResponseDto exchange(KonverzijaRequestDto konverzijaRequestDto);
}
