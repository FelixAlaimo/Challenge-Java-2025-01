package com.challenge.ventas.service;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.challenge.ventas.persistence.dto.CostBetweenSellingPointsDTO;
import com.challenge.ventas.persistence.dto.PathResultDTO;

@TestInstance(Lifecycle.PER_CLASS)
class PathFinderServiceTest {
	
	private PathFinderService pathFinder = new PathFinderService();
	
	@Test
	public void testFindShortestPath() {
		CostBetweenSellingPointsDTO cost1 = new CostBetweenSellingPointsDTO(1L, "", 2L, "", 2);
		CostBetweenSellingPointsDTO cost2 = new CostBetweenSellingPointsDTO(1L, "", 3L, "", 3);
		CostBetweenSellingPointsDTO cost3 = new CostBetweenSellingPointsDTO(1L, "", 4L, "", 11);
		CostBetweenSellingPointsDTO cost4 = new CostBetweenSellingPointsDTO(2L, "", 3L, "", 5);
		CostBetweenSellingPointsDTO cost5 = new CostBetweenSellingPointsDTO(2L, "", 4L, "", 10);
		CostBetweenSellingPointsDTO cost6 = new CostBetweenSellingPointsDTO(2L, "", 5L, "", 14);
		CostBetweenSellingPointsDTO cost7 = new CostBetweenSellingPointsDTO(3L, "", 8L, "", 10);
		CostBetweenSellingPointsDTO cost8 = new CostBetweenSellingPointsDTO(4L, "", 5L, "", 5);
		CostBetweenSellingPointsDTO cost9 = new CostBetweenSellingPointsDTO(4L, "", 6L, "", 6);
		CostBetweenSellingPointsDTO cost10 = new CostBetweenSellingPointsDTO(5L, "", 8L, "", 30);
		CostBetweenSellingPointsDTO cost11 = new CostBetweenSellingPointsDTO(6L, "", 7L, "", 32);
		CostBetweenSellingPointsDTO cost12 = new CostBetweenSellingPointsDTO(8L, "", 9L, "", 11);
		CostBetweenSellingPointsDTO cost13 = new CostBetweenSellingPointsDTO(10L, "", 5L, "", 5);
		CostBetweenSellingPointsDTO cost14 = new CostBetweenSellingPointsDTO(10L, "", 7L, "", 5);
		
		List<CostBetweenSellingPointsDTO> existingCosts = Arrays.asList(cost1, cost2, cost3, cost4, cost5, cost6, cost7, cost8, cost9, cost10, cost11, cost12, cost13, cost14);
		PathResultDTO result = pathFinder.findShortestPath(1L, 7L, existingCosts);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(5, result.getSellingPointsPath().size());
		Assertions.assertEquals(26, result.getCost());
		
		// no path
		result = pathFinder.findShortestPath(1L, 111L, existingCosts);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(0, result.getSellingPointsPath().size());
		Assertions.assertEquals(-1, result.getCost());
	}

}
