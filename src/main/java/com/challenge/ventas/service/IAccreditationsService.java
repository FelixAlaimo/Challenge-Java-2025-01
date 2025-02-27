package com.challenge.ventas.service;

import java.util.List;

import com.challenge.ventas.exception.MissingRequiredFieldException;
import com.challenge.ventas.exception.ResourceNotFoundException;
import com.challenge.ventas.persistence.dto.AccreditationDTO;
import com.challenge.ventas.persistence.model.SaleAccreditation;

public interface IAccreditationsService {

	List<AccreditationDTO> findAccreditationDTOs() throws ResourceNotFoundException;

	SaleAccreditation saveAccreditation(AccreditationDTO accreditationDTO) throws MissingRequiredFieldException, ResourceNotFoundException;

}
