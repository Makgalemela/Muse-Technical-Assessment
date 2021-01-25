package com.example.matome.webServiceEndpoints;

import com.example.matome.dto.GetShortestRouteRequest;
import com.example.matome.dto.GetShortestRouteResponses;
import com.example.matome.service.DijkstrasAlgorithm;
import com.ma2me.shortest.GetShortestPathRequest;
import com.ma2me.shortest.GetShortestPathResponse;
import com.ma2me.shortest.ShortestPathResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.io.IOException;

@Endpoint
public class ShortestPath {


    Logger logger = LoggerFactory.getLogger(ShortestPath.class);

    @Autowired
    DijkstrasAlgorithm dijkstrasAlgorithm;

    @PayloadRoot(namespace = "http://ma2me.com/shortest" , localPart = "GetShortestPathRequest")
    @ResponsePayload
    public GetShortestPathResponse GetShortestPath(@RequestPayload GetShortestPathRequest request) throws IOException {

        GetShortestRouteRequest jsonRequest = new GetShortestRouteRequest();
        jsonRequest.setDestination(request.getShortestPathRequest().getDestination());
        jsonRequest.setOrigin(request.getShortestPathRequest().getOrigin());
        jsonRequest.setTrafficInfo(request.getShortestPathRequest().isTrafficInfo());

        GetShortestRouteResponses jsonResponse = dijkstrasAlgorithm.findShortestPath(jsonRequest);
        GetShortestPathResponse xmlResponses = new GetShortestPathResponse();

        ShortestPathResponse xmlResponse = new ShortestPathResponse();
        ShortestPathResponse.Path route = new ShortestPathResponse.Path();
        route.getItem().addAll(jsonResponse.getPath());


        xmlResponse.setOrigin(jsonResponse.getOrigin());
        xmlResponse.setDistance(jsonResponse.getDistance());
        xmlResponse.setDestination(jsonResponse.getDestination());
        xmlResponse.setPath(route);
        xmlResponses.setShortestPathResponse(xmlResponse);


        return xmlResponses;
    }
}
