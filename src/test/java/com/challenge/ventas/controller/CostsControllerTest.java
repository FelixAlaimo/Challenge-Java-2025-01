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

import com.challenge.ventas.persistence.dto.CostBetweenSellingPointsDTO;
import com.challenge.ventas.persistence.model.CostBetweenSellingPoints;
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
	public void testGetCostBetweenSellingPoints() {
		String expectedResult = "Warning! revisar campos requeridos: 'id' (de ambos puntos)";
		Assertions.assertEquals(expectedResult, controller.getCostBetweenSellingPoints(null, 17L));
		Assertions.assertEquals(expectedResult, controller.getCostBetweenSellingPoints(17L, null));
		
		expectedResult = "El costo entre un punto y si mismo se presume irrisorio (0)";
		Assertions.assertEquals(expectedResult, controller.getCostBetweenSellingPoints(17L, 17L));
		
		expectedResult = "No se encontró un costo directo entre ambos puntos de venta. Puede ser que no exista o que alguno de los puntos de venta se encuentre borrado.";
		Assertions.assertEquals(expectedResult, controller.getCostBetweenSellingPoints(1L, 2L));
		
		expectedResult = "El costo entre ambos puntos de venta es: 2690";
		Mockito.when(salesService.findCostBetweenSellingPointsDTOByIds(Arrays.asList(1L, 2L))).thenReturn(new CostBetweenSellingPointsDTO(2690));
		Assertions.assertEquals(expectedResult, controller.getCostBetweenSellingPoints(1L, 2L));
	}
	
	@Test
	public void testGetCostBetweenSellingPointsById() {
		List<CostBetweenSellingPointsDTO> results = controller.getCostBetweenSellingPoints(null);
		Assertions.assertNotNull(results);
		Assertions.assertEquals(0, results.size());
		Mockito.verify(salesService, Mockito.times(0)).findCostBetweenSellingPointsDTOById(Mockito.anyLong());
		
		controller.getCostBetweenSellingPoints(429L);
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
		String expectedResult = "Warning! revisar campos requeridos: 'fromSellingPointId', 'toSellingPointId','cost'";
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
		ArgumentCaptor<CostBetweenSellingPoints> captor = ArgumentCaptor.forClass(CostBetweenSellingPoints.class);
		Mockito.verify(salesService, Mockito.times(1)).saveCostBetweenSellingPoints(captor.capture());
		CostBetweenSellingPoints captC = captor.getValue();
		Assertions.assertEquals(88L, captC.getId().getFromSellingPoint().getId());
		Assertions.assertEquals(99L, captC.getId().getToSellingPoint().getId());
		Assertions.assertEquals(300, captC.getAmount());
	}

}
