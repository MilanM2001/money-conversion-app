package com.project.dtos.racun;

import com.project.enums.TipRacuna;
import com.project.enums.Valuta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RacunUpdateDto {
    private String nazivBanke;
    private TipRacuna tipRacuna;
    private Valuta valuta;
}
