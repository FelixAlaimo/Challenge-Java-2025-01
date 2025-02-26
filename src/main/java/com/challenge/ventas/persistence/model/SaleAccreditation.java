package com.challenge.ventas.persistence.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="SALE_ACCREDITATION")
public class SaleAccreditation implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ACRE_ID")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name="ACRE_SEPO_ID", referencedColumnName = "SEPO_ID", nullable = false)
	private SellingPoint sellingPoint;
	
	@Column(name="ACRE_AMOUNT")
	private Long amount;
	
	@Column(name="ACRE_DATE")
	private Date accreditationDate;
	
	public SaleAccreditation() {
		// default empty constructor
	}

	public SaleAccreditation(SellingPoint sellingPoint, Long amount) {
		this.sellingPoint = sellingPoint;
		this.amount = amount;
		this.accreditationDate = new Date();
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public SellingPoint getSellingPoint() {
		return sellingPoint;
	}
	public void setSellingPoint(SellingPoint sellingPoint) {
		this.sellingPoint = sellingPoint;
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
	
	@Override
	public String toString() {
		return "[id: " + this.id + ", amount: " + this.amount + "] en el Punto de Venta: " + this.sellingPoint;
	}

}
