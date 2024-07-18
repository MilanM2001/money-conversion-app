package com.project.serviceinterfaces;



import com.project.dtos.klijent.KlijentDto;

import java.util.List;

public interface KlijentService {
    List<KlijentDto> findAll();

    KlijentDto findOneByEmail(String email);

    KlijentDto create(KlijentDto klijentDto);
}
