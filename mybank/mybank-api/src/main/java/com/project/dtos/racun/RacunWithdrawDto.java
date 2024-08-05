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
public class RacunWithdrawDto {

    @NotNull
    @Min(message = "Iznos ne moze biti manji od 0", value = 0)
    @Max(message = "Iznos ne moze biti veci od 100000", value = 100000)
    private double iznos;
}
