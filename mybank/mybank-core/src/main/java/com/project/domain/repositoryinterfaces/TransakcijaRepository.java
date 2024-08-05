package com.project.domain.repositoryinterfaces;

import com.project.domain.entities.Klijent;
import com.project.domain.entities.Transakcija;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransakcijaRepository extends JpaRepository<Transakcija, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM transakcije t WHERE t.klijent_email = ?")
    List<Transakcija> findAllByKlijentEmail(String email);

    List<Transakcija> findAllByKlijentEmail(Klijent email, Sort sort);
}
