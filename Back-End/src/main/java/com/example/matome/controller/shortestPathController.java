package com.example.matome.controller;


import com.example.matome.dto.GetShortestRouteRequest;
import com.example.matome.dto.GetShortestRouteResponses;
import com.example.matome.service.DijkstrasAlgorithm;
import com.example.matome.utils.ResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.json.JSONArray;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Objects;

import static com.example.matome.configs.UrlConstant.*;

@Controller
@CrossOrigin("*")
public class shortestPathController {


    Logger logger = LoggerFactory.getLogger(shortestPathController.class);

    @Autowired
    DijkstrasAlgorithm dijkstrasAlgorithm;


    @PostMapping(value= GET_SHORTEST_PATH)
    public ResponseEntity<Object> shortestPath(@RequestBody GetShortestRouteRequest request) {

        GetShortestRouteResponses response = dijkstrasAlgorithm.findShortestPath(request);
         String message = null;
         HttpStatus status =HttpStatus.OK ;
         boolean successStatus = true;
        if(Objects.isNull(response)){
            message = "The path you are looking for does not exist";
            successStatus = false;
        }else {
            message = "The path shortest path successfully computed";
        }
        return ResponseHandler.generateResponse(status, successStatus, message, response);
    }


}
