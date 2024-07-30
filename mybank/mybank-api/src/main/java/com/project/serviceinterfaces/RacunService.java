package com.project.serviceinterfaces;

import com.project.dtos.racun.RacunDepositDto;
import com.project.dtos.racun.RacunResponseDto;
import com.project.dtos.racun.RacunWithdrawDto;

import java.time.LocalDate;
import java.util.List;

public interface RacunService {
    List<RacunResponseDto> findAll();

    List<RacunResponseDto> findAllFiltered(String klijentEmail, String statusRacuna, LocalDate datumPoslednjePromene);

    List<RacunResponseDto> findByClientsEmail(String email);

    RacunDepositDto deposit(RacunDepositDto racunDepositDto, String brojRacuna);

    RacunWithdrawDto withdraw(RacunWithdrawDto racunWithdrawDto, String brojRacuna);

}
