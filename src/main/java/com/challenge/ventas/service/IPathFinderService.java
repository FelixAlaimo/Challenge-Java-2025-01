package com.challenge.ventas.service;

import java.util.List;

import com.challenge.ventas.exception.MissingRequiredFieldException;
import com.challenge.ventas.exception.ResourceNotFoundException;
import com.challenge.ventas.persistence.dto.CostBetweenSellingPointsDTO;
import com.challenge.ventas.persistence.dto.PathResultDTO;

public interface IPathFinderService {

	PathResultDTO findShortestPath(Long start, Long end, List<CostBetweenSellingPointsDTO> existingCosts) throws MissingRequiredFieldException, ResourceNotFoundException;

}
