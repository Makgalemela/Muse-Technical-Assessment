package com.example.matome.dto;

public class searchRequest {

    private Boolean trafficInfo;
    private String origin;
    private String destination;
    private String distance;

    public searchRequest() { }

    public Boolean getTrafficInfo() {
        return trafficInfo;
    }

    public void setTrafficInfo(Boolean trafficInfo) {
        this.trafficInfo = trafficInfo;
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

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
