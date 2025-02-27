package com.challenge.ventas.service.impl;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.challenge.ventas.exception.MissingRequiredFieldException;
import com.challenge.ventas.exception.ResourceNotFoundException;
import com.challenge.ventas.persistence.dto.SellingPointDTO;
import com.challenge.ventas.persistence.model.SellingPoint;
import com.challenge.ventas.persistence.repository.ISellingPointRepository;
import com.challenge.ventas.service.ISellingPointService;

import io.micrometer.common.util.StringUtils;

@Service
public class SellingPointService implements ISellingPointService {
	
	@Autowired
	private ISellingPointRepository sellingPointRepo;
	
	@Override
	@Cacheable(value = "sales:points", key = "'sales:points:active'")
	public List<SellingPointDTO> findActiveSellingPointDTOs() throws ResourceNotFoundException {
		List<SellingPointDTO> sellingPoints = sellingPointRepo.findActiveSellingPointDTOs();
		if (CollectionUtils.isEmpty(sellingPoints)) {
			throw new ResourceNotFoundException("Actualmente no se posee informacion sobre puntos de venta");
		}
		return sellingPoints;
	}
	
	@Override
	@Cacheable(value = "sales:point:dto", key = "#sellingPointId")
	public SellingPointDTO findActiveSellingPointDTO(Long sellingPointId) throws ResourceNotFoundException {
		return sellingPointRepo
				.findActiveSellingPointDTO(sellingPointId)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format("No se encontro el punto de venta ''{0}''", sellingPointId)));
	}
	
	@Override
	@Cacheable(value = "sales:point", key = "#sellingPointId")
	public SellingPoint findActiveSellingPoint(Long sellingPointId) throws ResourceNotFoundException {
		return sellingPointRepo.findActiveSellingPoint(sellingPointId)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format("No se encontro el punto de venta ''{0}''", sellingPointId)));
	}
	
	@Override
	@CacheEvict(value = {"sales:costs", "sales:costs:to", "sales:cost:direct", "sales:cost:shortest", "sales:points", "sales:point:dto", "sales:point"}, allEntries = true)
	public SellingPoint createSellingPoint(SellingPointDTO sellingPointDTO) throws MissingRequiredFieldException {
		if (sellingPointDTO == null || StringUtils.isBlank(sellingPointDTO.getName())) {
			throw new MissingRequiredFieldException("El atributo 'name' es requerido para crear el punto de venta");
		}
		
		return sellingPointRepo.save(new SellingPoint(sellingPointDTO.getName()));
	}
	
	@Override
	@CacheEvict(value = {"sales:costs", "sales:costs:to", "sales:cost:direct", "sales:cost:shortest", "sales:points", "sales:point:dto", "sales:point"}, allEntries = true)
	public SellingPoint updateSellingPoint(SellingPointDTO sellingPointDTO) throws MissingRequiredFieldException, ResourceNotFoundException {
		if (sellingPointDTO == null || sellingPointDTO.getId() == null || StringUtils.isBlank(sellingPointDTO.getName())) {
			throw new MissingRequiredFieldException("Los atributos 'name' e 'id' son requeridos para actualizar el punto de venta");
		}
		
		SellingPoint sellingPoint = findActiveSellingPoint(sellingPointDTO.getId());
        sellingPoint.setName(sellingPointDTO.getName());
		return sellingPointRepo.save(sellingPoint);
	}
	
	@Override
	@CacheEvict(value = {"sales:costs", "sales:costs:to", "sales:cost:direct", "sales:cost:shortest", "sales:points", "sales:point:dto", "sales:point"}, allEntries = true)
	public SellingPoint removeSellingPoint(Long sellingPointId) throws MissingRequiredFieldException, ResourceNotFoundException {
		if (sellingPointId == null) {
			throw new MissingRequiredFieldException("El atributo 'id' es requerido para remover el punto de venta");
		}
		
		SellingPoint sellingPoint = findActiveSellingPoint(sellingPointId);
		sellingPoint.setDeletedDate(new Date());
	    return sellingPointRepo.save(sellingPoint);
	}

}
