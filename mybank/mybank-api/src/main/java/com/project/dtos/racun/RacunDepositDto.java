package com.project.dtos.racun;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RacunDepositDto {

    @NotNull
    @Min(message = "iznos mora biti veci od 10", value = 10)
    @Max(message = "iznos ne moze biti veci od 100000", value = 100000)
    private double iznos;
}
