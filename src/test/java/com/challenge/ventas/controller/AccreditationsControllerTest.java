package com.challenge.ventas.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.challenge.ventas.persistence.dto.AccreditationDTO;
import com.challenge.ventas.persistence.model.SaleAccreditation;
import com.challenge.ventas.persistence.model.SellingPoint;
import com.challenge.ventas.service.SalesService;

@TestInstance(Lifecycle.PER_CLASS)
class AccreditationsControllerTest {
	
	@Mock
	private SalesService salesService;
	
	@InjectMocks
	private AccreditationsController controller;
	
	@BeforeAll
	public void init() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void testGetAccreditations() {
		controller.getAccreditations();
		Mockito.verify(salesService, Mockito.times(1)).findAccreditations();
	}
	
	@Test
	public void testSaveNewAccreditation() {
		String expectedResult = "No se procesó la carga de acreditación. Los campos 'puntoDeVentaId' e 'importe' son requeridos";
		AccreditationDTO dto = null;
		Assertions.assertEquals(expectedResult, controller.saveNewAccreditation(dto));
		
		dto = new AccreditationDTO();
		Assertions.assertEquals(expectedResult, controller.saveNewAccreditation(dto));
		
		dto.setSellingPointId(100L);
		Assertions.assertEquals(expectedResult, controller.saveNewAccreditation(dto));
		
		dto.setAmount(50000L);
		Assertions.assertEquals("El punto de venta enviado no es válido.", controller.saveNewAccreditation(dto));
		
		Mockito.when(salesService.findSellingPoint(100L)).thenReturn(new SellingPoint("leName"));
		
		controller.saveNewAccreditation(dto);
		ArgumentCaptor<SaleAccreditation> captor = ArgumentCaptor.forClass(SaleAccreditation.class);
		Mockito.verify(salesService, Mockito.times(1)).saveAccreditation(captor.capture());
		SaleAccreditation captA = captor.getValue();
		Assertions.assertEquals(100L, captA.getSellingPointId());
		Assertions.assertEquals("leName", captA.getSellingPointName());
		Assertions.assertEquals(50000L, captA.getAmount());
	}

}
