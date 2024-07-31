package com.project.domain.entities;

import com.project.enums.StatusKursneListe;
import com.project.enums.Valuta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "kursne_liste")
public class KursnaLista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @Column(name = "osnovna_valuta", nullable = false)
    private String osnovnaValuta;

    @Column(name = "naziv", nullable = false)
    private String naziv;

    @Column(name = "datum", nullable = false)
    private LocalDate datum;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusKursneListe status;

    @OneToMany(mappedBy = "kursnaLista", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Kurs> kursevi = new ArrayList<>();
}
