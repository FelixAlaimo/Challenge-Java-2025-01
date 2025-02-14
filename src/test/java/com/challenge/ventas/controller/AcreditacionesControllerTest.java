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
import com.challenge.ventas.persistence.model.Acreditacion;
import com.challenge.ventas.persistence.model.AcreditacionDTO;
import com.challenge.ventas.service.VentasService;

@TestInstance(Lifecycle.PER_CLASS)
class AcreditacionesControllerTest {
	
	@Mock
	private VentasService ventasService;
	
	@InjectMocks
	private AcreditacionesController controller;
	
	@BeforeAll
	public void init() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void testGetAcreditaciones() {
		controller.getAcreditaciones();
		Mockito.verify(ventasService, Mockito.times(1)).findAcreditaciones();
	}
	
	@Test
	public void testGuardarAcreditacion() {
		String expectedValidacionInicial = "No se procesó la carga de acreditación. Los campos 'puntoDeVentaId' e 'importe' son requeridos";
		AcreditacionDTO dto = null;
		Assertions.assertEquals(expectedValidacionInicial, controller.guardarAcreditacion(dto));
		
		dto = new AcreditacionDTO();
		Assertions.assertEquals(expectedValidacionInicial, controller.guardarAcreditacion(dto));
		
		dto.setPuntoDeVentaId(100L);
		Assertions.assertEquals(expectedValidacionInicial, controller.guardarAcreditacion(dto));
		
		dto.setImporte(50000L);
		Assertions.assertEquals("El punto de venta enviado no es válido.", controller.guardarAcreditacion(dto));
		
		Mockito.when(ventasService.findPuntoDeVenta(100L)).thenReturn(new PuntoDeVenta("leName"));
		
		controller.guardarAcreditacion(dto);
		ArgumentCaptor<Acreditacion> captor = ArgumentCaptor.forClass(Acreditacion.class);
		Mockito.verify(ventasService, Mockito.times(1)).saveOrUpdateAcreditacion(captor.capture());
		Acreditacion captA = captor.getValue();
		Assertions.assertEquals(100L, captA.getPuntoDeVentaId());
		Assertions.assertEquals("leName", captA.getPuntoDeVentaName());
		Assertions.assertEquals(50000L, captA.getImporte());
	}

}
