package com.project.dtos.zahtev;

import com.project.enums.TipRacuna;
import com.project.enums.Valuta;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZahtevRequestDto {
    @NotBlank(message = "Broj zahteva je obavezan")
    private String brojZahteva;

    @NotBlank(message = "Tip racuna je obavezan")
    private TipRacuna tipRacuna;

    @NotBlank(message = "Valuta je obavezna")
    private Valuta valuta;

    @NotBlank(message = "Broj racuna je obavezan")
    private String brojRacuna;
}
