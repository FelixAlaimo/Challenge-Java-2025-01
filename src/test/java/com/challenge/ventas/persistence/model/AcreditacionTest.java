package com.challenge.ventas.persistence.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import utils.BasePojoTests;

class AcreditacionTest extends BasePojoTests {
	
	@Test
	public void testGettersAndSetters() {
		testGettersAndSettersByReflection(Acreditacion.class);
	}
	
	@Test
	public void testOverloadedConstrustor() {
		Acreditacion ac = new Acreditacion(1857L, "punto de venta", 25000L);
		Assertions.assertEquals(1857L, ac.getPuntoDeVentaId());
		Assertions.assertEquals("punto de venta", ac.getPuntoDeVentaName());
		Assertions.assertEquals(25000L, ac.getImporte());
		Assertions.assertNotNull(ac.getAcreditacionDate());
	}

}
