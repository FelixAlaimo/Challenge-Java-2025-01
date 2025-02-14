package com.challenge.ventas.cache.model;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import utils.BasePojoTests;

public class CostoEntrePuntosDeVentaTest extends BasePojoTests {
	
	@Test
	public void testGettersAndSetters() {
		testGettersAndSettersByReflection(CostoEntrePuntosDeVenta.class);
	}
	
	@Test
	public void testOverloadedConstrustor() {
		PuntoDeVenta p1 = new PuntoDeVenta();
		PuntoDeVenta p2 = new PuntoDeVenta();
		CostoEntrePuntosDeVenta c = new CostoEntrePuntosDeVenta(p1, p2, 373093);
		List<PuntoDeVenta> puntos = Arrays.asList(c.getId().getPuntoDeVentaA(), c.getId().getPuntoDeVentaB());
		Assertions.assertTrue(puntos.contains(p1));
		Assertions.assertTrue(puntos.contains(p2));
		Assertions.assertEquals(373093, c.getAmount());
	}

}
