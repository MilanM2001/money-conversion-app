package com.project.dtos.konverzija;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KonverzijaResponseDto {
    private double iznos;
    private double koeficijentKonverzije;
}
