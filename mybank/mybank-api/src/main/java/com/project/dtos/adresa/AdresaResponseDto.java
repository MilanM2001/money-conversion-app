package com.project.dtos.adresa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdresaResponseDto {
    private Integer id;
    private String drzava;
    private String mesto;
    private String ulica;
    private int broj;
}
