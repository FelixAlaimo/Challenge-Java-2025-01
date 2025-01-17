package com.challenge.ventas.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class CostoEntrePuntosDeVenta {
	
	@EmbeddedId
	private CostoEntrePuntosDeVentaPk id;
	
	private int amount;
	
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
