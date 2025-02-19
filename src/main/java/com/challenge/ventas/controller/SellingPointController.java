package com.challenge.ventas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.ventas.persistence.dto.SellingPointDTO;
import com.challenge.ventas.persistence.model.SellingPoint;
import com.challenge.ventas.service.SalesService;

import io.micrometer.common.util.StringUtils;

@RestController
@RequestMapping("/selling-point")
public class SellingPointController {
	
	@Autowired
	private SalesService salesService;
	
	@GetMapping("/consult")
    public List<SellingPointDTO> getAllSellingPoints() {
        return salesService.findActiveSellingPoint(); 
    }
	
	@GetMapping("/consult/{id}")
	public SellingPointDTO getSellingPoint(@PathVariable Long id) {
		return salesService.findSellingPointDto(id); 
	}
	
	@PutMapping("/new")
	public String createSellingPoint(@RequestBody SellingPointDTO sellingPointDTO) {
		if (sellingPointDTO == null || StringUtils.isBlank(sellingPointDTO.getName())) {
			return "Warning! revisar campos requeridos: 'name'";
		}
		
		SellingPoint sellingPoint = new SellingPoint(sellingPointDTO.getName());
		sellingPoint = salesService.saveOrUpdateSellingPoint(sellingPoint);
		return "Punto de venta creado OK: " + sellingPoint.toString();
	}

	@PatchMapping("/update")
    public String updateSellingPoint(@RequestBody SellingPointDTO sellingPointDTO) {
		if (sellingPointDTO == null || sellingPointDTO.getId() == null || StringUtils.isBlank(sellingPointDTO.getName())) {
			return "Warning! revisar campos requeridos: 'name', 'id'";
		}
		
		SellingPoint sellingPoint = salesService.findSellingPoint(sellingPointDTO.getId());
		if (sellingPoint == null) {
			return "El punto de venta ingresado no existe o se encuentra borrado";
		}
		
        sellingPoint.setName(sellingPointDTO.getName());
        salesService.saveOrUpdateSellingPoint(sellingPoint);
        return "Punto de venta actualizado OK: " + sellingPoint.toString();
    }

	@DeleteMapping("/remove/{id}")
    public String removeSellingPoint(@PathVariable Long id) {
		if (id == null) {
			return "Warning! revisar campos requeridos: 'id'";
		}
		
		SellingPoint sellingPoint = salesService.findSellingPoint(id);
		if (sellingPoint == null) {
			return "El punto de venta ingresado no existe o se encuentra borrado";
		}
		
        salesService.removeSellingPoint(sellingPoint);
        return "Punto de venta borrado OK: " + sellingPoint.toString();
    }

}
