package com.challenge.ventas.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class CostoEntrePuntosDeVentaPk {
	
	private PuntoDeVenta puntoDeVentaA;
	
	private PuntoDeVenta puntoDeVentaB;
	
	public CostoEntrePuntosDeVentaPk() {
		// default empty constructor
	}
	
	public CostoEntrePuntosDeVentaPk(PuntoDeVenta puntoDeVentaA, PuntoDeVenta puntoDeVentaB) {
		this.puntoDeVentaA = puntoDeVentaA;
		this.puntoDeVentaB = puntoDeVentaB;
	}

	public PuntoDeVenta getPuntoDeVentaA() {
		return puntoDeVentaA;
	}
	public void setPuntoDeVentaA(PuntoDeVenta puntoDeVentaA) {
		this.puntoDeVentaA = puntoDeVentaA;
	}

	public PuntoDeVenta getPuntoDeVentaB() {
		return puntoDeVentaB;
	}
	public void setPuntoDeVentaB(PuntoDeVenta puntoDeVentaB) {
		this.puntoDeVentaB = puntoDeVentaB;
	}

}
