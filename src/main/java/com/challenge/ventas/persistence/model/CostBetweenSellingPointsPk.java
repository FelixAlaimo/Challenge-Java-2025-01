package com.challenge.ventas.persistence.model;

import java.io.Serializable;
import java.util.Objects;

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

	@Override
	public int hashCode() {
		return Objects.hash(this.fromSellingPoint, this.toSellingPoint);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		
		CostBetweenSellingPointsPk other = (CostBetweenSellingPointsPk) obj;
		return Objects.equals(this.fromSellingPoint, other.fromSellingPoint)
				&& Objects.equals(this.toSellingPoint, other.toSellingPoint);
	}

}
