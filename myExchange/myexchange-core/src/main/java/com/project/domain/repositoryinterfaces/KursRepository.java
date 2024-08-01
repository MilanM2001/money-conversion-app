package com.project.domain.repositoryinterfaces;

import com.project.domain.entities.Kurs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KursRepository extends JpaRepository<Kurs, Integer> {
}
