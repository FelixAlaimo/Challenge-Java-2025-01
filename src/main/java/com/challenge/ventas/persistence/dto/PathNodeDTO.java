package com.challenge.ventas.persistence.dto;

public class PathNodeDTO {
	
	Long sellingPointId;
    int cost;
    
    public PathNodeDTO() {
    	// default empty constructor
    }

    public PathNodeDTO(Long sellingPointId, int cost) {
        this.sellingPointId = sellingPointId;
        this.cost = cost;
    }

	public Long getSellingPointId() {
		return sellingPointId;
	}

	public void setSellingPointId(Long sellingPointId) {
		this.sellingPointId = sellingPointId;
	}

	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}

}
