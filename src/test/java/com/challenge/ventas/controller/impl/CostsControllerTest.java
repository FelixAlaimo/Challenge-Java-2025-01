package com.challenge.ventas.controller.impl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.Matchers.containsString;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.challenge.ventas.exception.BusinessRuleException;
import com.challenge.ventas.exception.MissingRequiredFieldException;
import com.challenge.ventas.exception.ResourceNotFoundException;
import com.challenge.ventas.helper.CostsFormatter;
import com.challenge.ventas.persistence.dto.CostBetweenSellingPointsDTO;
import com.challenge.ventas.persistence.dto.PathResultDTO;
import com.challenge.ventas.service.ICostsBetweenSellingPointsService;
import com.challenge.ventas.service.IPathFinderService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(value = CostsController.class)
@ExtendWith(MockitoExtension.class)
class CostsControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@MockitoBean
	private ICostsBetweenSellingPointsService costsBetweenSellingPointsService;
	
	@MockitoBean
	private IPathFinderService pathFinderService;
	
	@MockitoBean
	private CostsFormatter costsFormatter;
	
	
	@Test
	public void testGetCostsBetweenSellingPoints_ShouldReturnOk_WhenDataExists() throws Exception {
		List<CostBetweenSellingPointsDTO> costs = List.of(
				new CostBetweenSellingPointsDTO(1L, "one", 2L, "two", 15),
				new CostBetweenSellingPointsDTO(2L, "two", 4L, "four", 9));
		Mockito.when(costsBetweenSellingPointsService.findCostBetweenSellingPointsDTOs()).thenReturn(costs);
		
		mockMvc.perform(get("/costs/direct/"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.length()").value(2))
			.andExpect(jsonPath("$[0].fromSellingPointId").value(1L))
			.andExpect(jsonPath("$[0].fromSellingPointName").value("one"))
			.andExpect(jsonPath("$[0].toSellingPointId").value(2L))
			.andExpect(jsonPath("$[0].toSellingPointName").value("two"))
			.andExpect(jsonPath("$[0].cost").value(15))
			.andExpect(jsonPath("$[1].fromSellingPointId").value(2L))
			.andExpect(jsonPath("$[1].fromSellingPointName").value("two"))
			.andExpect(jsonPath("$[1].toSellingPointId").value(4L))
			.andExpect(jsonPath("$[1].toSellingPointName").value("four"))
			.andExpect(jsonPath("$[1].cost").value(9));
	}
	
	@Test
	public void testGetCostsBetweenSellingPoints_ShouldReturnNotFound_WhenDataDoesNotExist() throws Exception {
		Mockito.when(costsBetweenSellingPointsService.findCostBetweenSellingPointsDTOs()).thenThrow(new ResourceNotFoundException("not found warning 1"));
		
		mockMvc.perform(get("/costs/direct/"))
			.andExpect(status().isNotFound())
			.andExpect(content().string(containsString("not found warning 1")));
	}
	
	
	@Test
	public void testGetDirectCostBetweenSellingPoints_ShouldReturnOk_WhenDataExists() throws Exception {
		List<CostBetweenSellingPointsDTO> costs = List.of(
				new CostBetweenSellingPointsDTO(2L, "two", 4L, "four", 9),
				new CostBetweenSellingPointsDTO(1L, "one", 2L, "two", 15));
		Mockito.when(costsBetweenSellingPointsService.findCostBetweenSellingPointsDTOById(2L)).thenReturn(costs);
		
		mockMvc.perform(get("/costs/direct/2"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.length()").value(2))
			.andExpect(jsonPath("$[0].fromSellingPointId").value(2L))
			.andExpect(jsonPath("$[0].fromSellingPointName").value("two"))
			.andExpect(jsonPath("$[0].toSellingPointId").value(4L))
			.andExpect(jsonPath("$[0].toSellingPointName").value("four"))
			.andExpect(jsonPath("$[0].cost").value(9))
			.andExpect(jsonPath("$[1].fromSellingPointId").value(1L))
			.andExpect(jsonPath("$[1].fromSellingPointName").value("one"))
			.andExpect(jsonPath("$[1].toSellingPointId").value(2L))
			.andExpect(jsonPath("$[1].toSellingPointName").value("two"))
			.andExpect(jsonPath("$[1].cost").value(15));
	}
	
	@Test
	public void testGetDirectCostBetweenSellingPoints_ShouldReturnNotFound_WhenDataDoesNotExist() throws Exception {
		Mockito.when(costsBetweenSellingPointsService.findCostBetweenSellingPointsDTOById(2L)).thenThrow(new ResourceNotFoundException("not found warning 2"));
		
		mockMvc.perform(get("/costs/direct/2"))
			.andExpect(status().isNotFound())
			.andExpect(content().string(containsString("not found warning 2")));
	}
	
	
	@Test
	public void testGetDirectCostBetweenSellingPointsTwoArgs_ShouldReturnOk_WhenDataExists() throws Exception {
		CostBetweenSellingPointsDTO cost = new CostBetweenSellingPointsDTO(2L, "two", 5L, "five", 9);
		Mockito.when(costsBetweenSellingPointsService.findDirectCostBetweenSellingPointsDTOByIds(2L, 5L)).thenReturn(cost);
		
		mockMvc.perform(get("/costs/direct/2/5"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("El costo del camino DIRECTO entre ambos puntos de venta es: 9")));
	}
	
	@Test
	public void testGetDirectCostBetweenSellingPointsTwoArgs_ShouldReturnNotFound_WhenDataDoesNotExist() throws Exception {
		Mockito.when(costsBetweenSellingPointsService.findDirectCostBetweenSellingPointsDTOByIds(2L, 5L)).thenThrow(new ResourceNotFoundException("not found warning 3"));
		
		mockMvc.perform(get("/costs/direct/2/5"))
			.andExpect(status().isNotFound())
			.andExpect(content().string(containsString("not found warning 3")));
	}	
	
	
	@Test
	public void testGetShortestCostBetweenSellingPoints_ShouldReturnBadRequest_WhenInvalidIdFormat() throws Exception {
		mockMvc.perform(get("/costs/shortest/two/5"))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.error").value("Invalid number format"))
			.andExpect(jsonPath("$.message").value("Se esperaba un valor numerico pero no fue provisto correctamente."));
	}
	
	
	@Test
	public void testGetShortestCostBetweenSellingPoints_ShouldReturnOk_WhenPathIsFound() throws Exception {
		List<Long> ruta = List.of(2L, 4L, 7L, 5L);
		String[] formattedPathPoints = new String[] {"anyFormat2", "anyFormat4", "anyFormat7", "anyFormat5"};
		PathResultDTO pathTakenResultDto = new PathResultDTO(ruta, 60);
		Mockito.when(pathFinderService.findShortestPath(2L, 5L, new ArrayList<>())).thenReturn(pathTakenResultDto);
		Mockito.when(costsFormatter.formatPathPointsWithName(pathTakenResultDto)).thenReturn(formattedPathPoints);
		
		mockMvc.perform(get("/costs/shortest/2/5"))
	        .andExpect(status().isOk())
	        .andExpect(jsonPath("$.resultado").value("Se encontro una ruta entre ambos puntos con costo minimo de: 60"))
            .andExpect(jsonPath("$.ruta").value(String.join(" -> ", formattedPathPoints)));
	}
	
	@Test
	public void testGetShortestCostBetweenSellingPoints_ShouldReturnOk_WhenNoPathIsFound() throws Exception {
		Mockito.when(pathFinderService.findShortestPath(4L, 7L, new ArrayList<>())).thenReturn(new PathResultDTO());
		Mockito.when(costsFormatter.formatPathPointsWithName(Mockito.any(PathResultDTO.class))).thenReturn(null);
		mockMvc.perform(get("/costs/shortest/4/7"))
	        .andExpect(status().isOk())
	        .andExpect(jsonPath("$.resultado").value("No se encontro una ruta entre ambos puntos"))
            .andExpect(jsonPath("$.ruta").value("N/A"));
		
		Mockito.when(pathFinderService.findShortestPath(4L, 7L, new ArrayList<>())).thenReturn(null);
		mockMvc.perform(get("/costs/shortest/4/7"))
	        .andExpect(status().isOk())
	        .andExpect(jsonPath("$.resultado").value("No se encontro una ruta entre ambos puntos"))
            .andExpect(jsonPath("$.ruta").value("N/A"));
	}
	
	@Test
	public void testDeleteCostBetweenSellingPoints_ShouldReturnNotFound_WhenPointDoesNotExist() throws Exception {
		Mockito.doThrow(new ResourceNotFoundException("not found warning 4")).when(costsBetweenSellingPointsService).deleteCostBetweenSellingPoints(4L, 9L);
		
		mockMvc.perform(delete("/costs/4/9"))
		.andExpect(status().isNotFound())
		.andExpect(content().string(containsString("not found warning 4")));
	}
	
	@Test
	public void testDeleteCostBetweenSellingPoints_ShouldReturnBadRequest_WhenTryingToDeleteAPointSelfCost() throws Exception {
		Mockito.doThrow(new BusinessRuleException("business rule warning 1")).when(costsBetweenSellingPointsService).deleteCostBetweenSellingPoints(4L, 4L);
		
		mockMvc.perform(delete("/costs/4/4"))
		.andExpect(status().isNotAcceptable())
		.andExpect(content().string(containsString("business rule warning 1")));
	}
	
	@Test
	public void testDeleteCostBetweenSellingPoints_ShouldReturnOk_WhenNoErrorWhileDeleting() throws Exception {
		Mockito.doNothing().when(costsBetweenSellingPointsService).deleteCostBetweenSellingPoints(4L, 6L);
		
		mockMvc.perform(delete("/costs/4/6"))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("El costo entre los puntos de venta fue borrado")));
	}
	
	
	@Test
	public void testCreateCostBetweenSellingPoints_ShouldReturnOk_WhenCreatedOk() throws Exception {
		CostBetweenSellingPointsDTO costDto = new CostBetweenSellingPointsDTO(1L, "one", 9L, "nine", 17);
		Mockito.when(costsBetweenSellingPointsService.saveCostBetweenSellingPoints(Mockito.any())).thenReturn("CREADO");
		
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonRequest = objectMapper.writeValueAsString(costDto);
		
		mockMvc.perform(put("/costs/")
				.contentType("application/json")
				.content(jsonRequest))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("El costo entre los puntos de venta '1' y '9' fue CREADO correctamente, con un valor de '17'.")));
	}
	
	@Test
	public void testCreateCostBetweenSellingPoints_ShouldReturnBadRequest_WhenMissingRequiredFields() throws Exception {
		CostBetweenSellingPointsDTO costDto = new CostBetweenSellingPointsDTO(1L, "one", 9L, "nine", 17);
		Mockito.when(costsBetweenSellingPointsService.saveCostBetweenSellingPoints(Mockito.any())).thenThrow(new MissingRequiredFieldException("required fields warning 1"));
		
		ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(costDto);
		
		mockMvc.perform(put("/costs/")
				.contentType("application/json")
				.content(jsonRequest))
	        .andExpect(status().isBadRequest())
	        .andExpect(content().string(containsString("required fields warning 1")));
	}

}
