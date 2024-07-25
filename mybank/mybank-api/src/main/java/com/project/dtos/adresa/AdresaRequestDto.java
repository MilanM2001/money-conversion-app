package com.project.dtos.adresa;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdresaRequestDto {
    @NotBlank(message = "Drzava je obavezna")
    private String drzava;

    @NotBlank(message = "Mesto je obavezno")
    private String mesto;

    @NotBlank(message = "Ulica je obavezna")
    private String ulica;

    @NotNull(message = "Broj je obavezan")
    private int broj;
}
