package com.project.domain.entities;

import com.project.enums.StatusRacuna;
import com.project.enums.TipRacuna;
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
@Table(name = "racuni")
public class Racun {

    @Id
    @Column(name = "broj_racuna", nullable = false, unique = true)
    private String brojRacuna;

    @Column(name = "naziv_banke", nullable = false)
    private String nazivBanke;

    @Column(name = "tip_racuna", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipRacuna tipRacuna;

    @Column(name = "trenutni_iznos", nullable = false)
    private double trenutniIznos;

    @Column(name = "kreditni_limit", nullable = false)
    private double kreditniLimit;

    @Column(name = "valuta", nullable = false)
    @Enumerated(EnumType.STRING)
    private Valuta valuta;

    @Column(name = "status_racuna", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusRacuna statusRacuna;

    @Column(name = "datum_kreiranja", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate datumKreiranja;

    @Column(name = "datum_poslednje_promene", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate datumPoslednjePromene;

    @Column(name = "version", nullable = false)
    private String version;

    @ManyToOne(targetEntity = Klijent.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "klijent_email")
    private Klijent klijent;

}
