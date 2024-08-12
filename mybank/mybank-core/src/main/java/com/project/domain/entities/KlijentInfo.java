package com.project.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "klijenti")
public class KlijentInfo {

    @Id
    @Column(name = "jmbg", unique = true, nullable = false)
    private String jmbg;

    @Column(name = "ime", nullable = false)
    private String ime;

    @Column(name = "prezime", nullable = false)
    private String prezime;

    @ManyToOne(targetEntity = Adresa.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "adresaId")
    private Adresa adresa;

    @Column(name = "broj_telefona", unique = true, nullable = false)
    private String brojTelefona;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "version", nullable = false)
    private double version;

    @Column(name = "datum_kreiranja", nullable = false)
    private LocalDate datumKreiranja;

    @Column(name = "datum_promene", nullable = false)
    private LocalDate datumPromene;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    @ManyToOne(targetEntity = Operater.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "operater_id")
    private Operater operater;
}
