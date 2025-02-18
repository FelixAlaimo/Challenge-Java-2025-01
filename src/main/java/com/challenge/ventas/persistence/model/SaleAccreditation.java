package com.challenge.ventas.persistence.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="SALE_ACCREDITATION")
public class SaleAccreditation implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ACRE_ID")
	private Long id;
	
	@Column(name="ACRE_SEPO_ID")
	private Long sellingPointId;
	
	@Column(name="ACRE_SEPO_NAME")
	private String sellingPointName;
	
	@Column(name="ACRE_AMOUNT")
	private Long amount;
	
	@Column(name="ACRE_DATE")
	private Date accreditationDate;
	
	public SaleAccreditation() {
		// default empty constructor
	}

	public SaleAccreditation(Long sellingPointId, String sellingPointName, Long amount) {
		this.sellingPointId = sellingPointId;
		this.sellingPointName = sellingPointName;
		this.amount = amount;
		this.accreditationDate = new Date();
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

	public Date getAccreditationDate() {
		return accreditationDate;
	}
	public void setAccreditationDate(Date accreditationDate) {
		this.accreditationDate = accreditationDate;
	}

}
