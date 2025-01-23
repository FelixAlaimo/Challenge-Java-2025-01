package com.challenge.ventas.model;

import java.io.Serializable;

public class AcreditacionDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long puntoDeVentaId;
	
	private Long importe;

	public Long getPuntoDeVentaId() {
		return puntoDeVentaId;
	}
	public void setPuntoDeVentaId(Long puntoDeVentaId) {
		this.puntoDeVentaId = puntoDeVentaId;
	}

	public Long getImporte() {
		return importe;
	}
	public void setImporte(Long importe) {
		this.importe = importe;
	}

}
