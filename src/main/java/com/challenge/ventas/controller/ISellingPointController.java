package com.challenge.ventas.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.challenge.ventas.persistence.dto.SellingPointDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Selling Point API", description = "Endpoints for managing Selling Points.")
public interface ISellingPointController {
	
	@Operation(summary = "Returns all active Selling Points.")
	ResponseEntity<List<SellingPointDTO>> getAllSellingPoints();

	@Operation(summary = "Returns a Selling Point with the given Selling Point ID.")
	ResponseEntity<SellingPointDTO> getSellingPoint(
			@Parameter(description = "", required = true) Long sellingPointId);

	@Operation(summary = "Creates a mew Selling Point.")
	ResponseEntity<String> createSellingPoint(
			@RequestBody(
			    description = "Selling point representation",
			    required = true,
			    content = @Content(
			        schema = @Schema(implementation = SellingPointDTO.class),
	        		examples = @ExampleObject(
                        name = "Example Request",
                        summary = "Example of a valid selling point entry",
                        value = "{\n \"name\": \"La Pampa\",\n}"
	        		)
			    )
			) SellingPointDTO sellingPointDTO);

	@Operation(summary = "Updates an existing Selling Point.")
	ResponseEntity<String> updateSellingPoint(
			@RequestBody(
			    description = "Selling point representation",
			    required = true,
			    content = @Content(
			        schema = @Schema(implementation = SellingPointDTO.class),
	        		examples = @ExampleObject(
                        name = "Example Request",
                        summary = "Example of a valid selling point entry",
                        value = "{\n" +
                                "  \"id\": 5,\n" +
                                "  \"name\": \"Córdoba Capital\",\n" +
                                "}"
	        		)
			    )
			) SellingPointDTO sellingPointDTO);

	@Operation(summary = "Removes a selling point with the given Selling Point ID.")
	ResponseEntity<String> removeSellingPoint(
			@Parameter(description = "", required = true) Long sellingPointId);

}
