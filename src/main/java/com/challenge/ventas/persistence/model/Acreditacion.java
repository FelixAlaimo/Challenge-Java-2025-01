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
@Table(name="ACREDITACION")
public class Acreditacion implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ACRE_ID")
	private Long id;
	
	@Column(name="ACRE_PUNTO_ID")
	private Long puntoDeVentaId;
	
	@Column(name="ACRE_PUNTO_NAME")
	private String puntoDeVentaName;
	
	@Column(name="ACRE_DATE")
	private Date acreditacionDate;
	
	@Column(name="ACRE_IMPORTE")
	private Long importe;
	
	public Acreditacion() {
		// default empty constructor
	}
	
	public Acreditacion(Long puntoDeVentaId, String puntoDeVentaName, Long importe) {
		this.puntoDeVentaId = puntoDeVentaId;
		this.puntoDeVentaName = puntoDeVentaName;
		this.acreditacionDate = new Date();
		this.importe = importe;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Long getPuntoDeVentaId() {
		return puntoDeVentaId;
	}
	public void setPuntoDeVentaId(Long puntoDeVentaId) {
		this.puntoDeVentaId = puntoDeVentaId;
	}

	public String getPuntoDeVentaName() {
		return puntoDeVentaName;
	}
	public void setPuntoDeVentaName(String puntoDeVentaName) {
		this.puntoDeVentaName = puntoDeVentaName;
	}

	public Date getAcreditacionDate() {
		return acreditacionDate;
	}
	public void setAcreditacionDate(Date acreditacionDate) {
		this.acreditacionDate = acreditacionDate;
	}
	
	public Long getImporte() {
		return importe;
	}
	public void setImporte(Long importe) {
		this.importe = importe;
	}

}
