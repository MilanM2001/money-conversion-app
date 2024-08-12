package com.project.dtos.klijentInfo;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KlijentInfoUpdateDto {

    @NotBlank(message = "ime je obavezno")
    private String ime;

    @NotBlank(message = "prezime je obavezno")
    private String prezime;

    @NotBlank(message = "brojTelefona je obavezan")
    private String brojTelefona;
}
