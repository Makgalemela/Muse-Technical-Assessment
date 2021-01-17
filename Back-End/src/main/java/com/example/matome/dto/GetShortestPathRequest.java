package com.example.matome.dto;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "GetShortestPathRequest")
public class GetShortestPathRequest {

    private Boolean trafficInfo;
    private String origin;
    private String destination;
    private String distance;

    public GetShortestPathRequest() { }

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
