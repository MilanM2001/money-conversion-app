package com.project.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("KLIJENT")
public class Klijent extends User {

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "jmbg", referencedColumnName = "jmbg", unique = true)
    private KlijentInfo klijentInfo;

    public String getRole() {
        return this.getClass().getAnnotation(DiscriminatorValue.class).value();
    }

}
