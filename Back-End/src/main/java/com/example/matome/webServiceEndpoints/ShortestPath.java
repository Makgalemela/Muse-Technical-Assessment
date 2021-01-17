package com.example.matome.webServiceEndpoints;

import com.example.matome.controller.shortestPathController;
import com.example.matome.dto.GetShortestPathRequest;
import com.example.matome.dto.GetShortestPathResponse;
import com.example.matome.service.DijkstrasAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class ShortestPath {


    Logger logger = LoggerFactory.getLogger(ShortestPath.class);

    @Autowired
    DijkstrasAlgorithm dijkstrasAlgorithm;

    @PayloadRoot(namespace = "http://ma2me.com/shortest" , localPart = "GetShortestPathRequest")
    @ResponsePayload
    public  GetShortestPathResponse  GetShortestPath(@RequestPayload GetShortestPathRequest request){
         return dijkstrasAlgorithm.findShortestPath(request);
    }
}
