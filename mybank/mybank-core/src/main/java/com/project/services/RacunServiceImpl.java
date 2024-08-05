package com.project.services;

import com.project.domain.entities.Racun;
import com.project.domain.repositoryinterfaces.RacunRepository;
import com.project.dtos.racun.RacunDepositDto;
import com.project.dtos.racun.RacunResponseDto;
import com.project.dtos.racun.RacunWithdrawDto;
import com.project.enums.StatusRacuna;
import com.project.exceptions.EntityNotFoundException;
import com.project.serviceinterfaces.RacunService;
import jakarta.persistence.criteria.Predicate;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<RacunResponseDto> findAllFiltered(String klijentEmail, String statusRacuna, LocalDate datumPoslednjePromene) {
        List<Racun> filteredRacuni = racunRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (klijentEmail != null && !klijentEmail.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("klijent").get("email"), klijentEmail));
            }
            if (statusRacuna != null && !statusRacuna.isEmpty()) {
                try {
                    StatusRacuna status = StatusRacuna.valueOf(statusRacuna.toUpperCase());
                    predicates.add(criteriaBuilder.equal(root.get("statusRacuna"), status));
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException("Invalid statusRacuna value: " + statusRacuna, e);
                }
            }
            if (datumPoslednjePromene != null) {
                predicates.add(criteriaBuilder.equal(root.get("datumPoslednjePromene"), datumPoslednjePromene));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });

        return filteredRacuni.stream()
                .map(racun -> modelMapper.map(racun, RacunResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<RacunResponseDto> findByClientsEmail(String email) {
        List<Racun> racuni = racunRepository.findByClientsEmail(email);
        List<RacunResponseDto> racuniDto = modelMapper.map(racuni, new TypeToken<List<RacunResponseDto>>() {}.getType());

        return racuniDto;
    }

    @Override
    public RacunDepositDto deposit(RacunDepositDto racunDepositDto, String brojRacuna) {
        Racun racun = racunRepository.findByBrojRacuna(brojRacuna);

        if (racun == null) {
            throw new EntityNotFoundException("Racun with the provided broj racuna does not exist: " + brojRacuna);
        }

        if (racun.getStatusRacuna().equals(StatusRacuna.AKTIVAN) || racun.getStatusRacuna().equals(StatusRacuna.KREIRAN)) {
            return null;
        }

        racun.setTrenutniIznos(+racunDepositDto.getIznos());
        racunRepository.save(racun);

        return racunDepositDto;
    }

    @Override
    public RacunWithdrawDto withdraw(RacunWithdrawDto racunWithdrawDto, String brojRacuna) {
        Racun racun = racunRepository.findByBrojRacuna(brojRacuna);

        if (racun == null) {
            throw new EntityNotFoundException("Racun with the provided broj racuna does not exist: " + brojRacuna);
        }

        if (racun.getStatusRacuna().equals(StatusRacuna.NEAKTIVAN) || racun.getStatusRacuna().equals(StatusRacuna.ZATVOREN)) {
            return null;
        }

        double iznos = racunWithdrawDto.getIznos();
        double trenutnoStanje = racun.getTrenutniIznos();
        double novoStanje = trenutnoStanje - iznos;

        if (novoStanje < 0) {
            return null;
        }

        racun.setTrenutniIznos(novoStanje);
        racunRepository.save(racun);

        return racunWithdrawDto;
    }
}
