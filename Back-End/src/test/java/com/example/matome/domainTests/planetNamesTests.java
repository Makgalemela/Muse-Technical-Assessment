package com.example.matome.domainTests;

import com.example.matome.domain.PlanetName;
import com.example.matome.repository.PlanetNameRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
public class planetNamesTests {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PlanetNameRepository planetNameRepository;


    @Test
    public void findByPlanetName_returnPlanetData() {
        PlanetName planetName = new PlanetName();
        planetName.setNodeName("A");
        planetName.setPlanetName("Earth");

        entityManager.persist(planetName);
        entityManager.flush();

        PlanetName foundEntity = planetNameRepository.findByPlanetName(planetName.getPlanetName());

        assertThat(foundEntity.getPlanetName()).isEqualTo(planetName.getPlanetName());

    }
}
