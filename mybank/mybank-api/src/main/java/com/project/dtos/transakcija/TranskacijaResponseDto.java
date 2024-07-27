package com.project.dtos.transakcija;

import com.project.dtos.klijent.KlijentResponseDto;
import com.project.dtos.racun.RacunResponseDto;
import com.project.enums.StatusTransakcije;
import com.project.enums.TipTransakcije;
import com.project.enums.Valuta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TranskacijaResponseDto {
    private Integer id;
    private TipTransakcije tipTransakcije;
    private int iznos;
    private Valuta valuta;
    private RacunResponseDto racunUplate;
    private RacunResponseDto racunIsplate;
    private int koeficijentKonverzije;
    private LocalDate datumTransakcije;
    private KlijentResponseDto klijentEmail;
    private StatusTransakcije statusTransakcije;
}
