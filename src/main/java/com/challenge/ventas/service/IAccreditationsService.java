package com.challenge.ventas.service;

import java.util.List;

import com.challenge.ventas.persistence.dto.AccreditationDTO;
import com.challenge.ventas.persistence.model.SaleAccreditation;

public interface IAccreditationsService {

	List<AccreditationDTO> findAccreditationDTOs();

	SaleAccreditation saveAccreditation(AccreditationDTO accreditationDTO);

}
