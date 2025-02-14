package com.challenge.ventas.cache.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import utils.BasePojoTests;

class PuntoDeVentaDTOTest extends BasePojoTests {
	
	@Test
	public void testGettersAndSetters() {
		testGettersAndSettersByReflection(PuntoDeVentaDTO.class);
	}
	
	@Test
	public void testOverloadedConstrustor() {
		PuntoDeVentaDTO p = new PuntoDeVentaDTO(123L, "puntoDTO");
		Assertions.assertEquals(123L, p.getId());
		Assertions.assertEquals("puntoDTO", p.getName());
	}

}
