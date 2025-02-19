package com.challenge.ventas.persistence.dto;

import java.io.Serializable;

public class AccreditationDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long sellingPointId;
	
	private Long amount;

	public Long getSellingPointId() {
		return sellingPointId;
	}
	public void setSellingPointId(Long sellingPointId) {
		this.sellingPointId = sellingPointId;
	}

	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}

}
