package com.challenge.ventas.utils;

import com.challenge.ventas.persistence.dto.CostBetweenSellingPointsDTO;
import com.challenge.ventas.persistence.model.CostBetweenSellingPoints;
import com.challenge.ventas.persistence.model.CostBetweenSellingPointsPk;

public class CostBetweenSellingPointsConverter {
	
	private CostBetweenSellingPointsConverter() {
		// private default constructor for utility class
	}
	
	public static CostBetweenSellingPointsDTO toDTO(CostBetweenSellingPoints costEntity) {
        CostBetweenSellingPointsDTO dto = null;
        if (costEntity != null && costEntity.getId() != null) {
        	CostBetweenSellingPointsPk costPk = costEntity.getId();
        	dto = new CostBetweenSellingPointsDTO();
        	dto.setFromSellingPointId(costPk.getFromSellingPoint().getId());
        	dto.setFromSellingPointName(costPk.getFromSellingPoint().getName());
        	dto.setToSellingPointId(costPk.getToSellingPoint().getId());
        	dto.setToSellingPointName(costPk.getToSellingPoint().getName());
        	dto.setCost(costEntity.getAmount());
        }
        return dto;
    }

}
