package com.project.domain.repositoryinterfaces;

import com.project.domain.entities.Operater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperaterRepository extends JpaRepository<Operater, String> {
    Operater findOneByEmail(String email);
}
