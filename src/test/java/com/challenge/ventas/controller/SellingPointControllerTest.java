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

import com.challenge.ventas.persistence.dto.SellingPointDTO;
import com.challenge.ventas.persistence.model.SellingPoint;
import com.challenge.ventas.service.SalesService;

@TestInstance(Lifecycle.PER_CLASS)
class SellingPointControllerTest {
	
	@Mock
	private SalesService salesService;
	
	@InjectMocks
	private SellingPointController controller;
	
	@BeforeAll
	public void init() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void testGetAllSellingPoints() {
		controller.getAllSellingPoints();
		Mockito.verify(salesService, Mockito.times(1)).findActiveSellingPoint();
	}
	
	@Test
	public void testGetSellingPoint() {
		controller.getSellingPoint(475L);
		Mockito.verify(salesService, Mockito.times(1)).findSellingPointDto(475L);
	}
	
	@Test
	public void testCreateSellingPoint() {
		SellingPointDTO sellingPointDTO = null;
		
		String expectedResult = "Warning! revisar campos requeridos: 'name'";
		Assertions.assertEquals(expectedResult, controller.createSellingPoint(sellingPointDTO));
		
		sellingPointDTO = new SellingPointDTO();
		Assertions.assertEquals(expectedResult, controller.createSellingPoint(sellingPointDTO));
		
		sellingPointDTO.setName("      ");
		Assertions.assertEquals(expectedResult, controller.createSellingPoint(sellingPointDTO));
		
		sellingPointDTO.setName("puntoDeVentaNuevo");
		SellingPoint sellingPoint = new SellingPoint(18732L);
		sellingPoint.setName(sellingPointDTO.getName());
		Mockito.when(salesService.saveOrUpdateSellingPoint(Mockito.any())).thenReturn(sellingPoint);
		
		expectedResult = "Punto de venta creado OK: [id: 18732, name: 'puntoDeVentaNuevo']";
		Assertions.assertEquals(expectedResult, controller.createSellingPoint(sellingPointDTO));
	}
	
	@Test
	public void testUpdateSellingPoint() {
		Mockito.clearInvocations(salesService);
		
		SellingPointDTO sellingPointDTO = null;
		String expectedResult = "Warning! revisar campos requeridos: 'name', 'id'";
		Assertions.assertEquals(expectedResult, controller.updateSellingPoint(sellingPointDTO));
		
		sellingPointDTO = new SellingPointDTO();
		Assertions.assertEquals(expectedResult, controller.updateSellingPoint(sellingPointDTO));
		
		sellingPointDTO.setId(4184L);
		Assertions.assertEquals(expectedResult, controller.updateSellingPoint(sellingPointDTO));
		
		sellingPointDTO.setName("    ");
		Assertions.assertEquals(expectedResult, controller.updateSellingPoint(sellingPointDTO));
		
		sellingPointDTO.setName("newName");
		Assertions.assertEquals("El punto de venta ingresado no existe o se encuentra borrado", controller.updateSellingPoint(sellingPointDTO));
		
		SellingPoint sellingPoint = new SellingPoint(sellingPointDTO.getId());
		sellingPoint.setName("currentName");
		Mockito.when(salesService.findSellingPoint(Mockito.anyLong())).thenReturn(sellingPoint);
		
		expectedResult = "Punto de venta actualizado OK: [id: 4184, name: 'newName']";
		Assertions.assertEquals(expectedResult, controller.updateSellingPoint(sellingPointDTO));
		ArgumentCaptor<SellingPoint> captor = ArgumentCaptor.forClass(SellingPoint.class);
		Mockito.verify(salesService, Mockito.times(1)).saveOrUpdateSellingPoint(captor.capture());
		SellingPoint captP = captor.getValue();
		Assertions.assertEquals(sellingPointDTO.getName(), captP.getName());
		Assertions.assertEquals(sellingPointDTO.getId(), captP.getId());
	}
	
	@Test
	public void testRemoveSellingPoint() {
		Mockito.clearInvocations(salesService);
		
		String expectedResult = "Warning! revisar campos requeridos: 'id'";
		Assertions.assertEquals(expectedResult, controller.removeSellingPoint(null));
		
		expectedResult = "El punto de venta ingresado no existe o se encuentra borrado";
		Assertions.assertEquals(expectedResult, controller.removeSellingPoint(10L));
		
		SellingPoint sellingPoint = new SellingPoint(143L);
		sellingPoint.setName("puntoABorrar");
		Mockito.when(salesService.findSellingPoint(10L)).thenReturn(sellingPoint);
		
		expectedResult = "Punto de venta borrado OK: [id: 143, name: 'puntoABorrar']";
		Assertions.assertNull(sellingPoint.getDeletedDate());
		Assertions.assertEquals(expectedResult, controller.removeSellingPoint(10L));
		Assertions.assertNotNull(sellingPoint.getDeletedDate());
		
		ArgumentCaptor<SellingPoint> captor = ArgumentCaptor.forClass(SellingPoint.class);
		Mockito.verify(salesService, Mockito.times(1)).saveOrUpdateSellingPoint(captor.capture());
		SellingPoint captP = captor.getValue();
		Assertions.assertNotNull(captP.getDeletedDate());
		Assertions.assertEquals(sellingPoint.getName(), captP.getName());
		Assertions.assertEquals(sellingPoint.getId(), captP.getId());
	}

}
