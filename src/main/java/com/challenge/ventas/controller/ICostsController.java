package com.challenge.ventas.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.challenge.ventas.exception.BusinessRuleException;
import com.challenge.ventas.exception.MissingRequiredFieldException;
import com.challenge.ventas.exception.ResourceNotFoundException;
import com.challenge.ventas.persistence.dto.CostBetweenSellingPointsDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Costs API", description = "Endpoints for managing costs between Selling Points.")
public interface ICostsController {
	
	@ApiResponse(responseCode = "200", description = "Costs found")
	@ApiResponse(responseCode = "404",
		description = "Costs not found",
		content = @Content(
	        mediaType = "application/json", 
	        schema = @Schema(example = "Actualmente no se posee informacion sobre costos entre puntos de venta")
	    ))
	@Operation(summary = "Returns all the direct connections between different Selling Points.")
	ResponseEntity<List<CostBetweenSellingPointsDTO>> getCostsBetweenSellingPoints() throws ResourceNotFoundException;
	
	
	@ApiResponse(responseCode = "200", description = "Cost found")
	@ApiResponse(responseCode = "404",
	description = "Cost not found",
	content = @Content(
			mediaType = "application/json", 
			schema = @Schema(example = "No se encontraron costos hacia el punto de venta '${sellingPointId}'")
			))
	@ApiResponse(responseCode = "404",
		description = "Selling Point not found",
		content = @Content(
	        mediaType = "application/json", 
	        schema = @Schema(example = "No existe un punto de venta activo '${sellingPointId}'")
	    ))
	@Operation(summary = "Returns all the direct connections with the given Selling Point ID.")
	ResponseEntity<List<CostBetweenSellingPointsDTO>> getDirectCostBetweenSellingPoints(
			@Parameter(description = "ID of a Selling Point.", required = true) Long sellingPointId) throws ResourceNotFoundException, MissingRequiredFieldException;
	
	
	@ApiResponse(responseCode = "200",
		description = "Cost found",
		content = @Content(
	        mediaType = "application/json", 
	        schema = @Schema(example = "El costo del camino DIRECTO entre ambos puntos de venta es: '${cost}'")
	    ))
	@ApiResponse(responseCode = "404",
		description = "Cost not found",
		content = @Content(
	        mediaType = "application/json", 
	        schema = @Schema(example = "No se encontro un costo entre 'sellingPointId1' y 'sellingPointId2'")
	    ))
	@ApiResponse(responseCode = "404",
		description = "Selling Point not found",
		content = @Content(
	        mediaType = "application/json", 
	        schema = @Schema(example = "No existe un punto de venta activo '${sellingPointIdX}'")
	    ))
	@Operation(summary = "Returns the direct connection between the two given Selling Point IDs.")
	public ResponseEntity<String> getDirectCostBetweenSellingPoints(
			@Parameter(description = "ID of a Selling Point", required = true) Long sellingPointId1,
			@Parameter(description = "ID of a Selling Point", required = true) Long sellingPointId2) throws ResourceNotFoundException, MissingRequiredFieldException;
	
	@Operation(summary = "Returns the shortest path between the two given Selling Point IDs. Indicates both the total cost and the path taken.")
	public ResponseEntity<Map<String, Object>> getShortestCostBetweenSellingPoints(
			@Parameter(description = "ID of a Selling Point", required = true) Long fromSellingPointId,
			@Parameter(description = "ID of a Selling Point", required = true) Long toSellingPointId) throws ResourceNotFoundException, MissingRequiredFieldException;
	
	@Operation(summary = "Deletes the direct connection between the two given Selling Point IDs.")
	ResponseEntity<String> deleteCostBetweenSellingPoints(
			@Parameter(description = "ID of a Selling Point", required = true) Long sellingPointId1,
			@Parameter(description = "ID of a Selling Point", required = true) Long sellingPointId2) throws BusinessRuleException, ResourceNotFoundException, MissingRequiredFieldException;
	
	@Operation(summary = "Creates a direct connection between the two given Selling Point IDs.")
	ResponseEntity<String> createCostBetweenSellingPoints(
			@RequestBody(
			    description = "Connection between two selling points data",
			    required = true,
			    content = @Content(
			        schema = @Schema(implementation = CostBetweenSellingPointsDTO.class),
	        		examples = @ExampleObject(
                        name = "Example Request",
                        summary = "Example of a valid cost entry",
                        value = "{\n" +
                                "  \"fromSellingPointId\": 5,\n" +
                                "  \"fromSellingPointName\": \"Córdoba\",\n" +
                                "  \"toSellingPointId\": 8,\n" +
                                "  \"toSellingPointName\": \"Chubut\",\n" +
                                "  \"cost\": 1500\n" +
                                "}"
	        		)
			    )
			) CostBetweenSellingPointsDTO costDto) throws MissingRequiredFieldException, BusinessRuleException, ResourceNotFoundException;

}
