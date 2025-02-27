package com.challenge.ventas.helper;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.challenge.ventas.exception.ResourceNotFoundException;
import com.challenge.ventas.persistence.dto.PathResultDTO;
import com.challenge.ventas.persistence.dto.SellingPointDTO;
import com.challenge.ventas.service.ISellingPointService;

@ExtendWith(MockitoExtension.class)
class CostsFormatterTest {
	
	@Mock
	private ISellingPointService sellingPointService;
	
	@InjectMocks
	private CostsFormatter costsFormatter;
	
	@Test
	public void testFormatPathPointsWithName_ShouldReturnNull_WhenPathIsNullOrEmpty() {
		PathResultDTO result = null;
		Assertions.assertNull(costsFormatter.formatPathPointsWithName(result));
		
		result = new PathResultDTO();
		Assertions.assertNull(costsFormatter.formatPathPointsWithName(new PathResultDTO()));
		
		result.setSellingPointsPath(new ArrayList<>());
	}
	
	@Test
	public void testFormatPathPointsWithName_ShouldReturnResult_WhenPathIsPresent() throws ResourceNotFoundException {
		PathResultDTO pathDTO = new PathResultDTO(Arrays.asList(1L, 4L, 3L, 6L), 35);
		
		Mockito.when(sellingPointService.findActiveSellingPointDTO(1L)).thenReturn(new SellingPointDTO(1L, "pOne"));
		Mockito.when(sellingPointService.findActiveSellingPointDTO(4L)).thenReturn(new SellingPointDTO(4L, "pFour"));
		Mockito.when(sellingPointService.findActiveSellingPointDTO(3L)).thenThrow(new ResourceNotFoundException("Point does not exist"));
		Mockito.when(sellingPointService.findActiveSellingPointDTO(6L)).thenReturn(new SellingPointDTO(6L, "pSix"));
		
		String[] result = costsFormatter.formatPathPointsWithName(pathDTO);
		Assertions.assertEquals(4, result.length);
		Assertions.assertEquals("pOne (1)", result[0]);
		Assertions.assertEquals("pFour (4)", result[1]);
		Assertions.assertEquals("Missing_Name (3)", result[2]);
		Assertions.assertEquals("pSix (6)", result[3]);
	}
	

}
