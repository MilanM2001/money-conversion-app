package com.project.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "kursevi")
public class Kurs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @Column(name = "oznaka_valute", nullable = false, unique = true)
    private String oznakaValute;

    @Column(name = "sifra_valute", nullable = false)
    private String sifraValute;

    @Column(name = "naziv_zemlje", nullable = false)
    private String nazivZemlje;

    @Column(name = "vazi_za", nullable = false)
    private int vaziZa;

    @Column(name = "kupovni_kurs", nullable = false)
    private double kupovniKurs;

    @Column(name = "srednji_kurs", nullable = false)
    private double srednjiKurs;

    @Column(name = "prodajni_kurs", nullable = false)
    private double prodajniKurs;

    @ManyToOne
    @JoinColumn(name = "kursna_lista", nullable = false)
    private KursnaLista kursnaLista;
}
