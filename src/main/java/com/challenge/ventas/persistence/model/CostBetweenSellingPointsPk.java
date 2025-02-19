package com.challenge.ventas.persistence.model;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class CostBetweenSellingPointsPk implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
    @JoinColumn(name = "COST_FROM_SEPO_ID")
	private SellingPoint fromSellingPoint;
	
	@ManyToOne
    @JoinColumn(name = "COST_TO_SEPO_ID")
	private SellingPoint toSellingPoint;
	
	public CostBetweenSellingPointsPk() {
		// default empty constructor
	}

	public CostBetweenSellingPointsPk(SellingPoint fromSellingPoint, SellingPoint toSellingPoint) {
		this.fromSellingPoint = fromSellingPoint;
		this.toSellingPoint = toSellingPoint;
	}

	public SellingPoint getFromSellingPoint() {
		return fromSellingPoint;
	}
	public void setFromSellingPoint(SellingPoint fromSellingPoint) {
		this.fromSellingPoint = fromSellingPoint;
	}

	public SellingPoint getToSellingPoint() {
		return toSellingPoint;
	}
	public void setToSellingPoint(SellingPoint toSellingPoint) {
		this.toSellingPoint = toSellingPoint;
	}

}
