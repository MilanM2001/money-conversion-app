package com.project.domain.repositoryinterfaces;

import com.project.domain.entities.KursnaLista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KursnaListaRepository extends JpaRepository<KursnaLista, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM kursne_liste k WHERE k.status = 'AKTIVNA'")
    Optional<KursnaLista> findActive();
}
