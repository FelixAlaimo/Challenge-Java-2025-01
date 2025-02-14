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

import com.challenge.ventas.cache.model.PuntoDeVenta;
import com.challenge.ventas.cache.model.PuntoDeVentaDTO;
import com.challenge.ventas.service.VentasService;

@TestInstance(Lifecycle.PER_CLASS)
class VentasControllerTest {
	
	@Mock
	private VentasService ventasService;
	
	@InjectMocks
	private VentasController controller;
	
	@BeforeAll
	public void init() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void testGetPuntosDeVenta() {
		controller.getPuntosDeVenta();
		Mockito.verify(ventasService, Mockito.times(1)).findActivePuntosDeVenta();
	}
	
	@Test
	public void testGetPuntoDeVenta() {
		controller.getPuntoDeVenta(475L);
		Mockito.verify(ventasService, Mockito.times(1)).findPuntoDeVentaDto(475L);
	}
	
	@Test
	public void testCreatePuntoDeVenta() {
		PuntoDeVentaDTO puntoDeVentaDTO = null;
		
		String expectedResult = "Punto de venta no creado. El atributo 'name' es obligatorio";
		Assertions.assertEquals(expectedResult, controller.createPuntoDeVenta(puntoDeVentaDTO));
		
		puntoDeVentaDTO = new PuntoDeVentaDTO();
		Assertions.assertEquals(expectedResult, controller.createPuntoDeVenta(puntoDeVentaDTO));
		
		puntoDeVentaDTO.setName("      ");
		Assertions.assertEquals(expectedResult, controller.createPuntoDeVenta(puntoDeVentaDTO));
		
		puntoDeVentaDTO.setName("puntoDeVentaNuevo");
		PuntoDeVenta p = new PuntoDeVenta(18732L);
		p.setName(puntoDeVentaDTO.getName());
		Mockito.when(ventasService.saveOrUpdatePuntoDeVenta(Mockito.any())).thenReturn(p);
		
		expectedResult = "Punto de venta creado OK: [id: 18732, name: 'puntoDeVentaNuevo']";
		Assertions.assertEquals(expectedResult, controller.createPuntoDeVenta(puntoDeVentaDTO));
	}
	
	@Test
	public void testUpdatePuntoDeVenta() {
		Mockito.clearInvocations(ventasService);
		
		PuntoDeVentaDTO puntoDeVentaDTO = null;
		String expectedResult = "Para actualizar un punto de venta, son necesarios tanto el atributo 'name' como 'id'";
		Assertions.assertEquals(expectedResult, controller.updatePuntoDeVenta(puntoDeVentaDTO));
		
		puntoDeVentaDTO = new PuntoDeVentaDTO();
		Assertions.assertEquals(expectedResult, controller.updatePuntoDeVenta(puntoDeVentaDTO));
		
		puntoDeVentaDTO.setId(4184L);
		Assertions.assertEquals(expectedResult, controller.updatePuntoDeVenta(puntoDeVentaDTO));
		
		puntoDeVentaDTO.setName("    ");
		Assertions.assertEquals(expectedResult, controller.updatePuntoDeVenta(puntoDeVentaDTO));
		
		puntoDeVentaDTO.setName("newName");
		Assertions.assertEquals("El punto de venta ingresado no existe o se encuentra borrado", controller.updatePuntoDeVenta(puntoDeVentaDTO));
		
		PuntoDeVenta p = new PuntoDeVenta(puntoDeVentaDTO.getId());
		p.setName("currentName");
		Mockito.when(ventasService.findPuntoDeVenta(Mockito.anyLong())).thenReturn(p);
		
		expectedResult = "Punto de venta actualizado OK: [id: 4184, name: 'newName']";
		Assertions.assertEquals(expectedResult, controller.updatePuntoDeVenta(puntoDeVentaDTO));
		ArgumentCaptor<PuntoDeVenta> captor = ArgumentCaptor.forClass(PuntoDeVenta.class);
		Mockito.verify(ventasService, Mockito.times(1)).saveOrUpdatePuntoDeVenta(captor.capture());
		PuntoDeVenta captP = captor.getValue();
		Assertions.assertEquals(puntoDeVentaDTO.getName(), captP.getName());
		Assertions.assertEquals(puntoDeVentaDTO.getId(), captP.getId());
	}
	
	@Test
	public void testDeletePuntoDeVenta() {
		Mockito.clearInvocations(ventasService);
		
		String expectedResult = "Punto de venta no borrado. El atributo 'id' es obligatorio";
		Assertions.assertEquals(expectedResult, controller.deletePuntoDeVenta(null));
		
		expectedResult = "El punto de venta ingresado no existe o se encuentra borrado";
		Assertions.assertEquals(expectedResult, controller.deletePuntoDeVenta(10L));
		
		PuntoDeVenta puntoDeVenta = new PuntoDeVenta(143L);
		puntoDeVenta.setName("puntoABorrar");
		Mockito.when(ventasService.findPuntoDeVenta(10L)).thenReturn(puntoDeVenta);
		
		expectedResult = "Punto de venta borrado OK: [id: 143, name: 'puntoABorrar']";
		Assertions.assertNull(puntoDeVenta.getDeletedDate());
		Assertions.assertEquals(expectedResult, controller.deletePuntoDeVenta(10L));
		Assertions.assertNotNull(puntoDeVenta.getDeletedDate());
		
		ArgumentCaptor<PuntoDeVenta> captor = ArgumentCaptor.forClass(PuntoDeVenta.class);
		Mockito.verify(ventasService, Mockito.times(1)).saveOrUpdatePuntoDeVenta(captor.capture());
		PuntoDeVenta captP = captor.getValue();
		Assertions.assertNotNull(captP.getDeletedDate());
		Assertions.assertEquals(puntoDeVenta.getName(), captP.getName());
		Assertions.assertEquals(puntoDeVenta.getId(), captP.getId());
	}

}
