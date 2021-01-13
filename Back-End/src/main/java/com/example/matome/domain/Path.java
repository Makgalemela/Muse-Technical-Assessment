package com.example.matome.domain;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "Path")
public class Path {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long routerId;
    private String origin;
    private String destination;
    private String trafficDelay;


    public Long getRouterId() {
        return routerId;
    }

    public void setRouterId(Long routerId) {
        this.routerId = routerId;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getTrafficDelay() {
        return trafficDelay;
    }

    public void setTrafficDelay(String traficDelay) {
        this.trafficDelay = traficDelay;
    }
}
