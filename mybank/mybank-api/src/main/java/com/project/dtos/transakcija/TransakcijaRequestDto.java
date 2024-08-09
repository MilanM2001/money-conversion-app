package com.project.dtos.transakcija;

import com.project.enums.TipTransakcije;
import com.project.enums.Valuta;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransakcijaRequestDto {

    @Min(message = "iznos ne moze biti manji od 1", value = 1)
    @Max(message = "iznos ne moze biti veci od 100000", value = 100000)
    private double iznosTransakcije;

    @NotNull(message = "valutaTransakcije je obavezna")
    private Valuta valutaTransakcije;

    @NotNull(message = "tipTransakcije je obavezan")
    private TipTransakcije tipTransakcije;

}
