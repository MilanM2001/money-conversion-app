package com.project.dtos.kursnalista;

import com.project.enums.StatusKursneListe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KursnaListaResponseDto {
    private Integer id;
    private String osnovnaValuta;
    private String naziv;
    private LocalDate datum;
    private StatusKursneListe status;
}
