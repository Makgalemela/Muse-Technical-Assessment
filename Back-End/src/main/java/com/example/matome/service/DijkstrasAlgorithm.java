package com.example.matome.service;


import com.example.matome.domain.Path;
import com.example.matome.domain.PlanetName;
import com.example.matome.dto.searchRequest;
import com.example.matome.dto.searchResponse;
import com.example.matome.repository.PlanetNameRepository;
import com.example.matome.repository.pathRepository;
import com.example.matome.utils.ResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 *
 *
 *
 * The algorithm used is generic and widely know and what is makes it interesting in this instance is how it is applied
 * to persist the data the graph.
 *
 */

@Component
public class DijkstrasAlgorithm {

    Logger logger = LoggerFactory.getLogger(DijkstrasAlgorithm.class);



    //The graph contain the weight between nodes.
    private Double graph[][];

    private Double[] shortestDistances;

    boolean[] added;

    private int[] parents;

    private List<String> desiredShortestPath = new ArrayList<>();

    private static final int NO_PARENT = -1;


    @Autowired
    pathRepository pathRepository;

    @Autowired
    PlanetNameRepository planetNameRepository;


    /**
     *  We initialise the graph and build it as the adjacency matrix
     *  We initialise all rows and columns to contains 0s initiali
     *  Then the relevant indices are populated
     *  So the worst case time complexity  O(n^2)
     *
     */


    private void init(){

            List<PlanetName> planetNameList = planetNameRepository.findAll();
            int dimensions = planetNameList.size()+1;

            graph = new Double[dimensions][dimensions];

            shortestDistances = new Double[dimensions];

            added = new boolean[dimensions];

            parents = new int[dimensions];

            for(int itr = 0 ; itr< dimensions ; itr++){
                for(int itc = 0 ; itc < dimensions ; itc++){
                    graph[itr][itc] =  0.0;
                }
            }


        /**
         *  Build the matrix of doubles because our distance is constructed as double
         */


        List<Path> paths = pathRepository.findAll();
        int size = paths.size();

        for(int itr = 0; itr < size ; itr++){

            Path path = paths.get(itr);
            Double distance = pathRepository.findByOriginAndDestination(path.getOrigin(),path.getDestination())
                    .getDistance();
            graph[path.getOrigin()][path.getDestination()] = distance;
        }


        /**
         * Initialise the shortest distances and added
         */

        for(int itr = 0; itr < dimensions ; itr++){
            shortestDistances[itr] = Double.MAX_VALUE;
            added[itr] = false;
        }

    }


    private void dijkstra(Integer origin){

        shortestDistances[origin] = 0.0;

        parents[origin] = NO_PARENT;


        int vertices = graph[0].length;

        for (int itr = 1; itr < vertices ; itr++){

            int nearestVertex = -1;
            Double shortestDistance = Double.MAX_VALUE;

            for (int itr2 = 0;itr2 < vertices; itr2++)
            {
                if (!added[itr2] && shortestDistances[itr2] < shortestDistance)
                {
                    nearestVertex = itr2;
                    shortestDistance = shortestDistances[itr2];
                }
            }


            //Keep track of all nodes which have been visited and added to the shortest path tree.
            added[nearestVertex] = true;

            for (int itr2 = 0; itr2 < vertices; itr2++)
            {
                Double edgeDistance = graph[nearestVertex][itr2];

                if (edgeDistance > 0
                        && ((shortestDistance + edgeDistance) <
                        shortestDistances[itr2]))
                {
                    parents[itr2] = nearestVertex;
                    ///Update the distance from one point to another
                    shortestDistances[itr2] = shortestDistance +
                            edgeDistance;
                }
            }

        }

    }

    /**
     *
     *   Recursive method to compute the shortes distance path
     * @param vertex
     * @param parents
     */
    private void computePath(Integer vertex, int [] parents){
        if(vertex == NO_PARENT){return;}
        computePath(parents[vertex],parents);
        PlanetName planetName = planetNameRepository.findByIndex(vertex);
        desiredShortestPath.add(planetName.getPlanetName());
    }


    /**
     *   Utility function to aggregate the methods and compute the results
     *
     * @param req
     * @return
     */
    public ResponseEntity<Object> findShortestPath(searchRequest req){
        PlanetName NodeName = planetNameRepository.findByPlanetName(req.getOrigin());
        PlanetName des = planetNameRepository.findByPlanetName(req.getDestination());

        if(Objects.isNull(NodeName) || Objects.isNull(des)){
            return ResponseHandler.generateResponse(HttpStatus.EXPECTATION_FAILED, true, "Path Does not exist", null);
        }else {
            searchResponse res = new searchResponse();
            init();
            dijkstra(NodeName.getIndex());
            computePath(des.getIndex(), parents);
            res.setOrigin(NodeName.getPlanetName());
            res.setDestination(des.getPlanetName());
            res.setDistance(String.valueOf(shortestDistances[des.getIndex()]));
            res.setPath(desiredShortestPath);
            return ResponseHandler.generateResponse(HttpStatus.EXPECTATION_FAILED, true, "Successfully Found the path", res);

        }
    }
}
