package com.challenge.ventas.service;

import java.util.List;

import com.challenge.ventas.persistence.dto.CostBetweenSellingPointsDTO;

public interface ICostsBetweenSellingPointsService {

	List<CostBetweenSellingPointsDTO> findCostBetweenSellingPointsDTOs();

	List<CostBetweenSellingPointsDTO> findCostBetweenSellingPointsDTOById(Long id);

	CostBetweenSellingPointsDTO findDirectCostBetweenSellingPointsDTOByIds(Long idFrom, Long idTo);

	void deleteCostBetweenSellingPoints(Long fromSellingPoint, Long toSellingPoint);

	String saveCostBetweenSellingPoints(CostBetweenSellingPointsDTO costDto);

}
