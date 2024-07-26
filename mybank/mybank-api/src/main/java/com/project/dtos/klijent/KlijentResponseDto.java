package com.project.dtos.klijent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KlijentResponseDto {
    private String email;
    private String password;
    private String jmbg;
}
