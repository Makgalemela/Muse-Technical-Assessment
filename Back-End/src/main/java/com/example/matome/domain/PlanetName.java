package com.example.matome.domain;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class PlanetName {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Integer index;
    private String nodeName;
    private String planetName;

    public String getPlanetName() {
        return planetName;
    }

    public void setPlanetName(String planetName) {
        this.planetName = planetName;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
       this.index = index;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }
}
