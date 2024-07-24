package com.project.domain.repositoryinterfaces;

import com.project.domain.entities.Racun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RacunRepository extends JpaRepository<Racun, Integer> {
}
