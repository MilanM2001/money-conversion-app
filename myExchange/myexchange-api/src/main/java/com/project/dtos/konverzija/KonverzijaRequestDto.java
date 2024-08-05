package com.project.dtos.konverzija;

import com.project.enums.Valuta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KonverzijaRequestDto {
    private double iznosTransakcije;
    private Valuta valutaTranskacije;
    private Valuta valutaRacunaUplate;
}
