package com.project.dtos.transakcija;

import com.project.enums.Valuta;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransakcijaRequestDto {

    @NonNull
    @Min(message = "Iznos ne moze biti manji od 10", value = 10)
    @Max(message = "Iznos ne moze biti veci od 100000", value = 100000)
    private int iznos;

    @NotNull(message = "Valuta je obavezna")
    private Valuta valuta;

}
