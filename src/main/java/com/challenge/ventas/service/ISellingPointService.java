package com.challenge.ventas.service;

import java.util.List;

import com.challenge.ventas.persistence.dto.SellingPointDTO;
import com.challenge.ventas.persistence.model.SellingPoint;

public interface ISellingPointService {

	List<SellingPointDTO> findActiveSellingPointDTOs();

	SellingPointDTO findActiveSellingPointDTO(Long sellingPointId);

	SellingPoint findActiveSellingPoint(Long sellingPointId);

	SellingPoint createSellingPoint(SellingPointDTO sellingPointDTO);
	
	SellingPoint updateSellingPoint(SellingPointDTO sellingPointDTO);

	SellingPoint removeSellingPoint(Long sellingPointId);

}
