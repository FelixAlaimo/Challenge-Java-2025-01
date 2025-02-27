package com.challenge.ventas.persistence.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.challenge.ventas.test.utils.BasePojoTests;

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
	
	@Test
	public void testHashAndEquals() {
		CostBetweenSellingPointsPk pk1 = new CostBetweenSellingPointsPk(new SellingPoint(1L, "one"), new SellingPoint(2L, "two"));
		CostBetweenSellingPointsPk pk2 = new CostBetweenSellingPointsPk(new SellingPoint(1L, "one"), new SellingPoint(3L, "three"));
		
		Assertions.assertEquals(pk1, pk1);
		Assertions.assertEquals(pk1.hashCode(), pk1.hashCode());
		
		Assertions.assertNotEquals(pk1, null);
		
		Assertions.assertNotEquals(pk1, pk2);
		Assertions.assertNotEquals(pk1.hashCode(), pk2.hashCode());
		
		Assertions.assertNotEquals(pk1, 1L);
		
		pk2.setToSellingPoint(new SellingPoint(2L, "two-v2"));
		Assertions.assertEquals(pk1, pk2);
		Assertions.assertEquals(pk1.hashCode(), pk2.hashCode());
	}

}
