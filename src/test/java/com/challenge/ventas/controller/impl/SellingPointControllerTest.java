package com.challenge.ventas.controller.impl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.Matchers.containsString;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.challenge.ventas.controller.impl.SellingPointController;
import com.challenge.ventas.exception.MissingRequiredFieldException;
import com.challenge.ventas.exception.ResourceNotFoundException;
import com.challenge.ventas.persistence.dto.SellingPointDTO;
import com.challenge.ventas.persistence.model.SellingPoint;
import com.challenge.ventas.service.ISellingPointService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(value = SellingPointController.class)
@ExtendWith(MockitoExtension.class)
class SellingPointControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@MockitoBean
	private ISellingPointService sellingPointService;
	
	@Test
	public void testGetAllSellingPoints_ShouldReturnOk_WhenDataExists() throws Exception {
		List<SellingPointDTO> sellingPoints = List.of(new SellingPointDTO(1L, "one"), new SellingPointDTO(2L, "two"));
		Mockito.when(sellingPointService.findActiveSellingPointDTOs()).thenReturn(sellingPoints);
		
		mockMvc.perform(get("/selling-point/"))
	        .andExpect(status().isOk())
	        .andExpect(jsonPath("$.length()").value(2))
	        .andExpect(jsonPath("$[0].id").value(1L))
	        .andExpect(jsonPath("$[0].name").value("one"))
	        .andExpect(jsonPath("$[1].id").value(2L))
	        .andExpect(jsonPath("$[1].name").value("two"));
	}
	
	@Test
	public void testGetAllSellingPoints_ShouldReturnNotFound_WhenDataDoesNotExist() throws Exception {
		Mockito.when(sellingPointService.findActiveSellingPointDTOs()).thenThrow(new ResourceNotFoundException("not found warning 1"));
		
		mockMvc.perform(get("/selling-point/"))
	        .andExpect(status().isNotFound())
	        .andExpect(content().string(containsString("not found warning 1")));
	}
	
	@Test
	public void testGetSellingPoint_ShouldReturnDTO_WhenDataExists() throws Exception {
		SellingPointDTO sellingPointDTO = new SellingPointDTO(3L, "three");
		Mockito.when(sellingPointService.findActiveSellingPointDTO(5L)).thenReturn(sellingPointDTO);
		
		ObjectMapper objectMapper = new ObjectMapper();
	    String expectedJson = objectMapper.writeValueAsString(sellingPointDTO);
		
		mockMvc.perform(get("/selling-point/5"))
	        .andExpect(status().isOk())
	        .andExpect(content().json(expectedJson));
	}
	
	@Test
	public void testGetSellingPoint_ShouldReturnNotFound_WhenDataDoesNotExist() throws Exception {
		Mockito.when(sellingPointService.findActiveSellingPointDTO(15L)).thenThrow(new ResourceNotFoundException("not found warning 2"));
		
		mockMvc.perform(get("/selling-point/15"))
	        .andExpect(status().isNotFound())
	        .andExpect(content().string(containsString("not found warning 2")));
	}
	
	
	@Test
	public void testCreateSellingPoint_ShouldReturnCreated_WhenCreated() throws Exception {
		SellingPoint sellingPoint = new SellingPoint(6L);
		sellingPoint.setName("six");
		Mockito.when(sellingPointService.createSellingPoint(Mockito.any())).thenReturn(sellingPoint);
		
		ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(new SellingPointDTO());
		
		mockMvc.perform(post("/selling-point/")
				.contentType("application/json")
				.content(jsonRequest))
	        .andExpect(status().isCreated())
	        .andExpect(content().string(containsString("Punto de venta creado OK: " + sellingPoint.toString())));
	}
	
	@Test
	public void testCreateSellingPoint_ShouldReturnBadRequest_WhenMissingRequiredFields() throws Exception {
		Mockito.when(sellingPointService.createSellingPoint(Mockito.any())).thenThrow(new MissingRequiredFieldException("validation text 1"));
		
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(new SellingPointDTO());
		
		mockMvc.perform(post("/selling-point/")
				.contentType("application/json")
				.content(jsonRequest))
	        .andExpect(status().isBadRequest())
	        .andExpect(content().string(containsString("validation text 1")));
	}
	
	
	@Test
	public void testUpdateSellingPoint_ShouldReturnOk_whenUpdated() throws Exception {
		SellingPoint sellingPoint = new SellingPoint(7L);
		sellingPoint.setName("seven");
		Mockito.when(sellingPointService.updateSellingPoint(Mockito.any())).thenReturn(sellingPoint);
		
		ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(new SellingPointDTO());
        
        mockMvc.perform(patch("/selling-point/")
				.contentType("application/json")
				.content(jsonRequest))
	        .andExpect(status().isOk())
	        .andExpect(content().string(containsString("Punto de venta actualizado OK: " + sellingPoint.toString())));
        
	}
	
	@Test
	public void testUpdateSellingPoint_ShouldReturnBadRequest_WhenMissingRequiredFields() throws Exception {
		Mockito.when(sellingPointService.updateSellingPoint(Mockito.any())).thenThrow(new MissingRequiredFieldException("validation text 2"));
		
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(new SellingPointDTO());
		
		mockMvc.perform(patch("/selling-point/")
				.contentType("application/json")
				.content(jsonRequest))
	        .andExpect(status().isBadRequest())
	        .andExpect(content().string(containsString("validation text 2")));
	}
	
	@Test
	public void testUpdateSellingPoint_ShouldReturnNotFound_WhenDataDoesNotExist() throws Exception {
		Mockito.when(sellingPointService.updateSellingPoint(Mockito.any())).thenThrow(new ResourceNotFoundException("not found warning 3"));
		
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(new SellingPointDTO());
		
		mockMvc.perform(patch("/selling-point/")
				.contentType("application/json")
				.content(jsonRequest))
	        .andExpect(status().isNotFound())
	        .andExpect(content().string(containsString("not found warning 3")));
	}

	
	@Test
	public void testRemoveSellingPoint_ShouldReturnNoContent_whenUpdated() throws Exception {	
		ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(new SellingPointDTO());
        
        mockMvc.perform(delete("/selling-point/10")
				.contentType("application/json")
				.content(jsonRequest))
	        .andExpect(status().isNoContent());
        
	}
	
	@Test
	public void testRemoveSellingPoint_ShouldReturnBadRequest_WhenMissingRequiredFields() throws Exception {
		Mockito.when(sellingPointService.removeSellingPoint(10L)).thenThrow(new MissingRequiredFieldException("validation text 3"));
		
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(new SellingPointDTO());
		
		mockMvc.perform(delete("/selling-point/10")
				.contentType("application/json")
				.content(jsonRequest))
	        .andExpect(status().isBadRequest())
	        .andExpect(content().string(containsString("validation text 3")));
	}
	
	@Test
	public void testRemoveSellingPoint_ShouldReturnNotFound_WhenDataDoesNotExist() throws Exception {
		Mockito.when(sellingPointService.removeSellingPoint(10L)).thenThrow(new ResourceNotFoundException("not found warning 4"));
		
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(new SellingPointDTO());
		
		mockMvc.perform(delete("/selling-point/10")
				.contentType("application/json")
				.content(jsonRequest))
	        .andExpect(status().isNotFound())
	        .andExpect(content().string(containsString("not found warning 4")));
	}

}
