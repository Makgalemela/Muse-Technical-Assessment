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
    private Integer origin;
    private Integer destination;
    private Double trafficDelay;


    public Long getRouterId() {
        return routerId;
    }

    public void setRouterId(Long routerId) {
        this.routerId = routerId;
    }

    public Integer getOrigin() {
        return origin;
    }

    public void setOrigin(Integer origin) {
        this.origin = origin;
    }

    public Integer getDestination() {
        return destination;
    }

    public void setDestination(Integer destination) {
        this.destination = destination;
    }

    public Double getTrafficDelay() {
        return trafficDelay;
    }

    public void setTrafficDelay(Double trafficDelay) {
        this.trafficDelay = trafficDelay;
    }
}
