package com.challenge.ventas.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.challenge.ventas.exception.MissingRequiredFieldException;
import com.challenge.ventas.exception.ResourceNotFoundException;
import com.challenge.ventas.persistence.dto.AccreditationDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Accreditations API", description = "Endpoints for registering and consulting Accreditations.")
public interface IAccreditationsController {
	
	@Operation(summary = "Returns all accreditations.")
	ResponseEntity<List<AccreditationDTO>> getAccreditations() throws ResourceNotFoundException;
	
	@Operation(summary = "Saves a new accreditation.")
	ResponseEntity<String> saveNewAccreditation(
			@RequestBody(
			    description = "Connection between two selling points data",
			    required = true,
			    content = @Content(
			        schema = @Schema(implementation = AccreditationDTO.class),
	        		examples = @ExampleObject(
                        name = "Example Request",
                        summary = "Example of a valid accreditation entry",
                        value = "{\n" +
                                "  \"sellingPointId\": 5,\n" +
                                "  \"amount\": 32000\n" +
                                "}"
	        		)
			    )
			) AccreditationDTO accreditationDTO) throws MissingRequiredFieldException, ResourceNotFoundException;

}
