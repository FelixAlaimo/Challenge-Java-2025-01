package com.challenge.ventas.service;

import java.util.List;

import com.challenge.ventas.exception.MissingRequiredFieldException;
import com.challenge.ventas.exception.ResourceNotFoundException;
import com.challenge.ventas.persistence.dto.SellingPointDTO;
import com.challenge.ventas.persistence.model.SellingPoint;

public interface ISellingPointService {

	List<SellingPointDTO> findActiveSellingPointDTOs() throws ResourceNotFoundException;

	SellingPointDTO findActiveSellingPointDTO(Long sellingPointId) throws ResourceNotFoundException;

	SellingPoint findActiveSellingPoint(Long sellingPointId) throws ResourceNotFoundException;

	SellingPoint createSellingPoint(SellingPointDTO sellingPointDTO) throws MissingRequiredFieldException;
	
	SellingPoint updateSellingPoint(SellingPointDTO sellingPointDTO) throws MissingRequiredFieldException, ResourceNotFoundException;

	SellingPoint removeSellingPoint(Long sellingPointId) throws MissingRequiredFieldException, ResourceNotFoundException;

}
