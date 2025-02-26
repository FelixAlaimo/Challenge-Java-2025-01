package com.challenge.ventas.service.impl;

import java.util.Collections;
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

import com.challenge.ventas.exception.MissingRequiredFieldException;
import com.challenge.ventas.exception.ResourceNotFoundException;
import com.challenge.ventas.persistence.dto.AccreditationDTO;
import com.challenge.ventas.persistence.model.SaleAccreditation;
import com.challenge.ventas.persistence.model.SellingPoint;
import com.challenge.ventas.persistence.repository.IAccreditationsRepository;
import com.challenge.ventas.service.ISellingPointService;

@TestInstance(Lifecycle.PER_CLASS)
class AccreditationsServiceTest {
	
	@Mock
	private IAccreditationsRepository accreditationsRepo;
	
	@Mock
	private ISellingPointService sellingPointService;
	
	@InjectMocks
	private AccreditationsService service;
	
	@BeforeAll
	public void init() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void testFindAccreditationDTOs_shouldReturnResults_whenDataIsAvailable() {
		Mockito.when(accreditationsRepo.findAll()).thenReturn(List.of(
				new SaleAccreditation(new SellingPoint(15L, "fifteen"), 25000L),
				new SaleAccreditation(new SellingPoint(29L, "twenty-nine"), 48000L)));
		
		List<AccreditationDTO> result = service.findAccreditationDTOs();
		Assertions.assertEquals(2, result.size());
		Assertions.assertEquals(15L, result.get(0).getSellingPointId());
		Assertions.assertEquals("fifteen", result.get(0).getSellingPointName());
		Assertions.assertEquals(25000L, result.get(0).getAmount());
		Assertions.assertEquals(29L, result.get(1).getSellingPointId());
		Assertions.assertEquals("twenty-nine", result.get(1).getSellingPointName());
		Assertions.assertEquals(48000L, result.get(1).getAmount());
	}
	
	@Test
	public void testFindAccreditationDTOs_shouldThrowException_whenNoResults() {
		Mockito.when(accreditationsRepo.findAll()).thenReturn(Collections.emptyList());
		
		Exception exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.findAccreditationDTOs();
		});
		Assertions.assertEquals("Actualmente no se posee informacion sobre acreditaciones", exception.getMessage());
	}
	
	
	@Test
	public void testSaveAccreditation_shouldReturnNewEntity_whenPersistedCorrectly() {		
		AccreditationDTO accreditationDTO = new AccreditationDTO();
		accreditationDTO.setSellingPointId(34L);
		accreditationDTO.setAmount(40000L);
		
		SellingPoint sellingPoint = new SellingPoint(34L, "name");
		Mockito.when(sellingPointService.findActiveSellingPoint(accreditationDTO.getSellingPointId())).thenReturn(sellingPoint);
		Mockito.when(accreditationsRepo.save(Mockito.any())).thenAnswer(arguments -> arguments.getArgument(0));
		
		SaleAccreditation result = service.saveAccreditation(accreditationDTO);
		Assertions.assertEquals(sellingPoint, result.getSellingPoint());
		Assertions.assertEquals(40000L, result.getAmount());
	}

	@Test
	public void testSaveAccreditation_shouldThrowException_whenMissingRequiredFields() {		
		Exception exception = Assertions.assertThrows(MissingRequiredFieldException.class, () -> {
			service.saveAccreditation(null);
		});
		Assertions.assertEquals("Los atributos 'sellingPointId' e 'amount' son requeridos para asentar una acreditacion", exception.getMessage());
		
		AccreditationDTO accreditationDTO = new AccreditationDTO();
		exception = Assertions.assertThrows(MissingRequiredFieldException.class, () -> {
			service.saveAccreditation(null);
		});
		Assertions.assertEquals("Los atributos 'sellingPointId' e 'amount' son requeridos para asentar una acreditacion", exception.getMessage());
		
		accreditationDTO.setSellingPointId(63L);
		exception = Assertions.assertThrows(MissingRequiredFieldException.class, () -> {
			service.saveAccreditation(null);
		});
		Assertions.assertEquals("Los atributos 'sellingPointId' e 'amount' son requeridos para asentar una acreditacion", exception.getMessage());
	}
	

}
