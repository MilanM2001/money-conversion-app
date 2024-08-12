package com.project.dtos.klijent;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KlijentRequestDto {

    @NotBlank(message = "email je obavezan")
    private String email;

    @NotBlank(message = "password je obavezan")
    private String password;

    @NotBlank(message = "jmbg je obavezan")
    private String jmbg;
}
