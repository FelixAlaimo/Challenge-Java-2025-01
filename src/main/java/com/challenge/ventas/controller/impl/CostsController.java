package com.challenge.ventas.controller.impl;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.ventas.controller.ICostsController;
import com.challenge.ventas.exception.BusinessRuleException;
import com.challenge.ventas.exception.MissingRequiredFieldException;
import com.challenge.ventas.exception.ResourceNotFoundException;
import com.challenge.ventas.helper.CostsFormatter;
import com.challenge.ventas.persistence.dto.CostBetweenSellingPointsDTO;
import com.challenge.ventas.persistence.dto.PathResultDTO;
import com.challenge.ventas.service.ICostsBetweenSellingPointsService;
import com.challenge.ventas.service.IPathFinderService;

@RestController
@RequestMapping("/costs")
public class CostsController implements ICostsController {
	
	@Autowired
	private ICostsBetweenSellingPointsService costsBetweenSellingPointsService;
	
	@Autowired
	private IPathFinderService pathFinderService;
	
	@Autowired
	private CostsFormatter costsFormatter;
	
	@GetMapping("/direct/")
	@Transactional(readOnly = true)
    public ResponseEntity<List<CostBetweenSellingPointsDTO>> getCostsBetweenSellingPoints() throws ResourceNotFoundException {
        return ResponseEntity.ok(costsBetweenSellingPointsService.findCostBetweenSellingPointsDTOs()); 
    }
	
	@GetMapping("/direct/{sellingPointId}")
	@Transactional(readOnly = true)
	public ResponseEntity<List<CostBetweenSellingPointsDTO>> getDirectCostBetweenSellingPoints(@PathVariable Long sellingPointId) throws ResourceNotFoundException, MissingRequiredFieldException {
		return ResponseEntity.ok(costsBetweenSellingPointsService.findCostBetweenSellingPointsDTOById(sellingPointId));
	}
	
	@GetMapping("/direct/{sellingPointId1}/{sellingPointId2}")
	@Transactional(readOnly = true)
	public ResponseEntity<String> getDirectCostBetweenSellingPoints(@PathVariable Long sellingPointId1, @PathVariable Long sellingPointId2) throws ResourceNotFoundException, MissingRequiredFieldException {
		CostBetweenSellingPointsDTO cost = costsBetweenSellingPointsService.findDirectCostBetweenSellingPointsDTOByIds(sellingPointId1, sellingPointId2);
		return ResponseEntity.ok("El costo del camino DIRECTO entre ambos puntos de venta es: " + cost.getCost());
	}
	
	@GetMapping("/shortest/{fromSellingPointId}/{toSellingPointId}")
	@Transactional(readOnly = true)
	public ResponseEntity<Map<String, Object>> getShortestCostBetweenSellingPoints(@PathVariable Long fromSellingPointId, @PathVariable Long toSellingPointId) throws ResourceNotFoundException, MissingRequiredFieldException {
		
		List<CostBetweenSellingPointsDTO> currentCosts = costsBetweenSellingPointsService.findCostBetweenSellingPointsDTOs();
		PathResultDTO result = pathFinderService.findShortestPath(fromSellingPointId, toSellingPointId, currentCosts);
		
		Map<String, Object> response = new HashMap<>();
		String[] pathTaken = costsFormatter.formatPathPointsWithName(result);
		if (pathTaken != null) {
			response.put("resultado", "Se encontro una ruta entre ambos puntos con costo minimo de: " + result.getCost());
			response.put("ruta", String.join(" -> ", pathTaken));
		}
		else {
			response.put("resultado", "No se encontro una ruta entre ambos puntos");
			response.put("ruta", "N/A");
		}
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("/{sellingPointId1}/{sellingPointId2}")
	@Transactional
	public ResponseEntity<String> deleteCostBetweenSellingPoints(@PathVariable Long sellingPointId1, @PathVariable Long sellingPointId2) throws BusinessRuleException, ResourceNotFoundException, MissingRequiredFieldException {
		costsBetweenSellingPointsService.deleteCostBetweenSellingPoints(sellingPointId1, sellingPointId2);
		return ResponseEntity.ok("El costo entre los puntos de venta fue borrado");
	}
	
	@PutMapping("/")
	@Transactional
	public ResponseEntity<String> createCostBetweenSellingPoints(@RequestBody CostBetweenSellingPointsDTO costDto) throws MissingRequiredFieldException, BusinessRuleException, ResourceNotFoundException {		
		String operationPerformed = costsBetweenSellingPointsService.saveCostBetweenSellingPoints(costDto);
		return ResponseEntity.ok(MessageFormat.format("El costo entre los puntos de venta ''{0}'' y ''{1}'' fue {2} correctamente, con un valor de ''{3}''.",
				costDto.getFromSellingPointId(), costDto.getToSellingPointId(), operationPerformed, costDto.getCost()));
	}

}
