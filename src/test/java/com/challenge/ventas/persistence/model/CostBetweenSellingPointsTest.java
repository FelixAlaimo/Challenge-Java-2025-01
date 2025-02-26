package com.challenge.ventas.persistence.model;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import utils.BasePojoTests;

public class CostBetweenSellingPointsTest extends BasePojoTests {
	
	@Test
	public void testGettersAndSetters() {
		testGettersAndSettersByReflection(CostBetweenSellingPoints.class);
	}
	
	@Test
	public void testOverloadedConstrustor() {
		SellingPoint p1 = new SellingPoint();
		SellingPoint p2 = new SellingPoint();
		CostBetweenSellingPoints cost = new CostBetweenSellingPoints(p1, p2, 373093);
		List<SellingPoint> sellingPoints = Arrays.asList(cost.getId().getFromSellingPoint(), cost.getId().getToSellingPoint());
		Assertions.assertTrue(sellingPoints.contains(p1));
		Assertions.assertTrue(sellingPoints.contains(p2));
		Assertions.assertEquals(373093, cost.getAmount());
	}
	
	@Test
	public void testHashAndEquals() {
		CostBetweenSellingPoints cost1 = new CostBetweenSellingPoints(new SellingPoint(1L, "one"), new SellingPoint(2L, "two"), 5);
		CostBetweenSellingPoints cost2 = new CostBetweenSellingPoints(new SellingPoint(1L, "one"), new SellingPoint(3L, "three"), 33);
		
		Assertions.assertEquals(cost1, cost1);
		Assertions.assertEquals(cost1.hashCode(), cost1.hashCode());
		
		Assertions.assertNotEquals(cost1, null);
		
		Assertions.assertNotEquals(cost1, cost2);
		Assertions.assertNotEquals(cost1.hashCode(), cost2.hashCode());
		
		Assertions.assertNotEquals(cost1, 1L);
		
		cost2.getId().setToSellingPoint(new SellingPoint(2L, "two-v2"));
		Assertions.assertEquals(cost1, cost2);
		Assertions.assertEquals(cost1.hashCode(), cost2.hashCode());
	}

}
