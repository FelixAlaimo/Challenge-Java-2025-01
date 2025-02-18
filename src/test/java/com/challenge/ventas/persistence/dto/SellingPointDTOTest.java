package com.challenge.ventas.persistence.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import utils.BasePojoTests;

class SellingPointDTOTest extends BasePojoTests {
	
	@Test
	public void testGettersAndSetters() {
		testGettersAndSettersByReflection(SellingPointDTO.class);
	}
	
	@Test
	public void testOverloadedConstructor() {
		SellingPointDTO p = new SellingPointDTO(123L, "puntoDTO");
		Assertions.assertEquals(123L, p.getId());
		Assertions.assertEquals("puntoDTO", p.getName());
	}

}
