package com.project.domain.entities;

import com.project.enums.StatusTransakcije;
import com.project.enums.TipTransakcije;
import com.project.enums.Valuta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transakcije")
public class Transakcija {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "tip_transkacije", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipTransakcije tipTransakcije;

    @Column(name = "iznos", nullable = false)
    private int iznos;

    @Column(name = "valuta", nullable = false)
    @Enumerated(EnumType.STRING)
    private Valuta valuta;

    @ManyToOne(targetEntity = Racun.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "racun_uplate")
    private Racun racunUplate;

    @ManyToOne(targetEntity = Racun.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "racun_isplate")
    private Racun racunIsplate;

    @Column(name = "koeficijent_konverzije", nullable = false)
    private int koeficijentKonverzije;

    @Column(name = "datum_transakcije", nullable = false)
    private LocalDate datumTransakcije;

    @ManyToOne(targetEntity = Klijent.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "klijent_email")
    private Klijent klijentEmail;

    @Column(name = "status_transakcije", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusTransakcije statusTransakcije;
}
