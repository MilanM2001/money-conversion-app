package com.project.domain.repositoryinterfaces;

import com.project.domain.entities.Kurs;
import com.project.enums.Valuta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface KursRepository extends JpaRepository<Kurs, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM kursevi k WHERE k.kursna_lista = 1 AND k.oznaka_valute = ?;")
    Kurs findByKursnaListaAndValuta(Integer kursnaListaId, Valuta valuta);
}
