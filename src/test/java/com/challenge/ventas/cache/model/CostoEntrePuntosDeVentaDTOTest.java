package com.challenge.ventas.cache.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import utils.BasePojoTests;

class CostoEntrePuntosDeVentaDTOTest extends BasePojoTests {
	
	@Test
	public void testGettersAndSetters() {
		testGettersAndSettersByReflection(CostoEntrePuntosDeVentaDTO.class);
	}
	
	@Test
	public void testOverloadedConstrustor() {
		CostoEntrePuntosDeVentaDTO c = new CostoEntrePuntosDeVentaDTO(1L, "uno", 2L, "dos", 120);
		Assertions.assertEquals(1L, c.getPuntoAId());
		Assertions.assertEquals("uno", c.getPuntoAName());
		Assertions.assertEquals(2L, c.getPuntoBId());
		Assertions.assertEquals("dos", c.getPuntoBName());
		Assertions.assertEquals(120, c.getCosto());
	}

}
