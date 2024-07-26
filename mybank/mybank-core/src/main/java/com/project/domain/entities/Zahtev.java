package com.project.domain.entities;

import com.project.enums.StatusZahteva;
import com.project.enums.TipRacuna;
import com.project.enums.TipZahteva;
import com.project.enums.Valuta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "zahtevi")
public class Zahtev {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "broj_zahteva", nullable = false, unique = true)
    private Integer brojZahteva;

    @Column(name = "tip_zahteva", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private TipZahteva tipZahteva;

    @Column(name = "tip_naloga", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private TipRacuna tipRacuna;

    @Column(name = "valuta", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Valuta valuta;

    @Column(name = "kreditni_limit", nullable = false)
    private int kreditniLimit;

    @Column(name = "broj_racuna", unique = true, nullable = false)
    private String brojRacuna;

    @Column(name = "status_zahteva", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private StatusZahteva statusZahteva;

    @ManyToOne(targetEntity = Klijent.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "klijent_email")
    private Klijent podnosilacZahteva;

    @ManyToOne(targetEntity = Operater.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "operater_email")
    private Operater operaterBanke;

    @Column(name = "datum_zahteva", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate datumZahteva;

    @Column(name = "datum_odluke", nullable = true)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate datumOdluke;

}
