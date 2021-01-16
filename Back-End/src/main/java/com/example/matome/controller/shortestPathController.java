package com.example.matome.controller;


import com.example.matome.dto.searchRequest;
import com.example.matome.service.DijkstrasAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.example.matome.configs.UrlConstant.*;

@Controller
@CrossOrigin("*")
public class shortestPathController {

    @Autowired
    DijkstrasAlgorithm dijkstrasAlgorithm;


    @PostMapping(value= GET_SHORTEST_PATH)
    public ResponseEntity<Object> shortestPath(@RequestBody searchRequest request) {
        return dijkstrasAlgorithm.findShortestPath(request);
    }


}
