package com.challenge.ventas.persistence.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import utils.BasePojoTests;

class SellingPointTest extends BasePojoTests {
	
	@Test
	public void testGettersAndSetters() {
		testGettersAndSettersByReflection(SellingPoint.class);
	}
	
	@Test
	public void testOverloadedConstrustor() {
		SellingPoint sellingPoint = new SellingPoint(1739L);
		Assertions.assertEquals(1739L, sellingPoint.getId());
		sellingPoint = new SellingPoint("selling POINT X");
		Assertions.assertEquals("selling POINT X", sellingPoint.getName());
	}
	
	@Test
	public void testToString() {
		SellingPoint sellingPoint = new SellingPoint(222L);
		sellingPoint.setName("NaMe");
		Assertions.assertEquals("[id: 222, name: 'NaMe']", sellingPoint.toString());
	}
	
	@Test
	public void testHashAndEquals() {
		SellingPoint sellingPoint1 = new SellingPoint();
		SellingPoint sellingPoint2 = new SellingPoint();
		
		Assertions.assertEquals(sellingPoint1, sellingPoint1);
		Assertions.assertEquals(sellingPoint1.hashCode(), sellingPoint1.hashCode());
		
		Assertions.assertNotEquals(sellingPoint1, null);
		Assertions.assertNotEquals(sellingPoint1, new CostBetweenSellingPoints());
		
		Assertions.assertEquals(sellingPoint1, sellingPoint2);
		sellingPoint1.setId(1L);
		Assertions.assertNotEquals(sellingPoint1, sellingPoint2);
		sellingPoint2.setId(3L);
		Assertions.assertNotEquals(sellingPoint1, sellingPoint2);
		sellingPoint1.setId(3L);
		Assertions.assertEquals(sellingPoint1, sellingPoint2);
	}

}
