package com.challenge.ventas.persistence.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.challenge.ventas.test.utils.BasePojoTests;

class AccreditationDTOTest extends BasePojoTests {
	
	@Test
	public void testGettersAndSetters() {
		testGettersAndSettersByReflection(AccreditationDTO.class);
	}
	
	@Test
	public void testOverloadedConstructor() {
		AccreditationDTO accreditationDTO = new AccreditationDTO(25L,  160L, "three", 1500L, "formattedDate");
		Assertions.assertEquals(25L, accreditationDTO.getId());
		Assertions.assertEquals(160L, accreditationDTO.getSellingPointId());
		Assertions.assertEquals("three", accreditationDTO.getSellingPointName());
		Assertions.assertEquals(1500L, accreditationDTO.getAmount());
		Assertions.assertEquals("formattedDate", accreditationDTO.getAccreditationDate());
	}

}
