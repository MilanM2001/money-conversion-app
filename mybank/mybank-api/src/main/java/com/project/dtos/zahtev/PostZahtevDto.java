package com.project.dtos.zahtev;

import com.project.enums.StatusZahteva;
import com.project.enums.TipRacuna;
import com.project.enums.TipZahteva;
import com.project.enums.Valuta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostZahtevDto {
    private String brojZahteva;
    private TipRacuna tipRacuna;
    private Valuta valuta;
    private String brojRacuna;
}
