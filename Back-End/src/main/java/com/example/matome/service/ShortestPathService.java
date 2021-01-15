package com.example.matome.service;


import com.example.matome.domain.Path;
import com.example.matome.domain.SourceIndex;
import com.example.matome.dto.searchRequest;
import com.example.matome.dto.searchResponse;
import com.example.matome.repository.SourceIndexRepository;
import com.example.matome.repository.pathRepository;
import com.example.matome.utils.ResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShortestPathService {

    Logger logger = LoggerFactory.getLogger(ShortestPathService.class);



    private int numberOfVertices;
    private LinkedList<Integer> adjList[];
    int path [];
    Double distance = 0.0;
    List<String> journey = new ArrayList<>();


    @Autowired
    pathRepository pathRepository;

    @Autowired
    SourceIndexRepository sourceIndexRepository;



    private void init(){
        List<Path>  paths = pathRepository.findAll();
        numberOfVertices = paths.size();
        adjList =  new LinkedList[numberOfVertices];
        path = new int[numberOfVertices];
        for(int itr = 0 ; itr < numberOfVertices ; itr++ ){
            adjList[itr] = new LinkedList<>();
            path[itr] = -1;
        }
    }

    private void addEdges(){
        pathRepository.findAll().forEach(edge->{
            adjList[edge.getOrigin()].add(edge.getDestination());
        });
    }

    private void breathFirstSearch(searchRequest req){
        journey.clear();
        distance = 0.0;

        boolean visited[] = new boolean[numberOfVertices];
        LinkedList<Integer> queue = new LinkedList<Integer>();

        Integer source = sourceIndexRepository.findBySource(req.getOrigin()).getIndex();
        Integer des = sourceIndexRepository.findBySource(req.getDestination()).getIndex();
        visited[source]=true;
        queue.add(source);
        while (queue.size() != 0){
            source = queue.poll();
            Iterator<Integer> itr = adjList[source].listIterator();
            while (itr.hasNext())
            {
                int n = itr.next();
                if (!visited[n])
                {
                    visited[n] = true;
                    queue.add(n);
                    path[n] =  source;
                    if(n == des){
                        ComputeDistance(n);
                        return;
                    }
                }
            }
        }
        return;
    }

    public ResponseEntity<Object> findShortestPath(searchRequest req){

        searchResponse res = new searchResponse();
        init();
        addEdges();
        res.setDestination(req.getDestination());
        res.setOrigin(req.getOrigin());
        breathFirstSearch(req);
        req.setDistance(String.valueOf(distance));
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Successfully Calculated", req);

    }

    private  void ComputeDistance(int foundAt){
        SourceIndex source = sourceIndexRepository.findByIndex(foundAt);
        journey.clear();
        journey.add(source.getSource());
        while (path[foundAt] != -1){
             source = sourceIndexRepository.findByIndex(path[foundAt]);
             journey.add(source.getSource());
             foundAt = path[foundAt];
        }
        Collections.reverse(journey);

        int size = journey.size();
        for(int itr = 0 ; itr < size-1 ; itr++ ){
            Integer src = sourceIndexRepository.findBySource(journey.get(itr)).getIndex();
            Integer dest = sourceIndexRepository.findBySource(journey.get(itr+1)).getIndex();
            distance += pathRepository.findByOriginAndDestination(src, dest).getTrafficDelay();

        }
        logger.info("journey" + journey + " "+distance);
    }
}
