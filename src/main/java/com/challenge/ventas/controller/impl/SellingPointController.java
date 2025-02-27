package com.challenge.ventas.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.ventas.controller.ISellingPointController;
import com.challenge.ventas.exception.MissingRequiredFieldException;
import com.challenge.ventas.exception.ResourceNotFoundException;
import com.challenge.ventas.persistence.dto.SellingPointDTO;
import com.challenge.ventas.persistence.model.SellingPoint;
import com.challenge.ventas.service.ISellingPointService;

@RestController
@RequestMapping("/selling-point")
public class SellingPointController implements ISellingPointController {
	
	@Autowired
	private ISellingPointService sellingPointService;
	
	@GetMapping("/")
	@Transactional(readOnly = true)
    public ResponseEntity<List<SellingPointDTO>> getAllSellingPoints() throws ResourceNotFoundException {
        return ResponseEntity.ok(sellingPointService.findActiveSellingPointDTOs()); 
    }
	
	@GetMapping("/{sellingPointId}")
	@Transactional(readOnly = true)
	public ResponseEntity<SellingPointDTO> getSellingPoint(@PathVariable Long sellingPointId) throws ResourceNotFoundException {
		return ResponseEntity.ok(sellingPointService.findActiveSellingPointDTO(sellingPointId));
	}
	
	@PostMapping("/")
	@Transactional
	public ResponseEntity<String> createSellingPoint(@RequestBody SellingPointDTO sellingPointDTO) throws MissingRequiredFieldException {
		SellingPoint sellingPoint = sellingPointService.createSellingPoint(sellingPointDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body("Punto de venta creado OK: " + sellingPoint.toString());
	}

	@PatchMapping("/")
	@Transactional
    public ResponseEntity<String> updateSellingPoint(@RequestBody SellingPointDTO sellingPointDTO) throws MissingRequiredFieldException, ResourceNotFoundException {
		SellingPoint sellingPoint = sellingPointService.updateSellingPoint(sellingPointDTO);
        return ResponseEntity.ok("Punto de venta actualizado OK: " + sellingPoint.toString());
    }

	@DeleteMapping("/{sellingPointId}")
	@Transactional
    public ResponseEntity<String> removeSellingPoint(@PathVariable Long sellingPointId) throws MissingRequiredFieldException, ResourceNotFoundException {
		sellingPointService.removeSellingPoint(sellingPointId);
        return ResponseEntity.noContent().build();
    }

}
