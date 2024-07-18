package com.project.domain.repositoryinterfaces;

import com.project.domain.entities.KlijentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KlijentInfoRepository extends JpaRepository<KlijentInfo, String> {
    KlijentInfo findOneByJmbg(String jmbg);
}
