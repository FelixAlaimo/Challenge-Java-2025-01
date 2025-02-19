package com.challenge.ventas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.ventas.persistence.dto.AccreditationDTO;
import com.challenge.ventas.persistence.model.SaleAccreditation;
import com.challenge.ventas.persistence.model.SellingPoint;
import com.challenge.ventas.service.SalesService;

@RestController
@RequestMapping("/sale-accreditation")
public class AccreditationsController {
	
	@Autowired
	private SalesService salesService;
	
	@GetMapping("/all")
    public List<SaleAccreditation> getAccreditations() {
        return salesService.findAccreditations(); 
    }

	@PutMapping("/new")
    public String saveNewAccreditation(@RequestBody AccreditationDTO accreditationDTO) {
		if (accreditationDTO == null || accreditationDTO.getSellingPointId() == null || accreditationDTO.getAmount() == null) {
			return "Warning! revisar campos requeridos: 'sellingPointId', 'amount'";
		}
		
		SellingPoint sellingPoint = salesService.findSellingPoint(accreditationDTO.getSellingPointId());
		if (sellingPoint == null) {
			return "El punto de venta enviado no es válido.";
		}
		
		SaleAccreditation accreditation = new SaleAccreditation(accreditationDTO.getSellingPointId(), sellingPoint.getName(), accreditationDTO.getAmount());
		salesService.saveAccreditation(accreditation);
        return "Se procesó exitosamente la carga de la acreditación."; 
    }

}
