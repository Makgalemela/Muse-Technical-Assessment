package com.example.matome.repository;

import com.example.matome.domain.SourceIndex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SourceIndexRepository extends JpaRepository<SourceIndex, Long> {

    SourceIndex findBySource(String source);

    SourceIndex findByIndex(Integer index);
}
