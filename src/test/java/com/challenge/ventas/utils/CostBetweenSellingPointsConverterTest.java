package com.challenge.ventas.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.challenge.ventas.persistence.dto.CostBetweenSellingPointsDTO;
import com.challenge.ventas.persistence.model.CostBetweenSellingPoints;
import com.challenge.ventas.persistence.model.SellingPoint;

class CostBetweenSellingPointsConverterTest {
	
	@Test
	public void testToDTO_ShouldReturnNull_WhenNullEntityOrEmptyId() {
		Assertions.assertNull(CostBetweenSellingPointsConverter.toDTO(null));
		Assertions.assertNull(CostBetweenSellingPointsConverter.toDTO(new CostBetweenSellingPoints()));
	}
	
	@Test
	public void testToDTO_ShouldThrowException_WhenMissingOnePkField() {
		CostBetweenSellingPoints costWithThoutPkIntegrity = new CostBetweenSellingPoints(new SellingPoint(), null, 3);
		Exception exception = Assertions.assertThrows(IllegalStateException.class, () -> {
			CostBetweenSellingPointsConverter.toDTO(costWithThoutPkIntegrity);
        });
        Assertions.assertEquals("Falla en la integridad de los datos de esta operacion.", exception.getMessage());
	}
	
	@Test
	public void testToDTO_ShouldReturnBuiltDTO_WhenEntityIsOk() {
		SellingPoint sellingPoint1 = new SellingPoint(12L, "twelve");
		SellingPoint sellingPoint2 = new SellingPoint(24L, "twenty-four");
		CostBetweenSellingPoints costWithThoutPkIntegrity = new CostBetweenSellingPoints(sellingPoint1, sellingPoint2, 15);
		
		CostBetweenSellingPointsDTO result = CostBetweenSellingPointsConverter.toDTO(costWithThoutPkIntegrity);
		Assertions.assertEquals(sellingPoint1.getId(), result.getFromSellingPointId());
		Assertions.assertEquals(sellingPoint1.getName(), result.getFromSellingPointName());
		Assertions.assertEquals(sellingPoint2.getId(), result.getToSellingPointId());
		Assertions.assertEquals(sellingPoint2.getName(), result.getToSellingPointName());
		Assertions.assertEquals(costWithThoutPkIntegrity.getAmount(), result.getCost());
	}

}
