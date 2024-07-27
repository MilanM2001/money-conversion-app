package com.project.domain.repositoryinterfaces;

import com.project.domain.entities.Zahtev;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZahtevRepository extends JpaRepository<Zahtev, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM zahtevi WHERE zahtevi.klijent_email = ?")
    List<Zahtev> findByClientEmail(String klijentEmail);

    Zahtev findByBrojRacuna(String brojRacuna);

    @Query(nativeQuery = true, value = "SELECT * FROM zahtevi z WHERE z.broj_racuna = ? AND z.status_zahteva = 'KREIRAN'")
    Zahtev findKreiranByBrojRacuna(String brojRacuna);

    @Query(nativeQuery = true, value = "SELECT * FROM zahtevi z WHERE z.status_zahteva = 'KREIRAN'")
    List<Zahtev> findAllNonDecided();
}
