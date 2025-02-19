package com.challenge.ventas.persistence.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import utils.BasePojoTests;

class SaleAccreditationTest extends BasePojoTests {
	
	@Test
	public void testGettersAndSetters() {
		testGettersAndSettersByReflection(SaleAccreditation.class);
	}
	
	@Test
	public void testOverloadedConstrustor() {
		SaleAccreditation saleAccreditation = new SaleAccreditation(1857L, "selling Point", 25000L);
		Assertions.assertEquals(1857L, saleAccreditation.getSellingPointId());
		Assertions.assertEquals("selling Point", saleAccreditation.getSellingPointName());
		Assertions.assertEquals(25000L, saleAccreditation.getAmount());
		Assertions.assertNotNull(saleAccreditation.getAccreditationDate());
	}

}
