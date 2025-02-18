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

}
