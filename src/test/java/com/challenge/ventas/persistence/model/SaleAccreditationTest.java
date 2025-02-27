package com.challenge.ventas.persistence.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.challenge.ventas.test.utils.BasePojoTests;

class SaleAccreditationTest extends BasePojoTests {
	
	@Test
	public void testGettersAndSetters() {
		testGettersAndSettersByReflection(SaleAccreditation.class);
	}
	
	@Test
	public void testOverloadedConstructor() {
		SellingPoint sellingPoint = new SellingPoint(1857L, "selling Point");
		SaleAccreditation saleAccreditation = new SaleAccreditation(sellingPoint, 25000L);
		Assertions.assertEquals(1857L, saleAccreditation.getSellingPoint().getId());
		Assertions.assertEquals("selling Point", saleAccreditation.getSellingPoint().getName());
		Assertions.assertEquals(25000L, saleAccreditation.getAmount());
		Assertions.assertNotNull(saleAccreditation.getAccreditationDate());
	}
	
	@Test
	public void testToString() {
		SellingPoint sellingPoint = new SellingPoint(1857L, "selling Point");
		SaleAccreditation saleAccreditation = new SaleAccreditation(sellingPoint, 25000L);
		saleAccreditation.setId(739L);
		Assertions.assertEquals("[id: 739, amount: 25000] en el Punto de Venta: " + sellingPoint.toString() , saleAccreditation.toString());
	}

}
