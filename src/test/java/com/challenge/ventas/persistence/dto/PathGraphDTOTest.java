package com.challenge.ventas.persistence.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.challenge.ventas.test.utils.BasePojoTests;

class PathGraphDTOTest extends BasePojoTests {
	
	@Test
	public void testGettersAndSetters() {
		testGettersAndSettersByReflection(PathGraphDTO.class);
	}
	
	@Test
	public void testAddConnection() {
		PathGraphDTO graphDto = new PathGraphDTO();
		graphDto.addConnection(3L, 8L, 20);
		Assertions.assertEquals(20, graphDto.getGraph().get(8L).get(3L));
		Assertions.assertEquals(20, graphDto.getGraph().get(3L).get(8L));
	}
	

}
