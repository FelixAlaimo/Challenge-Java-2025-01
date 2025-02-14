package com.challenge.ventas.controller;

import java.util.Arrays;
import java.util.List;

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

import com.challenge.ventas.cache.model.CostoEntrePuntosDeVenta;
import com.challenge.ventas.cache.model.CostoEntrePuntosDeVentaDTO;
import com.challenge.ventas.service.VentasService;

@TestInstance(Lifecycle.PER_CLASS)
class CostosControllerTest {
	
	@Mock
	private VentasService ventasService;
	
	@InjectMocks
	private CostosController controller;
	
	@BeforeAll
	public void init() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void testGetPuntosDeVenta() {
		controller.getPuntosDeVenta();
		Mockito.verify(ventasService, Mockito.times(1)).findCostosEntrePuntosDeVentaDto();
	}
	
	@Test
	public void testGetCostoEntrePuntos() {
		String expectedResult = "Para buscar el costo entre dos puntos se requiere el ID de ambos puntos de venta.";
		Assertions.assertEquals(expectedResult, controller.getCostoEntrePuntos(null, 17L));
		Assertions.assertEquals(expectedResult, controller.getCostoEntrePuntos(17L, null));
		
		expectedResult = "El costo entre un punto y si mismo se presume irrisorio (0)";
		Assertions.assertEquals(expectedResult, controller.getCostoEntrePuntos(17L, 17L));
		
		expectedResult = "No se encontró un costo directo entre ambos puntos de venta. Puede ser que no exista o que alguno de los puntos de venta se encuentre borrado.";
		Assertions.assertEquals(expectedResult, controller.getCostoEntrePuntos(1L, 2L));
		
		expectedResult = "El costo entre ambos puntos de venta es: 2690";
		Mockito.when(ventasService.findCostoEntrePuntosDeVentaDto(Arrays.asList(1L, 2L))).thenReturn(new CostoEntrePuntosDeVentaDTO(2690));
		Assertions.assertEquals(expectedResult, controller.getCostoEntrePuntos(1L, 2L));
	}
	
	@Test
	public void testGetCostosHaciaPuntoDeVenta() {
		List<CostoEntrePuntosDeVentaDTO> results = controller.getCostosHaciaPuntoDeVenta(null);
		Assertions.assertNotNull(results);
		Assertions.assertEquals(0, results.size());
		Mockito.verify(ventasService, Mockito.times(0)).findCostosHaciaUnPuntoDeVentaDto(Mockito.anyLong());
		
		controller.getCostosHaciaPuntoDeVenta(429L);
		Mockito.verify(ventasService, Mockito.times(1)).findCostosHaciaUnPuntoDeVentaDto(Mockito.anyLong());
	}
	
	@Test
	public void testDeleteCostoEntrePuntos() {
		String expectedResult = "Para BORRAR el costo entre dos puntos se requiere el ID de ambos puntos de venta.";
		Assertions.assertEquals(expectedResult, controller.deleteCostoEntrePuntos(null, 17L));
		Assertions.assertEquals(expectedResult, controller.deleteCostoEntrePuntos(17L, null));
		
		expectedResult = "No se puede borrar el costo entre un punto y si mismo, ya que se presume irrisorio (0)";
		Assertions.assertEquals(expectedResult, controller.deleteCostoEntrePuntos(17L, 17L));
		Mockito.verify(ventasService, Mockito.times(0)).deleteCostoEntrePuntosDeVenta(Mockito.anyLong(), Mockito.anyLong());
		
		expectedResult = "Solicitud exitosa de borrado de costo entre dos puntos de venta. En caso de existir, sera eliminado";
		Assertions.assertEquals(expectedResult, controller.deleteCostoEntrePuntos(1L, 2L));
		Mockito.verify(ventasService, Mockito.times(1)).deleteCostoEntrePuntosDeVenta(1L, 2L);
	}
	
	@Test
	public void testCreateCostoEntrePuntos() {		
		String expectedResult = "Costo entre Puntos de venta no creado. El atributo 'puntoAId', 'puntoBId' y 'costo' son obligatorio";
		CostoEntrePuntosDeVentaDTO costoDto = null;
		Assertions.assertEquals(expectedResult, controller.createCostoEntrePuntos(costoDto));
		
		costoDto = new CostoEntrePuntosDeVentaDTO();
		Assertions.assertEquals(expectedResult, controller.createCostoEntrePuntos(costoDto));
		
		costoDto.setPuntoAId(88L);
		Assertions.assertEquals(expectedResult, controller.createCostoEntrePuntos(costoDto));
		
		expectedResult = "No se puede cambiar el costo entre un punto de venta y si mismo. Se considerará siempre irrisorio (0)";
		costoDto.setPuntoBId(88L);
		Assertions.assertEquals(expectedResult, controller.createCostoEntrePuntos(costoDto));
		
		expectedResult = "El costo no puede tener un valor negativo";
		costoDto.setPuntoBId(99L);
		costoDto.setCosto(-300);
		Assertions.assertEquals(expectedResult, controller.createCostoEntrePuntos(costoDto));
		
		expectedResult = "Solicitud exitosa de creacion de costo entre dos puntos de venta. En caso de existir, sera actualizado";
		costoDto.setCosto(300);
		Assertions.assertEquals(expectedResult, controller.createCostoEntrePuntos(costoDto));
		ArgumentCaptor<CostoEntrePuntosDeVenta> captor = ArgumentCaptor.forClass(CostoEntrePuntosDeVenta.class);
		Mockito.verify(ventasService, Mockito.times(1)).saveOrUpdateCostoEntrePuntosDeVenta(captor.capture());
		CostoEntrePuntosDeVenta captC = captor.getValue();
		Assertions.assertEquals(88L, captC.getId().getPuntoDeVentaA().getId());
		Assertions.assertEquals(99L, captC.getId().getPuntoDeVentaB().getId());
		Assertions.assertEquals(300, captC.getAmount());
	}

}
