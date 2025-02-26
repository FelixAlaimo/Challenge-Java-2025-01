package com.challenge.ventas.controller.impl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
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

import com.challenge.ventas.exception.MissingRequiredFieldException;
import com.challenge.ventas.exception.ResourceNotFoundException;
import com.challenge.ventas.persistence.dto.AccreditationDTO;
import com.challenge.ventas.persistence.model.SaleAccreditation;
import com.challenge.ventas.persistence.model.SellingPoint;
import com.challenge.ventas.service.IAccreditationsService;
import com.challenge.ventas.service.ISellingPointService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(value = AccreditationsController.class)
@ExtendWith(MockitoExtension.class)
class AccreditationsControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@MockitoBean
	private ISellingPointService sellingPointService;
	
	@MockitoBean
	private IAccreditationsService accreditationsService;
	
	@Test
	public void testGetAccreditations_ShouldReturnOk_WhenDataExists() throws Exception {
		List<AccreditationDTO> accreditationDTOs = List.of(
				new AccreditationDTO(1L,  3L, "three", 20000L, "le date 1"),
				new AccreditationDTO(2L,  6L, "six", 48000L, "le date 2"));
		Mockito.when(accreditationsService.findAccreditationDTOs()).thenReturn(accreditationDTOs);
		
		mockMvc.perform(get("/sale-accreditation/"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.length()").value(2))
			.andExpect(jsonPath("$[0].id").value(1L))
			.andExpect(jsonPath("$[0].sellingPointId").value(3L))
			.andExpect(jsonPath("$[0].sellingPointName").value("three"))
			.andExpect(jsonPath("$[0].amount").value(20000L))
			.andExpect(jsonPath("$[0].accreditationDate").value("le date 1"))
			.andExpect(jsonPath("$[1].id").value(2L))
			.andExpect(jsonPath("$[1].sellingPointId").value(6L))
			.andExpect(jsonPath("$[1].sellingPointName").value("six"))
			.andExpect(jsonPath("$[1].amount").value(48000L))
			.andExpect(jsonPath("$[1].accreditationDate").value("le date 2"));
	}
	
	@Test
	public void testGetAccreditations_ShouldReturnNotFound_WhenDataDoesNotExist() throws Exception {
		Mockito.when(accreditationsService.findAccreditationDTOs()).thenThrow(new ResourceNotFoundException("not found warning 1"));
		
		mockMvc.perform(get("/sale-accreditation/"))
			.andExpect(status().isNotFound())
			.andExpect(content().string(containsString("not found warning 1")));
	}
	
	@Test
	public void testSaveNewAccreditation_ShouldReturnCreated_WhenCreatedOk() throws Exception {
		SellingPoint sellingPoint = new SellingPoint(22L);
		sellingPoint.setName("twenty-two");
		SaleAccreditation accreditation = new SaleAccreditation(sellingPoint, 25000L);
		Mockito.when(accreditationsService.saveAccreditation(Mockito.any())).thenReturn(accreditation);
		
		ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(new AccreditationDTO());
		
		mockMvc.perform(post("/sale-accreditation/")
				.contentType("application/json")
				.content(jsonRequest))
			.andExpect(status().isCreated())
			.andExpect(content().string(containsString("Se asento correctamente la acreditacion: " + accreditation.toString())));
	}
	
	@Test
	public void testSaveNewAccreditations_ShouldReturnNotFound_WhenPointDoesNotExist() throws Exception {
		Mockito.when(accreditationsService.saveAccreditation(Mockito.any())).thenThrow(new ResourceNotFoundException("not found warning 2"));
		
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonRequest = objectMapper.writeValueAsString(new AccreditationDTO());
		
		mockMvc.perform(post("/sale-accreditation/")
				.contentType("application/json")
				.content(jsonRequest))
		.andExpect(status().isNotFound())
		.andExpect(content().string(containsString("not found warning 2")));
	}
	
	@Test
	public void testSaveNewAccreditations_ShouldReturnBadRequest_WhenMissingRequiredFields() throws Exception {
		Mockito.when(accreditationsService.saveAccreditation(Mockito.any())).thenThrow(new MissingRequiredFieldException("required fields warning 1"));
		
		ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(new AccreditationDTO());
		
		mockMvc.perform(post("/sale-accreditation/")
				.contentType("application/json")
				.content(jsonRequest))
	        .andExpect(status().isBadRequest())
	        .andExpect(content().string(containsString("required fields warning 1")));
	}

}
