package com.challenge.ventas.persistence.dto;

import java.util.List;

public class PathResultDTO {
	
	List<Long> sellingPointsPath;
    int cost;
    
    public PathResultDTO() {
    	// default empty constructor
    }

    public PathResultDTO(List<Long> sellingPointsPath, int cost) {
        this.sellingPointsPath = sellingPointsPath;
        this.cost = cost;
    }

	public List<Long> getSellingPointsPath() {
		return sellingPointsPath;
	}
	public void setSellingPointsPath(List<Long> sellingPointsPath) {
		this.sellingPointsPath = sellingPointsPath;
	}

	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}

}
