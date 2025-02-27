package com.challenge.ventas.persistence.dto;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.challenge.ventas.test.utils.BasePojoTests;

class PathResultDTOTest extends BasePojoTests {
	
	@Test
	public void testGettersAndSetters() {
		testGettersAndSettersByReflection(PathResultDTO.class);
	}
	
	@Test
	public void testOverloadedConstructor() {
		List<Long> sellingPointsPath = Arrays.asList(3L, 8L, 33L);
		PathResultDTO resultDto = new PathResultDTO(sellingPointsPath, 927);
		Assertions.assertEquals(sellingPointsPath, resultDto.getSellingPointsPath());
		Assertions.assertEquals(927, resultDto.getCost());
	}

}
