package com.project.serviceinterfaces;

import com.project.dtos.transakcija.TransakcijaRequestDto;
import com.project.dtos.transakcija.TransakcijaResponseDto;

import java.util.List;

public interface TransakcijaService {
    List<TransakcijaResponseDto> findAll();

    List<TransakcijaResponseDto> findAllByKlijentEmail(String email);

    List<TransakcijaResponseDto> findAllByKlijentEmail(String email, String sortBy);

    TransakcijaResponseDto transakcija(TransakcijaRequestDto transakcijaRequestDto, String klijentEmail, String brojRacunaUplate, String brojRacunaIsplate);

//    TransakcijaRequestDto deposit(TransakcijaRequestDto transkacijaDepositDto, String klijentEmail, String brojRacunaUplate);
//
//    TransakcijaRequestDto withdraw(TransakcijaRequestDto transakcijaDto, String klijentEmail, String brojRacunaIsplate);
}
