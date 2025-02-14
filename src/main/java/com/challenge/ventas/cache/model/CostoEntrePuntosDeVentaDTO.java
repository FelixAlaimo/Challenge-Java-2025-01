package com.challenge.ventas.cache.model;

import java.io.Serializable;

public class CostoEntrePuntosDeVentaDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long puntoAId;
	private String puntoAName;
	private Long puntoBId;
	private String puntoBName;
	private int costo;

	public CostoEntrePuntosDeVentaDTO() {
		// default empty constructor
	}
	
	public CostoEntrePuntosDeVentaDTO(int costo) {
		this.costo = costo;
	}

	public CostoEntrePuntosDeVentaDTO(Long puntoAId, String puntoAName, Long puntoBId, String puntoBName, int costo) {
		this(costo);
		this.puntoAId = puntoAId;
		this.puntoAName = puntoAName;
		this.puntoBId = puntoBId;
		this.puntoBName = puntoBName;
	}

	public Long getPuntoAId() {
		return puntoAId;
	}
	public void setPuntoAId(Long puntoAId) {
		this.puntoAId = puntoAId;
	}

	public String getPuntoAName() {
		return puntoAName;
	}
	public void setPuntoAName(String puntoAName) {
		this.puntoAName = puntoAName;
	}

	public Long getPuntoBId() {
		return puntoBId;
	}
	public void setPuntoBId(Long puntoBId) {
		this.puntoBId = puntoBId;
	}

	public String getPuntoBName() {
		return puntoBName;
	}
	public void setPuntoBName(String puntoBName) {
		this.puntoBName = puntoBName;
	}

	public int getCosto() {
		return costo;
	}
	public void setCosto(int costo) {
		this.costo = costo;
	}

}
