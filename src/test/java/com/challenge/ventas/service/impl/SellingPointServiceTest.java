package com.challenge.ventas.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.challenge.ventas.exception.MissingRequiredFieldException;
import com.challenge.ventas.exception.ResourceNotFoundException;
import com.challenge.ventas.persistence.dto.SellingPointDTO;
import com.challenge.ventas.persistence.model.SellingPoint;
import com.challenge.ventas.persistence.repository.ISellingPointRepository;

@TestInstance(Lifecycle.PER_CLASS)
class SellingPointServiceTest {
	
	@Mock
	private ISellingPointRepository sellingPointRepo;
	
	@InjectMocks
	private SellingPointService service;
	
	@BeforeAll
	public void init() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void testFindActiveSellingPointDTOs_shouldReturnResults_whenDataIsAvailable() {
		Mockito.when(sellingPointRepo.findActiveSellingPointDTOs()).thenReturn(List.of(new SellingPointDTO(34L, "trirty-four")));
		List<SellingPointDTO> result = service.findActiveSellingPointDTOs();
		Assertions.assertEquals(1, result.size());
		Assertions.assertEquals(34L, result.get(0).getId());
		Assertions.assertEquals("trirty-four", result.get(0).getName());
	}
	
	@Test
	public void testFindActiveSellingPointDTOs_shouldThrowException_whenNoDataIsAvailable() {
		Mockito.when(sellingPointRepo.findActiveSellingPointDTOs()).thenReturn(new ArrayList<>());
        Exception exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.findActiveSellingPointDTOs();
        });
        Assertions.assertEquals("Actualmente no se posee informacion sobre puntos de venta", exception.getMessage());
	}
	
	@Test
	public void testFindActiveSellingPointDTO_shouldReturnResults_whenDataIsAvailable() {
		Mockito.when(sellingPointRepo.findActiveSellingPointDTO(22L)).thenReturn(Optional.of(new SellingPointDTO(22L, "twenty-two")));
		SellingPointDTO result = service.findActiveSellingPointDTO(22L);
		Assertions.assertEquals(22L, result.getId());
		Assertions.assertEquals("twenty-two", result.getName());
	}
	
	@Test
	public void testFindActiveSellingPointDTO_shouldThrowException_whenNotFound() {
		Mockito.when(sellingPointRepo.findActiveSellingPointDTO(22L)).thenReturn(Optional.empty());
		
		Exception exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.findActiveSellingPointDTO(22L);
        });
        Assertions.assertEquals("No se encontro el punto de venta '22'", exception.getMessage());
	}
	
	@Test
	public void testFindActiveSellingPoint_shouldReturnResults_whenDataIsAvailable() {
		Mockito.when(sellingPointRepo.findActiveSellingPoint(33L)).thenReturn(Optional.of(new SellingPoint(33L, "thirty-three")));
		SellingPoint result = service.findActiveSellingPoint(33L);
		Assertions.assertEquals(33L, result.getId());
		Assertions.assertEquals("thirty-three", result.getName());
	}
	
	@Test
	public void testFindActiveSellingPoint_shouldThrowException_whenNotFound() {
		Mockito.when(sellingPointRepo.findActiveSellingPoint(33L)).thenReturn(Optional.empty());
		
		Exception exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.findActiveSellingPoint(33L);
		});
		Assertions.assertEquals("No se encontro el punto de venta '33'", exception.getMessage());
	}
	
	@Test
	public void testCreateSellingPoint_shouldReturnSellingPoint_whenCreatedOk() {
		Mockito.when(sellingPointRepo.save(Mockito.any(SellingPoint.class))).thenAnswer(invocation -> invocation.getArgument(0));
		
		SellingPointDTO sellingPointDTO = new SellingPointDTO(null, "new-selling-point 1");
		SellingPoint result = service.createSellingPoint(sellingPointDTO);
		Assertions.assertEquals("new-selling-point 1", result.getName());
	}
	
	@Test
	public void testCreateSellingPoint_shouldThrowMissingRequiredFieldException_whenNullSellingPointArg() {
		Exception exception = Assertions.assertThrows(MissingRequiredFieldException.class, () -> {
			service.createSellingPoint(null);
		});
		Assertions.assertEquals("El atributo 'name' es requerido para crear el punto de venta", exception.getMessage());
	}
	
	
	@Test
	public void testCreateSellingPoint_shouldThrowMissingRequiredFieldException_whenEmptyNameArg() {
		SellingPointDTO sellingPointDTO = new SellingPointDTO(55L, null);
		Exception exception = Assertions.assertThrows(MissingRequiredFieldException.class, () -> {
			service.createSellingPoint(null);
		});
		Assertions.assertEquals("El atributo 'name' es requerido para crear el punto de venta", exception.getMessage());
		
		sellingPointDTO.setName("   ");
		exception = Assertions.assertThrows(MissingRequiredFieldException.class, () -> {
			service.createSellingPoint(null);
		});
		Assertions.assertEquals("El atributo 'name' es requerido para crear el punto de venta", exception.getMessage());
	}
	
	
	@Test
	public void testUpdateSellingPoint_ShouldReturnSellingPoint_WhenUpdatedOk() {
		Mockito.when(sellingPointRepo.save(Mockito.any(SellingPoint.class))).thenAnswer(invocation -> invocation.getArgument(0));
		
		SellingPoint sellingPoint = new SellingPoint(44L, "forty-four");
		Mockito.when(sellingPointRepo.findActiveSellingPoint(44L)).thenReturn(Optional.of(sellingPoint));
		
		SellingPoint updatedSellingPoint = service.updateSellingPoint(new SellingPointDTO(44L, "new-name"));
		Assertions.assertEquals("new-name", updatedSellingPoint.getName());
	}
	
	@Test
	public void testUpdateSellingPoint_shouldThrowMissingRequiredFieldException_whenNullSellingPointArg() {
		Exception exception = Assertions.assertThrows(MissingRequiredFieldException.class, () -> {
			service.updateSellingPoint(null);
		});
		Assertions.assertEquals("Los atributos 'name' e 'id' son requeridos para actualizar el punto de venta", exception.getMessage());
	}
	
	@Test
	public void testUpdateSellingPoint_shouldThrowMissingRequiredFieldException_whenNullSellingPointIdArg() {
		SellingPointDTO sellingPointDTO = new SellingPointDTO(null, "only-name");
		Exception exception = Assertions.assertThrows(MissingRequiredFieldException.class, () -> {
			service.updateSellingPoint(sellingPointDTO);
		});
		Assertions.assertEquals("Los atributos 'name' e 'id' son requeridos para actualizar el punto de venta", exception.getMessage());
	}
		
	@Test
	public void testUpdateSellingPoint_shouldThrowMissingRequiredFieldException_whenEmptyNameArg() {
		SellingPointDTO sellingPointDTO = new SellingPointDTO(55L, null);
		Exception exception = Assertions.assertThrows(MissingRequiredFieldException.class, () -> {
			service.updateSellingPoint(sellingPointDTO);
		});
		Assertions.assertEquals("Los atributos 'name' e 'id' son requeridos para actualizar el punto de venta", exception.getMessage());
		
		sellingPointDTO.setName("  ");
		exception = Assertions.assertThrows(MissingRequiredFieldException.class, () -> {
			service.updateSellingPoint(sellingPointDTO);
		});
		Assertions.assertEquals("Los atributos 'name' e 'id' son requeridos para actualizar el punto de venta", exception.getMessage());
	}
	
	@Test
	public void testUpdateSellingPoint_shouldThrowResourceNotFoundException_whenSellingPointIdReceivedNotFoundArg() {
		Mockito.when(sellingPointRepo.findActiveSellingPoint(66L)).thenReturn(Optional.empty());
		
		SellingPointDTO sellingPointDTO = new SellingPointDTO(66L, "the new name");
		Exception exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.updateSellingPoint(sellingPointDTO);
		});
		Assertions.assertEquals("No se encontro el punto de venta '66'", exception.getMessage());
	}
	
	@Test
	public void testRemoveSellingPoint_ShouldReturnSellingPoint_WhenRemovedOk() {
		Mockito.when(sellingPointRepo.save(Mockito.any(SellingPoint.class))).thenAnswer(invocation -> invocation.getArgument(0));
		
		SellingPoint sellingPoint = new SellingPoint(77L, "seventy-seven");
		Mockito.when(sellingPointRepo.findActiveSellingPoint(77L)).thenReturn(Optional.of(sellingPoint));
		
		SellingPoint removedSellingPoint = service.removeSellingPoint(77L);
		Assertions.assertNotNull(removedSellingPoint.getDeletedDate());
	}
	
	@Test
	public void testRemoveSellingPoint_shouldThrowMissingRequiredFieldException_whenNullSellingPointIdArg() {
		Exception exception = Assertions.assertThrows(MissingRequiredFieldException.class, () -> {
			service.removeSellingPoint(null);
		});
		Assertions.assertEquals("El atributo 'id' es requerido para remover el punto de venta", exception.getMessage());
	}
	
	@Test
	public void testRemoveSellingPoint_shouldThrowResourceNotFoundException_whenSellingPointIdReceivedNotFoundArg() {
		Mockito.when(sellingPointRepo.findActiveSellingPoint(88L)).thenReturn(Optional.empty());
		
		Exception exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.removeSellingPoint(88L);
		});
		Assertions.assertEquals("No se encontro el punto de venta '88'", exception.getMessage());
	}

}
