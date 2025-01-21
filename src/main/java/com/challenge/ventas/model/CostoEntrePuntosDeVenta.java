package com.challenge.ventas.model;

import java.io.Serializable;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity
public class CostoEntrePuntosDeVenta implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private CostoEntrePuntosDeVentaPk id;
	
	private int amount;
	
	public CostoEntrePuntosDeVenta() {
		// default empty constructor
	}
	
	public CostoEntrePuntosDeVenta(PuntoDeVenta puntoA, PuntoDeVenta puntoB, int amount) {
		this.id = new CostoEntrePuntosDeVentaPk(puntoA, puntoB);
		this.amount = amount;
	}

	public CostoEntrePuntosDeVentaPk getId() {
		return id;
	}
	public void setId(CostoEntrePuntosDeVentaPk id) {
		this.id = id;
	}

	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	

}
