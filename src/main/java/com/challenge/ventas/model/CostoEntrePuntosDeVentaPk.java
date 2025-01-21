package com.challenge.ventas.model;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class CostoEntrePuntosDeVentaPk implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
    @JoinColumn(name = "punto_de_venta_a_id")
	private PuntoDeVenta puntoDeVentaA;
	
	@ManyToOne
    @JoinColumn(name = "punto_de_venta_b_id")
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
