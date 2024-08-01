package com.project.dtos.kurs;

import com.project.dtos.kursnalista.KursnaListaResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KursResponseDto {
    private Integer id;
    private String oznakaValute;
    private String sifraValute;
    private String nazivZemlje;
    private int vaziZa;
    private double kupovniKurs;
    private double srednjiKurs;
    private double prodajniKurs;
    private KursnaListaResponseDto kursnaLista;
}
