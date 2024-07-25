package com.project.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "adrese")
public class Adresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "drzava", nullable = false)
    private String drzava;

    @Column(name = "mesto", nullable = false)
    private String mesto;

    @Column(name = "ulica", nullable = false)
    private String ulica;

    @Column(name = "broj", nullable = false)
    private int broj;
}
