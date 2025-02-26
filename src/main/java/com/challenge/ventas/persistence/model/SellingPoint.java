package com.challenge.ventas.persistence.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="SELLING_POINT")
public class SellingPoint implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="SEPO_ID")
	private Long id;
	
	@Column(name="SEPO_NAME")
	private String name;
	
	@Column(name="SEPO_DELETED_DATE")
	private Date deletedDate;

	public SellingPoint() {
		// default empty constructor
	}
	
	public SellingPoint(Long id) {
		this.id = id;
	}
	
	public SellingPoint(String name) {
		this.name = name;
	}
	
	public SellingPoint(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Date getDeletedDate() {
		return deletedDate;
	}
	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}
	
	@Override
	public String toString() {
		return "[id: " + this.id + ", name: '" + this.name + "']";
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
		SellingPoint other = (SellingPoint) obj;
		return Objects.equals(id, other.id);
	}
	

}
