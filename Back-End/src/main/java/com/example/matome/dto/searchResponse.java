package com.example.matome.dto;

import java.util.ArrayList;
import java.util.List;

public class searchResponse {
    private String origin;
    private String destination;
    private String distance;
    private List<trafficDistance> path = new ArrayList<>();

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

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public List<trafficDistance> getPath() {
        return path;
    }

    public void setPath(List<trafficDistance> path) {
        this.path = path;
    }
}
