package com.project.dtos.klijentInfo;

import com.project.enums.StatusKlijenta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KlijentInfoResponseDto {
    private String jmbg;
    private String ime;
    private String prezime;
    private Integer adresaId;
    private String brojTelefona;
    private StatusKlijenta status;
    private double version;
    private LocalDate datumKreiranja;
    private LocalDate datumPromene;
}
