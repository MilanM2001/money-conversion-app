package com.project.domain.repositoryinterfaces;

import com.project.domain.entities.Transakcija;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TranskacijaRepository extends JpaRepository<Transakcija, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM transakcije t WHERE t.klijent_email = ?")
    List<Transakcija> findAllByClientsEmail(String email);
}
