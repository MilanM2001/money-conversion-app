package com.project.domain.repositoryinterfaces;

import com.project.domain.entities.Adresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdresaRepository extends JpaRepository<Adresa, Integer> {
    Adresa findOneById(Integer id);
}
