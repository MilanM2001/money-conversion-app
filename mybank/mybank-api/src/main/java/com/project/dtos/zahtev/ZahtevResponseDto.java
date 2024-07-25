package com.project.dtos.zahtev;

import com.project.dtos.klijent.KlijentDto;
import com.project.enums.StatusZahteva;
import com.project.enums.TipRacuna;
import com.project.enums.TipZahteva;
import com.project.enums.Valuta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZahtevResponseDto {
    private String brojZahteva;
    private TipZahteva tipZahteva;
    private TipRacuna tipRacuna;
    private Valuta valuta;
    private int kreditniLimit;
    private String brojRacuna;
    private StatusZahteva statusZahteva;
    private KlijentDto podnosilacZahteva;
    private LocalDate datumZahteva;
    private LocalDate datumOdluke;
}
