package com.project.dtos.klijentInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KlijentInfoDto {
    private String jmbg;
    private String ime;
    private String prezime;
    private Integer adresaId;
    private String brojTelefona;
    private String status;
    private String version;
}
