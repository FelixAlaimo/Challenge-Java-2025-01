package com.challenge.ventas.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.ventas.controller.IAccreditationsController;
import com.challenge.ventas.persistence.dto.AccreditationDTO;
import com.challenge.ventas.persistence.model.SaleAccreditation;
import com.challenge.ventas.service.IAccreditationsService;

@RestController
@RequestMapping("/sale-accreditation")
public class AccreditationsController implements IAccreditationsController {
	
	@Autowired
	private IAccreditationsService accreditationsService;
	
	@GetMapping("/")
	@Transactional(readOnly = true)
    public ResponseEntity<List<AccreditationDTO>> getAccreditations() {
        return ResponseEntity.ok(accreditationsService.findAccreditationDTOs());
    }

	@PostMapping("/")
	@Transactional
    public ResponseEntity<String> saveNewAccreditation(@RequestBody AccreditationDTO accreditationDTO) {
		SaleAccreditation accreditation = accreditationsService.saveAccreditation(accreditationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Se asento correctamente la acreditacion: " + accreditation.toString()); 
    }

}
