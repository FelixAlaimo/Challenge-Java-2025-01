package com.challenge.ventas.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.challenge.ventas.persistence.dto.AccreditationDTO;
import com.challenge.ventas.persistence.model.SaleAccreditation;
import com.challenge.ventas.persistence.model.SellingPoint;

class AccreditationConverterTest {
	
	@Test
	public void testToDTO_ShouldReturnNull_WhenNullEntity() {
		Assertions.assertNull(AccreditationConverter.toDTO(null));
	}
	
	@Test
	public void testToDTO_ShouldThrowException_WhenMissingOneSellingPointOrAmount() {
		SaleAccreditation saleAccreditation = new SaleAccreditation(new SellingPoint(), null);
		
		Exception exception = Assertions.assertThrows(IllegalStateException.class, () -> {
			AccreditationConverter.toDTO(saleAccreditation);
		});
		Assertions.assertEquals("Falla en la integridad de los datos de esta operacion.", exception.getMessage());
		
		saleAccreditation.setSellingPoint(null);
		saleAccreditation.setAmount(36L);
		exception = Assertions.assertThrows(IllegalStateException.class, () -> {
			AccreditationConverter.toDTO(saleAccreditation);
        });
        Assertions.assertEquals("Falla en la integridad de los datos de esta operacion.", exception.getMessage());
	}
	
	@Test
	public void testToDTO_ShouldReturnBuiltDTO_WhenEntityIsOk() {
		SaleAccreditation saleAccreditation = new SaleAccreditation(new SellingPoint(483L, "name"), 13500L);
		saleAccreditation.setId(1234L);
		Date fixedTestingDate = Date.from(LocalDate.of(2025, 02, 27).atStartOfDay(ZoneId.systemDefault()).toInstant());
		saleAccreditation.setAccreditationDate(fixedTestingDate);
		
		AccreditationDTO result = AccreditationConverter.toDTO(saleAccreditation);
		Assertions.assertEquals(saleAccreditation.getSellingPoint().getId(), result.getSellingPointId());
		Assertions.assertEquals(saleAccreditation.getSellingPoint().getName(), result.getSellingPointName());
		Assertions.assertEquals(saleAccreditation.getAmount(), result.getAmount());
		Assertions.assertEquals("Feb 27, 2025 00:00", result.getAccreditationDate());
	}

}
