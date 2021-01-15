package com.example.matome.service;


import com.example.matome.domain.Path;
import com.example.matome.domain.SourceIndex;
import com.example.matome.dto.searchRequest;
import com.example.matome.dto.searchResponse;
import com.example.matome.dto.trafficDistance;
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
    private List<trafficDistance> originToDestination = new ArrayList<>();



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

    private void breathFirstSearch(SourceIndex source_ , SourceIndex des_){
        journey.clear();
        distance = 0.0;

        boolean visited[] = new boolean[numberOfVertices];
        LinkedList<Integer> queue = new LinkedList<Integer>();

        Integer source = source_.getIndex();
        Integer des = des_.getIndex();
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

        SourceIndex source = sourceIndexRepository.findBySource(req.getOrigin());
        SourceIndex des = sourceIndexRepository.findBySource(req.getDestination());

        if(Objects.isNull(source) || Objects.isNull(des)){
            return ResponseHandler.generateResponse(HttpStatus.EXPECTATION_FAILED, true, "Path Does not exsit", null);
        }
        else{
            searchResponse res = new searchResponse();
            init();
            addEdges();
            res.setDestination(req.getDestination());
            res.setOrigin(req.getOrigin());
            breathFirstSearch(source,des);
            res.setDistance(String.valueOf(distance));
            res.setPath(originToDestination);

            return ResponseHandler.generateResponse(HttpStatus.OK, true, "Successfully Calculated", res);
        }


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
            trafficDistance _path = new trafficDistance();

            SourceIndex src = sourceIndexRepository.findBySource(journey.get(itr));
            SourceIndex dest = sourceIndexRepository.findBySource(journey.get(itr+1));
            double shortDistance =  pathRepository.findByOriginAndDestination(src.getIndex(), dest.getIndex()).getTrafficDelay();
            distance += shortDistance;
            _path.setFrom(src.getSource());
            _path.setTo(dest.getSource());
            _path.setDistance(String.valueOf(shortDistance));
            originToDestination.add(_path);
        }
        logger.info("journey" + journey + " "+distance);
    }
}
