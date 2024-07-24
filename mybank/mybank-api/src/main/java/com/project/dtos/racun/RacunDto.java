package com.project.dtos.racun;

import com.project.dtos.klijent.KlijentDto;
import com.project.enums.StatusRacuna;
import com.project.enums.TipRacuna;
import com.project.enums.Valuta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RacunDto {
    private Integer id;
    private String nazivBanke;
    private TipRacuna tipRacuna;
    private String brojRacuna;
    private int trenutniIznos;
    private int kreditniLimit;
    private Valuta valuta;
    private StatusRacuna statusRacuna;
    private LocalDate datumKreiranja;
    private LocalDate datumPoslednjePromene;
    private String version;
    private KlijentDto klijent;
}
