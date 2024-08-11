package com.project.services;

import com.project.domain.entities.Racun;
import com.project.domain.repositoryinterfaces.RacunRepository;
import com.project.dtos.racun.RacunDepositDto;
import com.project.dtos.racun.RacunResponseDto;
import com.project.dtos.racun.RacunWithdrawDto;
import com.project.enums.StatusRacuna;
import com.project.serviceinterfaces.RacunService;
import jakarta.persistence.criteria.Predicate;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
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

        return modelMapper.map(racuni, new TypeToken<List<RacunResponseDto>>() {}.getType());
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

        return modelMapper.map(racuni, new TypeToken<List<RacunResponseDto>>() {}.getType());
    }
}
