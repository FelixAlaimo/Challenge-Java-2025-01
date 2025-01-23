package com.challenge.ventas.model;

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
	private String puntoDeVentaname;
	
	@Column(name="ACRE_DATE")
	private Date acreditacionDate;
	
	@Column(name="ACRE_IMPORTE")
	private Long importe;
	
	public Acreditacion() {
		// default empty constructor
	}
	
	public Acreditacion(Long puntoDeVentaId, String puntoDeVentaname, Long importe) {
		this.puntoDeVentaId = puntoDeVentaId;
		this.puntoDeVentaname = puntoDeVentaname;
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

	public String getPuntoDeVentaname() {
		return puntoDeVentaname;
	}
	public void setPuntoDeVentaname(String puntoDeVentaname) {
		this.puntoDeVentaname = puntoDeVentaname;
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
