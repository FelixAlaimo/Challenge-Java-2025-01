package com.challenge.ventas.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.ventas.persistence.dto.CostBetweenSellingPointsDTO;
import com.challenge.ventas.persistence.model.CostBetweenSellingPoints;
import com.challenge.ventas.persistence.model.SellingPoint;
import com.challenge.ventas.service.SalesService;

@RestController
@RequestMapping("/costs")
public class CostsController {
	
	@Autowired
	private SalesService salesService;
	
	@GetMapping("/consult")
    public List<CostBetweenSellingPointsDTO> getSellingPoints() {
        return salesService.findCostBetweenSellingPointsDTO(); 
    }
	
	@GetMapping("/consult/{id}")
	public List<CostBetweenSellingPointsDTO> getCostBetweenSellingPoints(@PathVariable Long id) {
		if (id == null) {
			return new ArrayList<>();
		}
		
		return salesService.findCostBetweenSellingPointsDTOById(id);
	}
	
	@GetMapping("/consult/{id1}/{id2}")
	public String getCostBetweenSellingPoints(@PathVariable Long id1, @PathVariable Long id2) {
		if (id1 == null || id2 == null) {
			return "Warning! revisar campos requeridos: 'id' (de ambos puntos)";
		}
		
		if (id1.equals(id2)) {
			return "El costo entre un punto y si mismo se presume irrisorio (0)";
		}
		
		CostBetweenSellingPointsDTO cost = salesService.findCostBetweenSellingPointsDTOByIds(Arrays.asList(id1, id2));
		
		if (cost == null) {
			return "No se encontró un costo directo entre ambos puntos de venta. Puede ser que no exista o que alguno de los puntos de venta se encuentre borrado.";
		}
		
		return "El costo entre ambos puntos de venta es: " + cost.getCost();
	}
	
	@DeleteMapping("/remove/{id1}/{id2}")
	public String deleteCostBetweenSellingPoints(@PathVariable Long id1, @PathVariable Long id2) {
		if (id1 == null || id2 == null) {
			return "Warning! revisar campos requeridos: 'id' (de ambos puntos)";
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
			return "Warning! revisar campos requeridos: 'fromSellingPointId', 'toSellingPointId','cost'";
		}
		if (costDto.getFromSellingPointId().equals(costDto.getToSellingPointId())) {
			return "No se puede cambiar el costo entre un punto de venta y si mismo. Se considerará siempre irrisorio (0)";
		}
		if (costDto.getCost() < 0) {
			return "El costo no puede tener un valor negativo";
		}
		
		CostBetweenSellingPoints cost = new CostBetweenSellingPoints(new SellingPoint(costDto.getFromSellingPointId()), new SellingPoint(costDto.getToSellingPointId()), costDto.getCost());
		salesService.saveCostBetweenSellingPoints(cost);
		return "Solicitud exitosa de creacion de costo entre dos puntos de venta. En caso de existir, sera actualizado";
	}

}
