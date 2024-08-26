package com.project.dtos.racun;

import com.project.enums.TipRacuna;
import com.project.enums.Valuta;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RacunUpdateDto {
    @NotBlank(message = "nazivBanke je obavezan")
    private String nazivBanke;

    @NotBlank(message = "tipRacuna je obavezan")
    private TipRacuna tipRacuna;

    @NotBlank(message = "valuta je obavezna")
    private Valuta valuta;
}
