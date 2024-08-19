package com.project.dtos.operater;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperaterRequestDto {

    @NotBlank(message = "email je obavezan")
    @Email(message = "Incorrect Email Format")
    private String email;

    @NotBlank(message = "password je obavezan")
    private String password;
}
