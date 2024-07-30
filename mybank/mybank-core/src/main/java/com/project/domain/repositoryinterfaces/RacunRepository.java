package com.project.domain.repositoryinterfaces;

import com.project.domain.entities.Racun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RacunRepository extends JpaRepository<Racun, Integer>, JpaSpecificationExecutor<Racun> {

    @Query(nativeQuery = true, value = "SELECT * FROM racuni r WHERE r.klijent_email = ?")
    List<Racun> findByClientsEmail(String email);

    Racun findByBrojRacuna(String brojRacuna);
}
