package com.project.dtos.operater;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperaterRequestDto {

    @NotBlank(message = "email je obavezan")
    private String email;

    @NotBlank(message = "password je obavezan")
    private String password;
}
