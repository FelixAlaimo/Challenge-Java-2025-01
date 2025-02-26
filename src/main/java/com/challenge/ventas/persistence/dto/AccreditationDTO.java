package com.challenge.ventas.persistence.dto;

import java.io.Serializable;

public class AccreditationDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private Long sellingPointId;
	
	private String sellingPointName;
	
	private Long amount;
	
	private String accreditationDate;
	
	
	public AccreditationDTO() {
		// default empty constructor
	}
	
	public AccreditationDTO(Long id, Long sellingPointId, String sellingPointName, Long amount, String accreditationDate) {
		this.id = id;
		this.sellingPointId = sellingPointId;
		this.sellingPointName = sellingPointName;
		this.amount = amount;
		this.accreditationDate = accreditationDate;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Long getSellingPointId() {
		return sellingPointId;
	}
	public void setSellingPointId(Long sellingPointId) {
		this.sellingPointId = sellingPointId;
	}

	public String getSellingPointName() {
		return sellingPointName;
	}
	public void setSellingPointName(String sellingPointName) {
		this.sellingPointName = sellingPointName;
	}

	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getAccreditationDate() {
		return accreditationDate;
	}
	public void setAccreditationDate(String accreditationDate) {
		this.accreditationDate = accreditationDate;
	}

}
