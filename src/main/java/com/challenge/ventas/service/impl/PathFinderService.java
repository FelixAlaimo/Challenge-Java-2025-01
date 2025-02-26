package com.challenge.ventas.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.challenge.ventas.persistence.dto.CostBetweenSellingPointsDTO;
import com.challenge.ventas.persistence.dto.PathGraphDTO;
import com.challenge.ventas.persistence.dto.PathNodeDTO;
import com.challenge.ventas.persistence.dto.PathResultDTO;
import com.challenge.ventas.service.IPathFinderService;
import com.challenge.ventas.utils.SellingPointValidator;

@Service
public class PathFinderService implements IPathFinderService {
	
	@Autowired
	private SellingPointValidator sellingPointValidator;
	
	@Override
	@Cacheable(value = "sales:cost:shortest", key = "#start + ':' + #end")
	public PathResultDTO findShortestPath(Long fromSellingPointId, Long toSellingPointId, List<CostBetweenSellingPointsDTO> existingCosts) {
		
		sellingPointValidator.validateSellingPointExistence(fromSellingPointId, "fromSellingPointId");
		sellingPointValidator.validateSellingPointExistence(toSellingPointId, "toSellingPointId");
		
		PathGraphDTO pathGraphDto = new PathGraphDTO();
		for (CostBetweenSellingPointsDTO cost : existingCosts) {
			pathGraphDto.addConnection(cost.getFromSellingPointId(), cost.getToSellingPointId(), cost.getCost());
		}
		return resolveShortestPath(fromSellingPointId, toSellingPointId, pathGraphDto.getGraph());
	}
	
	
	protected PathResultDTO resolveShortestPath(Long fromSellingPointId, Long toSellingPointId, Map<Long, Map<Long, Integer>> pathGraphMap) {
		PriorityQueue<PathNodeDTO> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(n -> n.getCost()));
        Map<Long, Integer> distances = new HashMap<>();
        Map<Long, Long> previous = new HashMap<>();
        
        // Initialize all distances as Infinity except for the start node
        for (Long point : pathGraphMap.keySet()) {
            distances.put(point, Integer.MAX_VALUE);
        }
        distances.put(fromSellingPointId, 0);
        
        // Add the start node to the queue
        priorityQueue.offer(new PathNodeDTO(fromSellingPointId, 0));

        while (!priorityQueue.isEmpty()) {
        	PathNodeDTO current = priorityQueue.poll();
            Long currentNode = current.getSellingPointId();
            int currentCost = current.getCost();

            // If we reach the destination, we can stop early
            if (currentNode.equals(toSellingPointId)) {
                List<Long> path = new ArrayList<>();
                Long currentPathNode = toSellingPointId;
                while (currentPathNode != null) {
                    path.add(currentPathNode);
                    currentPathNode = previous.get(currentPathNode);
                }
                Collections.reverse(path); // Reverse to get the correct order
                return new PathResultDTO(path, currentCost);
            }

            // If the cost at the current node is larger than the recorded cost, skip
            if (currentCost > distances.get(currentNode) || pathGraphMap.get(currentNode) == null) {
                continue;
            }

            // Explore all the neighbors and calculate the cost
            for (Map.Entry<Long, Integer> neighbor : pathGraphMap.get(currentNode).entrySet()) {
                Long neighborNode = neighbor.getKey();
                int newCost = currentCost + neighbor.getValue();

                // If a shorter path to the neighbor is found, update distances and previous node
                if (newCost < distances.get(neighborNode)) {
                    distances.put(neighborNode, newCost);
                    previous.put(neighborNode, currentNode); // Track the path
                    priorityQueue.offer(new PathNodeDTO(neighborNode, newCost));
                }
            }
        }
        return new PathResultDTO(Collections.emptyList(), -1); // If no path exists
    }

}
