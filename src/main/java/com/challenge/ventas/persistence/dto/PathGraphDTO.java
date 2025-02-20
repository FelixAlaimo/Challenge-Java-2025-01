package com.challenge.ventas.persistence.dto;

import java.util.HashMap;
import java.util.Map;

public class PathGraphDTO {
	
	// A map to store the graph (connections between points and their cost)
	private Map<Long, Map<Long, Integer>> graph;

    public PathGraphDTO() {
        this.graph = new HashMap<>();
    }

	public Map<Long, Map<Long, Integer>> getGraph() {
		return graph;
	}
	public void setGraph(Map<Long, Map<Long, Integer>> graph) {
		this.graph = graph;
	}
	
	public void addConnection(Long from, Long to, int cost) {
        graph.putIfAbsent(from, new HashMap<>());
        graph.putIfAbsent(to, new HashMap<>());
        
        // both directions (from -> to and to -> from) are added
        graph.get(from).put(to, cost);
        graph.get(to).put(from, cost);
    }

}
