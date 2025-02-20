package com.challenge.ventas.controller;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.challenge.ventas.persistence.dto.CostBetweenSellingPointsDTO;
import com.challenge.ventas.persistence.dto.PathResultDTO;
import com.challenge.ventas.persistence.dto.SellingPointDTO;
import com.challenge.ventas.service.SalesService;

@TestInstance(Lifecycle.PER_CLASS)
class CostsControllerTest {
	
	@Mock
	private SalesService salesService;
	
	@InjectMocks
	private CostsController controller;
	
	@BeforeAll
	public void init() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void testGetSellingPoints() {
		controller.getSellingPoints();
		Mockito.verify(salesService, Mockito.times(1)).findCostBetweenSellingPointsDTO();
	}
	
	@Test
	public void testGetDirectCostBetweenSellingPoints() {
		String expectedResult = "Warning! revisar campos requeridos: 'id' (de ambos puntos)";
		Assertions.assertEquals(expectedResult, controller.getDirectCostBetweenSellingPoints(null, 17L));
		Assertions.assertEquals(expectedResult, controller.getDirectCostBetweenSellingPoints(17L, null));
		
		expectedResult = "El costo entre un punto y si mismo se presume irrisorio (0)";
		Assertions.assertEquals(expectedResult, controller.getDirectCostBetweenSellingPoints(17L, 17L));
		
		expectedResult = "No se encontró un costo directo entre ambos puntos de venta. Puede ser que no exista o que alguno de los puntos de venta se encuentre borrado.";
		Assertions.assertEquals(expectedResult, controller.getDirectCostBetweenSellingPoints(1L, 2L));
		
		expectedResult = "El costo del camino DIRECTO entre ambos puntos de venta es: 2690";
		Mockito.when(salesService.findDirectCostBetweenSellingPointsDTOByIds(1L, 2L)).thenReturn(new CostBetweenSellingPointsDTO(2690));
		Assertions.assertEquals(expectedResult, controller.getDirectCostBetweenSellingPoints(1L, 2L));
	}
	
	@Test
	public void testGetShortestCostBetweenSellingPoints() {
		String expectedResult = "Warning! revisar campos requeridos: 'id' (de ambos puntos)";
		Assertions.assertEquals(expectedResult, controller.getShortestCostBetweenSellingPoints(null, 17L));
		Assertions.assertEquals(expectedResult, controller.getShortestCostBetweenSellingPoints(17L, null));
		
		expectedResult = "Lon puntos ingresados no poseen conexión";
		Assertions.assertEquals(expectedResult, controller.getShortestCostBetweenSellingPoints(7L, 2L));
		
		PathResultDTO mockedResultDto = new PathResultDTO();
		Mockito.when(salesService.findShortestCostBetweenSellingPointsDTOByIds(7L, 2L)).thenReturn(mockedResultDto);
		Assertions.assertEquals(expectedResult, controller.getShortestCostBetweenSellingPoints(7L, 2L));
		
		Mockito.when(salesService.findSellingPointDto(7L)).thenReturn(new SellingPointDTO(7L, "Point7name"));
		Mockito.when(salesService.findSellingPointDto(3L)).thenReturn(new SellingPointDTO(3L, "Point3name"));
		Mockito.when(salesService.findSellingPointDto(5L)).thenReturn(new SellingPointDTO(5L, "Point5name"));
		Mockito.when(salesService.findSellingPointDto(2L)).thenReturn(new SellingPointDTO(2L, "Point2name"));
		
		mockedResultDto.setSellingPointsPath(Arrays.asList(7L, 3L, 5L, 2L));
		mockedResultDto.setCost(82);
		expectedResult = "El camino de menor costo es Point7name -> Point3name -> Point5name -> Point2name con costo 82";
		Assertions.assertEquals(expectedResult, controller.getShortestCostBetweenSellingPoints(7L, 2L));
	}
	
	@Test
	public void testGetDirectCostBetweenSellingPointsById() {
		List<CostBetweenSellingPointsDTO> results = controller.getDirectCostBetweenSellingPoints(null);
		Assertions.assertNotNull(results);
		Assertions.assertEquals(0, results.size());
		Mockito.verify(salesService, Mockito.times(0)).findCostBetweenSellingPointsDTOById(Mockito.anyLong());
		
		controller.getDirectCostBetweenSellingPoints(429L);
		Mockito.verify(salesService, Mockito.times(1)).findCostBetweenSellingPointsDTOById(Mockito.anyLong());
	}
	
	@Test
	public void testDeleteCostBetweenSellingPoints() {
		String expectedResult = "Warning! revisar campos requeridos: 'id' (de ambos puntos)";
		Assertions.assertEquals(expectedResult, controller.deleteCostBetweenSellingPoints(null, 17L));
		Assertions.assertEquals(expectedResult, controller.deleteCostBetweenSellingPoints(17L, null));
		
		expectedResult = "No se puede borrar el costo entre un punto y si mismo, ya que se presume irrisorio (0)";
		Assertions.assertEquals(expectedResult, controller.deleteCostBetweenSellingPoints(17L, 17L));
		Mockito.verify(salesService, Mockito.times(0)).deleteCostBetweenSellingPoints(Mockito.anyLong(), Mockito.anyLong());
		
		expectedResult = "Solicitud exitosa de borrado de costo entre dos puntos de venta. En caso de existir, sera eliminado";
		Assertions.assertEquals(expectedResult, controller.deleteCostBetweenSellingPoints(1L, 2L));
		Mockito.verify(salesService, Mockito.times(1)).deleteCostBetweenSellingPoints(1L, 2L);
	}
	
	@Test
	public void testCreateCostBetweenSellingPoints() {		
		String expectedResult = "Warning! revisar campos requeridos: 'fromSellingPointId', 'toSellingPointId', 'cost'";
		CostBetweenSellingPointsDTO costDto = null;
		Assertions.assertEquals(expectedResult, controller.createCostBetweenSellingPoints(costDto));
		
		costDto = new CostBetweenSellingPointsDTO();
		Assertions.assertEquals(expectedResult, controller.createCostBetweenSellingPoints(costDto));
		
		costDto.setFromSellingPointId(88L);
		Assertions.assertEquals(expectedResult, controller.createCostBetweenSellingPoints(costDto));
		
		expectedResult = "No se puede cambiar el costo entre un punto de venta y si mismo. Se considerará siempre irrisorio (0)";
		costDto.setToSellingPointId(88L);
		Assertions.assertEquals(expectedResult, controller.createCostBetweenSellingPoints(costDto));
		
		expectedResult = "El costo no puede tener un valor negativo";
		costDto.setToSellingPointId(99L);
		costDto.setCost(-300);
		Assertions.assertEquals(expectedResult, controller.createCostBetweenSellingPoints(costDto));
		
		expectedResult = "Solicitud exitosa de creacion de costo entre dos puntos de venta. En caso de existir, sera actualizado";
		costDto.setCost(300);
		Assertions.assertEquals(expectedResult, controller.createCostBetweenSellingPoints(costDto));
		Mockito.verify(salesService, Mockito.times(1)).saveCostBetweenSellingPoints(88L, 99L, 300);
	}

}
