package com.example.matome.repository;

import com.example.matome.domain.Path;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface pathRepository  extends JpaRepository<Path, Long> {
    Path findByDestination(String destination);
    Path findByOrigin(String Origin);
    Path findByTrafficDelay(String trafficDelay);
}
