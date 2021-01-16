package com.example.matome.repository;

import com.example.matome.domain.PlanetName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanetNameRepository extends JpaRepository<PlanetName, Long> {

    PlanetName findByNodeName(String NodeName);

    PlanetName findByIndex(Integer index);

    PlanetName findByPlanetName(String PlanetName);
}
