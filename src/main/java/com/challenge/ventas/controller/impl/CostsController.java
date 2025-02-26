package com.challenge.ventas.controller.impl;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.ventas.controller.ICostsController;
import com.challenge.ventas.persistence.dto.CostBetweenSellingPointsDTO;
import com.challenge.ventas.persistence.dto.PathResultDTO;
import com.challenge.ventas.service.ICostsBetweenSellingPointsService;
import com.challenge.ventas.service.IPathFinderService;
import com.challenge.ventas.service.ISellingPointService;

@RestController
@RequestMapping("/costs")
public class CostsController implements ICostsController {
	
	@Autowired
	private ICostsBetweenSellingPointsService costsBetweenSellingPointsService;
	
	@Autowired
	private ISellingPointService sellingPointService;
	
	@Autowired
	private IPathFinderService pathFinderService;
	
	@GetMapping("/direct/")
	@Transactional(readOnly = true)
    public ResponseEntity<List<CostBetweenSellingPointsDTO>> getCostsBetweenSellingPoints() {
        return ResponseEntity.ok(costsBetweenSellingPointsService.findCostBetweenSellingPointsDTOs()); 
    }
	
	@GetMapping("/direct/{sellingPointId}")
	@Transactional(readOnly = true)
	public ResponseEntity<List<CostBetweenSellingPointsDTO>> getDirectCostBetweenSellingPoints(@PathVariable Long sellingPointId) {
		return ResponseEntity.ok(costsBetweenSellingPointsService.findCostBetweenSellingPointsDTOById(sellingPointId));
	}
	
	@GetMapping("/direct/{sellingPointId1}/{sellingPointId2}")
	@Transactional(readOnly = true)
	public ResponseEntity<String> getDirectCostBetweenSellingPoints(@PathVariable Long sellingPointId1, @PathVariable Long sellingPointId2) {
		CostBetweenSellingPointsDTO cost = costsBetweenSellingPointsService.findDirectCostBetweenSellingPointsDTOByIds(sellingPointId1, sellingPointId2);
		return ResponseEntity.ok("El costo del camino DIRECTO entre ambos puntos de venta es: " + cost.getCost());
	}
	
	@GetMapping("/shortest/{fromSellingPointId}/{toSellingPointId}")
	@Transactional(readOnly = true)
	public ResponseEntity<Map<String, Object>> getShortestCostBetweenSellingPoints(@PathVariable Long fromSellingPointId, @PathVariable Long toSellingPointId) {
		
		List<CostBetweenSellingPointsDTO> currentCosts = costsBetweenSellingPointsService.findCostBetweenSellingPointsDTOs();
		PathResultDTO result = pathFinderService.findShortestPath(fromSellingPointId, toSellingPointId, currentCosts);
		
		Map<String, Object> response = new HashMap<>();
		if (result != null && !CollectionUtils.isEmpty(result.getSellingPointsPath())) {
			String[] pathTaken = result.getSellingPointsPath().stream()
					.map(spId -> sellingPointService.findActiveSellingPointDTO(spId).getName() + " (" + spId + ")").toArray(String[]::new);
			String path = String.join(" -> ", pathTaken);
			response.put("resultado", "Se encontro una ruta entre ambos puntos con costo minimo de: " + result.getCost());
			response.put("ruta", path);
		}
		else {
			response.put("resultado", "No se encontro una ruta entre ambos puntos");
			response.put("ruta", "N/A");
		}
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("/{sellingPointId1}/{sellingPointId2}")
	@Transactional
	public ResponseEntity<String> deleteCostBetweenSellingPoints(@PathVariable Long sellingPointId1, @PathVariable Long sellingPointId2) {
		costsBetweenSellingPointsService.deleteCostBetweenSellingPoints(sellingPointId1, sellingPointId2);
		return ResponseEntity.ok("El costo entre los puntos de venta fue borrado");
	}
	
	@PutMapping("/")
	@Transactional
	public ResponseEntity<String> createCostBetweenSellingPoints(@RequestBody CostBetweenSellingPointsDTO costDto) {		
		String operationPerformed = costsBetweenSellingPointsService.saveCostBetweenSellingPoints(costDto);
		return ResponseEntity.ok(MessageFormat.format("El costo entre los puntos de venta ''{0}'' y ''{1}'' fue {2} correctamente, con un valor de ''{3}''.",
				costDto.getFromSellingPointId(), costDto.getToSellingPointId(), operationPerformed, costDto.getCost()));
	}

}
