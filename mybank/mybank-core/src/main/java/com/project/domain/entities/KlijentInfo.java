package com.project.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String version;

//    @OneToOne(mappedBy = "klijentInfo", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private Klijent klijent;

}
