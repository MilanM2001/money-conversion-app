package com.project.domain.repositoryinterfaces;

import com.project.domain.entities.Klijent;
import com.project.domain.entities.Zahtev;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZahtevRepository extends JpaRepository<Zahtev, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM zahtevi where zahtevi.klijent_email = ?")
    List<Zahtev> findAllByPodnosilacZahteva(String klijentEmail);
}
