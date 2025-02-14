package com.challenge.ventas.cache.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import utils.BasePojoTests;

class CostoEntrePuntosDeVentaPkTest extends BasePojoTests {
	
	@Test
	public void testGettersAndSetters() {
		testGettersAndSettersByReflection(CostoEntrePuntosDeVentaPk.class);
	}
	
	@Test
	public void testOverloadedConstrustor() {
		PuntoDeVenta p1 = new PuntoDeVenta();
		PuntoDeVenta p2 = new PuntoDeVenta();
		CostoEntrePuntosDeVentaPk pk = new CostoEntrePuntosDeVentaPk(p1, p2);
		Assertions.assertEquals(p1, pk.getPuntoDeVentaA());
		Assertions.assertEquals(p2, pk.getPuntoDeVentaB());
	}

}
