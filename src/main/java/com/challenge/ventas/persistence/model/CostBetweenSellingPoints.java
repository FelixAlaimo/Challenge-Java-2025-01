package com.challenge.ventas.persistence.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="COST_BETWEEN_SELLING_POINTS")
public class CostBetweenSellingPoints implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private CostBetweenSellingPointsPk id;
	
	@Column(name="COST_AMOUNT")
	private int amount;
	
	public CostBetweenSellingPoints() {
		// default empty constructor
	}
	
	public CostBetweenSellingPoints(SellingPoint puntoA, SellingPoint puntoB, int amount) {
		this.id = new CostBetweenSellingPointsPk(puntoA, puntoB);
		this.amount = amount;
	}

	public CostBetweenSellingPointsPk getId() {
		return id;
	}
	public void setId(CostBetweenSellingPointsPk id) {
		this.id = id;
	}

	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
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
		
		CostBetweenSellingPoints other = (CostBetweenSellingPoints) obj;
		return Objects.equals(id, other.id);
	}

}
