package com.challenge.ventas.service.impl;

import java.util.Collections;
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
import org.springframework.data.domain.PageRequest;

import com.challenge.ventas.exception.BusinessRuleException;
import com.challenge.ventas.exception.MissingRequiredFieldException;
import com.challenge.ventas.exception.ResourceNotFoundException;
import com.challenge.ventas.persistence.dto.CostBetweenSellingPointsDTO;
import com.challenge.ventas.persistence.model.CostBetweenSellingPoints;
import com.challenge.ventas.persistence.model.SellingPoint;
import com.challenge.ventas.persistence.repository.ICostBetweenSellingPointsRepository;
import com.challenge.ventas.service.ISellingPointService;
import com.challenge.ventas.utils.SellingPointValidator;

@TestInstance(Lifecycle.PER_CLASS)
class CostsBetweenSellingPointsServiceTest {
	
	@Mock
	private ICostBetweenSellingPointsRepository costsRepo;
	
	@Mock
	private ISellingPointService sellingPointService;

	@Mock
	private SellingPointValidator sellingPointValidator;
	
	@InjectMocks
	private CostsBetweenSellingPointsService service;
	
	@BeforeAll
	public void init() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void testFindCostBetweenSellingPointsDTOs_shouldReturnResults_whenDataIsAvailable() {
		Mockito.when(costsRepo.findCostBetweenSellingPointsDTO()).thenReturn(List.of(
				new CostBetweenSellingPointsDTO(1L, "one", 3L, "three", 10),
				new CostBetweenSellingPointsDTO(2L, "two", 4L, "four", 15)));
		List<CostBetweenSellingPointsDTO> result = service.findCostBetweenSellingPointsDTOs();
		Assertions.assertEquals(2, result.size());
		Assertions.assertEquals(1L, result.get(0).getFromSellingPointId());
		Assertions.assertEquals("one", result.get(0).getFromSellingPointName());
		Assertions.assertEquals(3L, result.get(0).getToSellingPointId());
		Assertions.assertEquals("three", result.get(0).getToSellingPointName());
		Assertions.assertEquals(10, result.get(0).getCost());
		Assertions.assertEquals(2L, result.get(1).getFromSellingPointId());
		Assertions.assertEquals("two", result.get(1).getFromSellingPointName());
		Assertions.assertEquals(4L, result.get(1).getToSellingPointId());
		Assertions.assertEquals("four", result.get(1).getToSellingPointName());
		Assertions.assertEquals(15, result.get(1).getCost());
	}
	
