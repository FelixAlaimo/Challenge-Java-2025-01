package com.challenge.ventas.utils;

import com.challenge.ventas.persistence.dto.CostBetweenSellingPointsDTO;
import com.challenge.ventas.persistence.model.CostBetweenSellingPoints;
import com.challenge.ventas.persistence.model.CostBetweenSellingPointsPk;

public class CostBetweenSellingPointsConverter {
	
	private CostBetweenSellingPointsConverter() {
		// private default constructor for utility class
	}
	
	public static CostBetweenSellingPointsDTO toDTO(CostBetweenSellingPoints costEntity) {
		if (costEntity == null || costEntity.getId() == null) {
	        return null;
	    }

	    CostBetweenSellingPointsPk costPk = costEntity.getId();
	    if (costPk.getFromSellingPoint() == null || costPk.getToSellingPoint() == null) {
	        throw new IllegalStateException("Falla en la integridad de los datos de esta operacion.");
	    }

	    CostBetweenSellingPointsDTO dto = new CostBetweenSellingPointsDTO();
	    dto.setFromSellingPointId(costPk.getFromSellingPoint().getId());
	    dto.setFromSellingPointName(costPk.getFromSellingPoint().getName());
	    dto.setToSellingPointId(costPk.getToSellingPoint().getId());
	    dto.setToSellingPointName(costPk.getToSellingPoint().getName());
	    dto.setCost(costEntity.getAmount());

	    return dto;
    }

}
