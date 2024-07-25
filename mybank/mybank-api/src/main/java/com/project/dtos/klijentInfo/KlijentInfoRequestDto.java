package com.project.dtos.klijentInfo;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KlijentInfoRequestDto {

    @NotBlank(message = "JMBG je obavezan")
    private String jmbg;

    @NotBlank(message = "Ime je obavezno")
    private String ime;

    @NotBlank(message = "Prezime je obavezno")
    private String prezime;

    private Integer adresaId;

    @NotBlank(message = "Broj telefona je obavezan")
    private String brojTelefona;

    @NotBlank(message = "Status je obavezan")
    private String status;

    @NotBlank(message = "Version je obavezna")
    private String version;
}
