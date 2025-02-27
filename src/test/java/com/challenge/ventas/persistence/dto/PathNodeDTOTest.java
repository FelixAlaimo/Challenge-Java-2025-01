package com.challenge.ventas.persistence.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.challenge.ventas.test.utils.BasePojoTests;

class PathNodeDTOTest extends BasePojoTests {
	
	@Test
	public void testGettersAndSetters() {
		testGettersAndSettersByReflection(PathNodeDTO.class);
	}
	
	@Test
	public void testOverloadedConstructor() {
		PathNodeDTO nodeDto = new PathNodeDTO(5L, 33);
		Assertions.assertEquals(5L, nodeDto.getSellingPointId());
		Assertions.assertEquals(33, nodeDto.getCost());
	}

}
