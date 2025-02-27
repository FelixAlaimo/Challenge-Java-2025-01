package com.challenge.ventas.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.ventas.exception.MissingRequiredFieldException;
import com.challenge.ventas.exception.ResourceNotFoundException;
import com.challenge.ventas.persistence.dto.AccreditationDTO;
import com.challenge.ventas.persistence.model.SaleAccreditation;
import com.challenge.ventas.persistence.model.SellingPoint;
import com.challenge.ventas.persistence.repository.IAccreditationsRepository;
import com.challenge.ventas.service.IAccreditationsService;
import com.challenge.ventas.service.ISellingPointService;
import com.challenge.ventas.utils.AccreditationConverter;

@Service
public class AccreditationsService implements IAccreditationsService {
	
	@Autowired
	private IAccreditationsRepository accreditationsRepo;

	@Autowired
	private ISellingPointService sellingPointService;
	
	@Override
	public List<AccreditationDTO> findAccreditationDTOs() throws ResourceNotFoundException {
		List<SaleAccreditation> accreditations = accreditationsRepo.findAll();
		
		return Optional.of(accreditations.stream().map(AccreditationConverter::toDTO).toList())
	    	.filter(list -> !list.isEmpty())
	    	.orElseThrow(() -> new ResourceNotFoundException("Actualmente no se posee informacion sobre acreditaciones"));
	}
	
	@Override
	public SaleAccreditation saveAccreditation(AccreditationDTO accreditationDTO) throws MissingRequiredFieldException, ResourceNotFoundException {
		if (accreditationDTO == null || accreditationDTO.getSellingPointId() == null || accreditationDTO.getAmount() == null) {
			throw new MissingRequiredFieldException("Los atributos 'sellingPointId' e 'amount' son requeridos para asentar una acreditacion");
		}
		
		SellingPoint sellingPoint = sellingPointService.findActiveSellingPoint(accreditationDTO.getSellingPointId());
		SaleAccreditation accreditation = new SaleAccreditation(sellingPoint, accreditationDTO.getAmount());
		return accreditationsRepo.save(accreditation);
	}

}
