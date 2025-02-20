package com.challenge.ventas.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.ventas.persistence.dto.CostBetweenSellingPointsDTO;
import com.challenge.ventas.persistence.dto.PathResultDTO;
import com.challenge.ventas.service.SalesService;

@RestController
@RequestMapping("/costs")
public class CostsController {
	
	private static final String WARNING_AMBOS_IDS = "Warning! revisar campos requeridos: 'id' (de ambos puntos)";
	
	@Autowired
	private SalesService salesService;
	
	@GetMapping("/all")
    public List<CostBetweenSellingPointsDTO> getSellingPoints() {
        return salesService.findCostBetweenSellingPointsDTO(); 
    }
	
	@GetMapping("/selling-point-connections/{id}")
	public List<CostBetweenSellingPointsDTO> getDirectCostBetweenSellingPoints(@PathVariable Long id) {
		if (id == null) {
			return new ArrayList<>();
		}
		
		return salesService.findCostBetweenSellingPointsDTOById(id);
	}
	
	@GetMapping("/direct-path/{id1}/{id2}")
	public String getDirectCostBetweenSellingPoints(@PathVariable Long id1, @PathVariable Long id2) {
		if (id1 == null || id2 == null) {
			return WARNING_AMBOS_IDS;
		}
		
		if (id1.equals(id2)) {
			return "El costo entre un punto y si mismo se presume irrisorio (0)";
		}
		
		CostBetweenSellingPointsDTO cost = salesService.findDirectCostBetweenSellingPointsDTOByIds(id1, id2);
		
		if (cost == null) {
			return "No se encontró un costo directo entre ambos puntos de venta. Puede ser que no exista o que alguno de los puntos de venta se encuentre borrado.";
		}
		
		return "El costo del camino DIRECTO entre ambos puntos de venta es: " + cost.getCost();
	}
	
	@GetMapping("/shortest-path/{id1}/{id2}")
	public String getShortestCostBetweenSellingPoints(@PathVariable Long id1, @PathVariable Long id2) {
		if (id1 == null || id2 == null) {
			return WARNING_AMBOS_IDS;
		}
		
		PathResultDTO result = salesService.findShortestCostBetweenSellingPointsDTOByIds(id1, id2);
		if (result == null || CollectionUtils.isEmpty(result.getSellingPointsPath())) {
			return "Lon puntos ingresados no poseen conexión";
		}
		else {
			String path = String.join(" -> ", result.getSellingPointsPath().stream().map(spId -> salesService.findSellingPointDto(spId).getName()).toArray(String[]::new));
			return "El camino de menor costo es " + path + " con costo " + result.getCost();
		}
	}
	
	@DeleteMapping("/remove/{id1}/{id2}")
	public String deleteCostBetweenSellingPoints(@PathVariable Long id1, @PathVariable Long id2) {
		if (id1 == null || id2 == null) {
			return WARNING_AMBOS_IDS;
		}
		
		if (id1.equals(id2)) {
			return "No se puede borrar el costo entre un punto y si mismo, ya que se presume irrisorio (0)";
		}

		salesService.deleteCostBetweenSellingPoints(id1, id2);
		return "Solicitud exitosa de borrado de costo entre dos puntos de venta. En caso de existir, sera eliminado";
	}
	
	@PutMapping("/new")
	public String createCostBetweenSellingPoints(@RequestBody CostBetweenSellingPointsDTO costDto) {
		if (costDto == null || costDto.getFromSellingPointId() == null || costDto.getToSellingPointId() == null) {
			return "Warning! revisar campos requeridos: 'fromSellingPointId', 'toSellingPointId', 'cost'";
		}
		if (costDto.getFromSellingPointId().equals(costDto.getToSellingPointId())) {
			return "No se puede cambiar el costo entre un punto de venta y si mismo. Se considerará siempre irrisorio (0)";
		}
		if (costDto.getCost() < 0) {
			return "El costo no puede tener un valor negativo";
		}
		
		salesService.saveCostBetweenSellingPoints(costDto.getFromSellingPointId(), costDto.getToSellingPointId(), costDto.getCost());
		return "Solicitud exitosa de creacion de costo entre dos puntos de venta. En caso de existir, sera actualizado";
	}

}
