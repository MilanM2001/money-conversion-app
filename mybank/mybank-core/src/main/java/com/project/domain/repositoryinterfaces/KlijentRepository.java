package com.project.domain.repositoryinterfaces;

import com.project.domain.entities.Klijent;
import com.project.domain.entities.KlijentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KlijentRepository extends JpaRepository<Klijent, String> {

    Klijent findOneByEmail(String email);
}
