package com.challenge.ventas.persistence.dto;

import java.io.Serializable;

public class CostBetweenSellingPointsDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long fromSellingPointId;
	private String fromSellingPointName;
	private Long toSellingPointId;
	private String toSellingPointName;
	private int cost;

	public CostBetweenSellingPointsDTO() {
		// default empty constructor
	}
	
	public CostBetweenSellingPointsDTO(int cost) {
		this.cost = cost;
	}

	public CostBetweenSellingPointsDTO(Long fromSellingPointId, String fromSellingPointName, Long toSellingPointId, String toSellingPointName, int cost) {
		this(cost);
		this.fromSellingPointId = fromSellingPointId;
		this.fromSellingPointName = fromSellingPointName;
		this.toSellingPointId = toSellingPointId;
		this.toSellingPointName = toSellingPointName;
	}

	public Long getFromSellingPointId() {
		return fromSellingPointId;
	}
	public void setFromSellingPointId(Long fromSellingPointId) {
		this.fromSellingPointId = fromSellingPointId;
	}

	public String getFromSellingPointName() {
		return fromSellingPointName;
	}
	public void setFromSellingPointName(String fromSellingPointName) {
		this.fromSellingPointName = fromSellingPointName;
	}

	public Long getToSellingPointId() {
		return toSellingPointId;
	}
	public void setToSellingPointId(Long toSellingPointId) {
		this.toSellingPointId = toSellingPointId;
	}

	public String getToSellingPointName() {
		return toSellingPointName;
	}
	public void setToSellingPointName(String toSellingPointName) {
		this.toSellingPointName = toSellingPointName;
	}

	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}

}
