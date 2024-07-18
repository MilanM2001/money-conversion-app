package com.project.domain.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@DiscriminatorValue("OPERATER")
public class Operater extends User {
    public String getRole() {
        return this.getClass().getAnnotation(DiscriminatorValue.class).value();
    }
}
