package com.project.dtos.klijent;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KlijentRequestDto {

    @NotBlank(message = "Email je obavezan")
    private String email;

    @NotBlank(message = "Password je obavezan")
    private String password;

    @NotBlank(message = "JMBG je obavezan")
    private String jmbg;
}
