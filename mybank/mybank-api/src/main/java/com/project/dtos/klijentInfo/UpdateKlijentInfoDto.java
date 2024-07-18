package com.project.dtos.klijentInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateKlijentInfoDto {
    private String ime;
    private String prezime;
    private String brojTelefona;
}