	@Test
	public void testFindCostBetweenSellingPointsDTOs_shouldThrowException_whenNoDataIsAvailable() {
		Mockito.when(costsRepo.findCostBetweenSellingPointsDTO()).thenReturn(Collections.emptyList());
		Exception exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.findCostBetweenSellingPointsDTOs();
		});
		Assertions.assertEquals("Actualmente no se posee informacion sobre costos entre puntos de venta", exception.getMessage());
	}
	
	@Test
	public void testFindCostBetweenSellingPointsDTOById_shouldReturnResults_whenDataIsAvailable() {
		Mockito.when(costsRepo.findCostBetweenSellingPointsDTO(1L)).thenReturn(List.of(
				new CostBetweenSellingPointsDTO(1L, "one", 3L, "three", 10),
				new CostBetweenSellingPointsDTO(2L, "two", 1L, "one", 11)));
		
		List<CostBetweenSellingPointsDTO> result = service.findCostBetweenSellingPointsDTOById(1L);
		Assertions.assertEquals(2, result.size());
		Assertions.assertEquals(1L, result.get(0).getFromSellingPointId());
		Assertions.assertEquals("one", result.get(0).getFromSellingPointName());
		Assertions.assertEquals(3L, result.get(0).getToSellingPointId());
		Assertions.assertEquals("three", result.get(0).getToSellingPointName());
		Assertions.assertEquals(10, result.get(0).getCost());
		Assertions.assertEquals(2L, result.get(1).getFromSellingPointId());
		Assertions.assertEquals("two", result.get(1).getFromSellingPointName());
		Assertions.assertEquals(1L, result.get(1).getToSellingPointId());
		Assertions.assertEquals("one", result.get(1).getToSellingPointName());
		Assertions.assertEquals(11, result.get(1).getCost());
	}
	
	@Test
	public void testFindCostBetweenSellingPointsDTOById_shouldThrowException_whenPointDoesNotExist() {
		Mockito.doThrow(new ResourceNotFoundException("not found warning 1")).when(sellingPointValidator).validateSellingPointExistence(2L, "id");
		Exception exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.findCostBetweenSellingPointsDTOById(2L);
		});
		Assertions.assertEquals("not found warning 1", exception.getMessage());
	}
	
	@Test
	public void testFindCostBetweenSellingPointsDTOById_shouldThrowException_whenNoDataIsAvailable() {
		Mockito.when(costsRepo.findCostBetweenSellingPointsDTO(3L)).thenReturn(Collections.emptyList());
		Exception exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.findCostBetweenSellingPointsDTOById(3L);
		});
		Assertions.assertEquals("No se encontraron costos hacia el punto de venta '3'", exception.getMessage());
	}
	
	
	@Test
	public void testFindDirectCostBetweenSellingPointsDTOByIds_shouldReturnResult_whenFound() {
		Mockito.doNothing().when(sellingPointValidator).validateSellingPointExistence(Mockito.anyLong(), Mockito.anyString());
		Mockito.when(costsRepo.findCostBetweenSellingPoints(1L, 2L, PageRequest.of(0, 1))).thenReturn(List.of(
				new CostBetweenSellingPoints(new SellingPoint(1L, "one"), new SellingPoint(2L, "two"), 7)));
		
		
		CostBetweenSellingPointsDTO cost = service.findDirectCostBetweenSellingPointsDTOByIds(1L, 2L);
		Assertions.assertEquals(1L, cost.getFromSellingPointId());
		Assertions.assertEquals("one", cost.getFromSellingPointName());
		Assertions.assertEquals(2L, cost.getToSellingPointId());
		Assertions.assertEquals("two", cost.getToSellingPointName());
		Assertions.assertEquals(7, cost.getCost());
	}
	
	@Test
	public void testFindDirectCostBetweenSellingPointsDTOByIds_shouldReturnZeroCost_whenFromAndToEquals() {
		Mockito.doNothing().when(sellingPointValidator).validateSellingPointExistence(Mockito.anyLong(), Mockito.anyString());
		CostBetweenSellingPointsDTO cost = service.findDirectCostBetweenSellingPointsDTOByIds(1L, 1L);
		Assertions.assertEquals(0, cost.getCost());
	}
	
	@Test
	public void testFindDirectCostBetweenSellingPointsDTOByIds_shouldThrowException_whenPointDoesNotExist() {
		Mockito.doThrow(new ResourceNotFoundException("not found warning 2")).when(sellingPointValidator).validateSellingPointExistence(2L, "id");
		Exception exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.findDirectCostBetweenSellingPointsDTOByIds(1L, 2L);
		});
		Assertions.assertEquals("not found warning 2", exception.getMessage());
	}
	
	@Test
	public void testFindDirectCostBetweenSellingPointsDTOByIds_shouldThrowException_whenNoDataIsAvailable() {
		Mockito.doNothing().when(sellingPointValidator).validateSellingPointExistence(Mockito.anyLong(), Mockito.anyString());
		Mockito.when(costsRepo.findCostBetweenSellingPoints(1L, 2L, PageRequest.of(0, 1))).thenReturn(Collections.emptyList());
		Exception exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.findDirectCostBetweenSellingPointsDTOByIds(1L, 2L);
		});
		Assertions.assertEquals("No se encontro un costo entre '1' y '2'", exception.getMessage());
	}
	
	@Test
	public void testDeleteCostBetweenSellingPoints_shouldRemoveCostBetweenPoints_whenNoValidationErrorsOcurr() {
		Mockito.reset(costsRepo);
		Mockito.doNothing().when(sellingPointValidator).validateSellingPointExistence(Mockito.anyLong(), Mockito.anyString());
		Mockito.when(costsRepo.findCostBetweenSellingPoints(3L, 4L, PageRequest.of(0, 1))).thenReturn(List.of(
				new CostBetweenSellingPoints(new SellingPoint(4L, "four"), new SellingPoint(3L, "three"), 18)));
		
		service.deleteCostBetweenSellingPoints(3L, 4L);
		ArgumentCaptor<CostBetweenSellingPoints> captor = ArgumentCaptor.forClass(CostBetweenSellingPoints.class);
		Mockito.verify(costsRepo, Mockito.times(1)).delete(captor.capture());
		CostBetweenSellingPoints captCost = captor.getValue();
		Assertions.assertEquals(4L, captCost.getId().getFromSellingPoint().getId());
		Assertions.assertEquals(3L, captCost.getId().getToSellingPoint().getId());
		Assertions.assertEquals(18, captCost.getAmount());
	}
	
	@Test
	public void testDeleteCostBetweenSellingPoints_shouldThrowException_whenPointDoesNotExist() {
		Mockito.doThrow(new ResourceNotFoundException("not found warning 3")).when(sellingPointValidator).validateSellingPointExistence(5L, "id");
		Exception exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.deleteCostBetweenSellingPoints(3L, 5L);
		});
		Assertions.assertEquals("not found warning 3", exception.getMessage());
	}
	
	@Test
	public void testDeleteCostBetweenSellingPoints_shouldThrowException_whenFromAndToEquals() {
		Mockito.doNothing().when(sellingPointValidator).validateSellingPointExistence(Mockito.anyLong(), Mockito.anyString());
		Exception exception = Assertions.assertThrows(BusinessRuleException.class, () -> {
			service.deleteCostBetweenSellingPoints(3L, 3L);
		});
		Assertions.assertEquals("No se puede borrar el costo entre un punto y si mismo, ya que se presume irrisorio (0)", exception.getMessage());
	}
	
	@Test
	public void testDeleteCostBetweenSellingPoints_shouldThrowException_whenNoDataIsAvailable() {
		Mockito.doNothing().when(sellingPointValidator).validateSellingPointExistence(Mockito.anyLong(), Mockito.anyString());
		Mockito.when(costsRepo.findCostBetweenSellingPoints(6L, 8L, PageRequest.of(0, 1))).thenReturn(Collections.emptyList());
		Exception exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.deleteCostBetweenSellingPoints(6L, 8L);
        });
        Assertions.assertEquals("No se encontro un costo entre '6' y '8' para ser borrado", exception.getMessage());
	}
	
	@Test
	public void testSaveCostBetweenSellingPoints_ShouldReturnOk_ifCostWasCreated() {
		Mockito.reset(costsRepo);
		Mockito.doNothing().when(sellingPointValidator).validateNullableField(Mockito.anyLong(), Mockito.anyString());
		Mockito.when(costsRepo.findCostBetweenSellingPoints(14L, 22L, PageRequest.of(0, 1))).thenReturn(Collections.emptyList());
		
		CostBetweenSellingPointsDTO costDto = new CostBetweenSellingPointsDTO(14L, "fromProint", 22L, "toPoint", 99);
		String result = service.saveCostBetweenSellingPoints(costDto);
		Assertions.assertEquals("CREADO", result);
		
		ArgumentCaptor<CostBetweenSellingPoints> captor = ArgumentCaptor.forClass(CostBetweenSellingPoints.class);
		Mockito.verify(costsRepo, Mockito.times(1)).save(captor.capture());
		CostBetweenSellingPoints captCost = captor.getValue();
		Assertions.assertEquals(99, captCost.getAmount());
	}
	
	@Test
	public void testSaveCostBetweenSellingPoints_ShouldReturnOk_ifCostWasUpdated() {
		Mockito.reset(costsRepo);
		Mockito.doNothing().when(sellingPointValidator).validateNullableField(Mockito.anyLong(), Mockito.anyString());
		Mockito.when(costsRepo.findCostBetweenSellingPoints(15L, 23L, PageRequest.of(0, 1)))
			.thenReturn(List.of(new CostBetweenSellingPoints(new SellingPoint(15L, "fromProint"), new SellingPoint(23L, "toPoint"), 95)));
		
		CostBetweenSellingPointsDTO costDto = new CostBetweenSellingPointsDTO(15L, "fromProint", 23L, "toPoint", 120);
		String result = service.saveCostBetweenSellingPoints(costDto);
		Assertions.assertEquals("ACTUALIZADO", result);
		
		ArgumentCaptor<CostBetweenSellingPoints> captor = ArgumentCaptor.forClass(CostBetweenSellingPoints.class);
		Mockito.verify(costsRepo, Mockito.times(1)).save(captor.capture());
		CostBetweenSellingPoints captCost = captor.getValue();
		Assertions.assertEquals(120, captCost.getAmount());
	}
	
	@Test
	public void testSaveCostBetweenSellingPoints_ShouldThrowException_WhenMissingRequiredFields() {
		Exception exception = Assertions.assertThrows(MissingRequiredFieldException.class, () -> {
			service.saveCostBetweenSellingPoints(null);
		});
		Assertions.assertEquals("Para establecer el costo entre dos puntos se requieren los campos 'fromSellingPointId', 'toSellingPointId' y 'cost'", exception.getMessage());
		
		Mockito.doThrow(new MissingRequiredFieldException("required field warning 1")).when(sellingPointValidator).validateNullableField(null, "fromSellingPointId");
		CostBetweenSellingPointsDTO costDto = new CostBetweenSellingPointsDTO();
		exception = Assertions.assertThrows(MissingRequiredFieldException.class, () -> {
			service.saveCostBetweenSellingPoints(costDto);
		});
		Assertions.assertEquals("required field warning 1", exception.getMessage());
		
		Mockito.doNothing().when(sellingPointValidator).validateNullableField(12L, "fromSellingPointId");
		Mockito.doThrow(new MissingRequiredFieldException("required field warning 2")).when(sellingPointValidator).validateNullableField(null, "toSellingPointId");
		costDto.setFromSellingPointId(12L);
		exception = Assertions.assertThrows(MissingRequiredFieldException.class, () -> {
			service.saveCostBetweenSellingPoints(costDto);
		});
		Assertions.assertEquals("required field warning 2", exception.getMessage());
	}
	
	@Test
	public void testSaveCostBetweenSellingPoints_ShouldThrowException_WhenFromAndToEquals() {
		Mockito.doNothing().when(sellingPointValidator).validateNullableField(Mockito.anyLong(), Mockito.anyString());
		
		CostBetweenSellingPointsDTO costDto = new CostBetweenSellingPointsDTO(1L, "", 1L, "", 5);
		Exception exception = Assertions.assertThrows(BusinessRuleException.class, () -> {
			service.saveCostBetweenSellingPoints(costDto);
		});
		Assertions.assertEquals("No se puede establecer un costo entre un punto y si mismo, ya que se presume irrisorio (0)", exception.getMessage());
	}
	
	@Test
	public void testSaveCostBetweenSellingPoints_ShouldThrowException_WhenAmountIsNegative() {
		Mockito.doNothing().when(sellingPointValidator).validateNullableField(Mockito.anyLong(), Mockito.anyString());
		
		CostBetweenSellingPointsDTO costDto = new CostBetweenSellingPointsDTO(1L, "", 2L, "", -10);
		Exception exception = Assertions.assertThrows(BusinessRuleException.class, () -> {
			service.saveCostBetweenSellingPoints(costDto);
		});
		Assertions.assertEquals("El costo debe ser positivo", exception.getMessage());
	}
	
	

}
