package com.project.serviceinterface;

import com.project.dtos.kurs.KursRequestDto;
import com.project.dtos.kurs.KursResponseDto;
import com.project.enums.Valuta;

import java.util.List;

public interface KursService {
    List<KursResponseDto> findAll();

    KursResponseDto findById(Integer id);

    KursResponseDto findByKursnaListaAndValuta(Integer kursnaListaId, String valuta);

    KursRequestDto create(KursRequestDto kursRequestDto, Integer listaId);

}
