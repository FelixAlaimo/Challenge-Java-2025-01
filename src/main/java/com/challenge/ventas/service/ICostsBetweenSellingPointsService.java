package com.challenge.ventas.service;

import java.util.List;

import com.challenge.ventas.exception.BusinessRuleException;
import com.challenge.ventas.exception.MissingRequiredFieldException;
import com.challenge.ventas.exception.ResourceNotFoundException;
import com.challenge.ventas.persistence.dto.CostBetweenSellingPointsDTO;

public interface ICostsBetweenSellingPointsService {

	List<CostBetweenSellingPointsDTO> findCostBetweenSellingPointsDTOs() throws ResourceNotFoundException;

	List<CostBetweenSellingPointsDTO> findCostBetweenSellingPointsDTOById(Long id) throws ResourceNotFoundException, MissingRequiredFieldException;

	CostBetweenSellingPointsDTO findDirectCostBetweenSellingPointsDTOByIds(Long idFrom, Long idTo) throws ResourceNotFoundException, MissingRequiredFieldException;

	void deleteCostBetweenSellingPoints(Long fromSellingPoint, Long toSellingPoint) throws BusinessRuleException, ResourceNotFoundException, MissingRequiredFieldException;

	String saveCostBetweenSellingPoints(CostBetweenSellingPointsDTO costDto) throws MissingRequiredFieldException, BusinessRuleException, ResourceNotFoundException;

}
