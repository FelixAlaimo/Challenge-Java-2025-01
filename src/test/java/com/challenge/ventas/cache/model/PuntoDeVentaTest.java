package com.challenge.ventas.cache.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import utils.BasePojoTests;

class PuntoDeVentaTest extends BasePojoTests {
	
	@Test
	public void testGettersAndSetters() {
		testGettersAndSettersByReflection(PuntoDeVenta.class);
	}
	
	@Test
	public void testOverloadedConstrustor() {
		PuntoDeVenta p = new PuntoDeVenta(1739L);
		Assertions.assertEquals(1739L, p.getId());
		p = new PuntoDeVenta("pto de venta X");
		Assertions.assertEquals("pto de venta X", p.getName());
	}
	
	@Test
	public void testToString() {
		PuntoDeVenta p = new PuntoDeVenta(222L);
		p.setName("NomBre");
		Assertions.assertEquals("[id: 222, name: 'NomBre']", p.toString());
	}
	
	@Test
	public void testHashAndEquals() {
		PuntoDeVenta p1 = new PuntoDeVenta();
		PuntoDeVenta p2 = new PuntoDeVenta();
		
		Assertions.assertEquals(p1, p1);
		Assertions.assertEquals(p1.hashCode(), p1.hashCode());
		
		Assertions.assertNotEquals(p1, null);
		Assertions.assertNotEquals(p1, new CostoEntrePuntosDeVenta());
		
		Assertions.assertEquals(p1, p2);
		p1.setId(1L);
		Assertions.assertNotEquals(p1, p2);
		p2.setId(3L);
		Assertions.assertNotEquals(p1, p2);
		p1.setId(3L);
		Assertions.assertEquals(p1, p2);
	}

}
