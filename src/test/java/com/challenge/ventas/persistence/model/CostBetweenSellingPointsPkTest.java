package com.challenge.ventas.persistence.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import utils.BasePojoTests;

class CostBetweenSellingPointsPkTest extends BasePojoTests {
	
	@Test
	public void testGettersAndSetters() {
		testGettersAndSettersByReflection(CostBetweenSellingPointsPk.class);
	}
	
	@Test
	public void testOverloadedConstrustor() {
		SellingPoint p1 = new SellingPoint();
		SellingPoint p2 = new SellingPoint();
		CostBetweenSellingPointsPk pk = new CostBetweenSellingPointsPk(p1, p2);
		Assertions.assertEquals(p1, pk.getFromSellingPoint());
		Assertions.assertEquals(p2, pk.getToSellingPoint());
	}

}
