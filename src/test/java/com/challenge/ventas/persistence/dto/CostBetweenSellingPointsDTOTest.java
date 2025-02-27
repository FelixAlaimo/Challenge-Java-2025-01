package com.challenge.ventas.persistence.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.challenge.ventas.test.utils.BasePojoTests;

class CostBetweenSellingPointsDTOTest extends BasePojoTests {
	
	@Test
	public void testGettersAndSetters() {
		testGettersAndSettersByReflection(CostBetweenSellingPointsDTO.class);
	}
	
	@Test
	public void testOverloadedConstrustor() {
		CostBetweenSellingPointsDTO c = new CostBetweenSellingPointsDTO(1L, "uno", 2L, "dos", 120);
		Assertions.assertEquals(1L, c.getFromSellingPointId());
		Assertions.assertEquals("uno", c.getFromSellingPointName());
		Assertions.assertEquals(2L, c.getToSellingPointId());
		Assertions.assertEquals("dos", c.getToSellingPointName());
		Assertions.assertEquals(120, c.getCost());
	}

}
