package com.challenge.ventas.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.challenge.ventas.exception.ResourceNotFoundException;
import com.challenge.ventas.persistence.dto.PathResultDTO;
import com.challenge.ventas.service.ISellingPointService;

@Component
public class CostsFormatter {
	
	@Autowired
	private ISellingPointService sellingPointService;
	
	public String[] formatPathPointsWithName(PathResultDTO result) {
		String[] formattedPathPoints = null;
		if (result != null && !CollectionUtils.isEmpty(result.getSellingPointsPath())) {
			return result.getSellingPointsPath().stream()
					.map(spId -> {
						try {
							return sellingPointService.findActiveSellingPointDTO(spId).getName() + " (" + spId + ")";
						} catch (ResourceNotFoundException e) {
							return "Missing_Name (" + spId + ")";
						}
					})
					.toArray(String[]::new);			
		}
		return formattedPathPoints;
	}

}
