package com.challenge.ventas.service.impl;

import java.text.MessageFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.challenge.ventas.exception.BusinessRuleException;
import com.challenge.ventas.exception.MissingRequiredFieldException;
import com.challenge.ventas.exception.ResourceNotFoundException;
import com.challenge.ventas.helper.SellingPointValidator;
import com.challenge.ventas.persistence.dto.CostBetweenSellingPointsDTO;
import com.challenge.ventas.persistence.model.CostBetweenSellingPoints;
import com.challenge.ventas.persistence.model.SellingPoint;
import com.challenge.ventas.persistence.repository.ICostBetweenSellingPointsRepository;
import com.challenge.ventas.service.ICostsBetweenSellingPointsService;
import com.challenge.ventas.service.ISellingPointService;
import com.challenge.ventas.utils.CostBetweenSellingPointsConverter;

@Service
public class CostsBetweenSellingPointsService implements ICostsBetweenSellingPointsService {
	
	@Autowired
	private ICostBetweenSellingPointsRepository costsRepo;
	
	@Autowired
	private ISellingPointService sellingPointService;

	@Autowired
	private SellingPointValidator sellingPointValidator;
	
	@Override
	@Cacheable(value = "sales:costs", key = "'sales:costs:active'")
	public List<CostBetweenSellingPointsDTO> findCostBetweenSellingPointsDTOs() throws ResourceNotFoundException {
		List<CostBetweenSellingPointsDTO> costs = costsRepo.findCostBetweenSellingPointsDTO();
		if (CollectionUtils.isEmpty(costs)) {
			throw new ResourceNotFoundException("Actualmente no se posee informacion sobre costos entre puntos de venta");
		}
		return costs;
	}

	@Override
	@Cacheable(value = "sales:costs:to", key = "#sellingPointId")
	public List<CostBetweenSellingPointsDTO> findCostBetweenSellingPointsDTOById(Long sellingPointId) throws ResourceNotFoundException, MissingRequiredFieldException {
		
		sellingPointValidator.validateSellingPointExistence(sellingPointId, "id");
		
		List<CostBetweenSellingPointsDTO> costs = costsRepo.findCostBetweenSellingPointsDTO(sellingPointId);
		if (CollectionUtils.isEmpty(costs)) {
			throw new ResourceNotFoundException(MessageFormat.format("No se encontraron costos hacia el punto de venta ''{0}''", sellingPointId));
		}
		
		return costs;
	}

	@Override
	@Cacheable(value = "sales:cost:direct", key = "#fromSellingPointId + ':' + #toSellingPointId")
	public CostBetweenSellingPointsDTO findDirectCostBetweenSellingPointsDTOByIds(Long fromSellingPointId, Long toSellingPointId) throws ResourceNotFoundException, MissingRequiredFieldException {
		
		sellingPointValidator.validateSellingPointExistence(fromSellingPointId, "id");
		sellingPointValidator.validateSellingPointExistence(toSellingPointId, "id");
		
		if (fromSellingPointId.equals(toSellingPointId)) {
			// cost between a sellingPoint and itself is considered 0
			return new CostBetweenSellingPointsDTO(0);
		}
		
		List<CostBetweenSellingPoints> cost = costsRepo.findCostBetweenSellingPoints(fromSellingPointId, toSellingPointId, PageRequest.of(0, 1));
		if (CollectionUtils.isEmpty(cost)) {
			throw new ResourceNotFoundException(MessageFormat.format("No se encontro un costo entre ''{0}'' y ''{1}''", fromSellingPointId, toSellingPointId));
		}
		return CostBetweenSellingPointsConverter.toDTO(cost.get(0));
	}

	@Override
	@CacheEvict(value = {"sales:costs", "sales:costs:to", "sales:cost:direct", "sales:cost:shortest", "sales:points", "sales:point:dto", "sales:point"}, allEntries = true)
	public void deleteCostBetweenSellingPoints(Long fromSellingPointId, Long toSellingPointId) throws BusinessRuleException, ResourceNotFoundException, MissingRequiredFieldException {
		
		sellingPointValidator.validateSellingPointExistence(fromSellingPointId, "id");
		sellingPointValidator.validateSellingPointExistence(toSellingPointId, "id");
		
		if (fromSellingPointId.equals(toSellingPointId)) {
			throw new BusinessRuleException("No se puede borrar el costo entre un punto y si mismo, ya que se presume irrisorio (0)");
		}
		
		List<CostBetweenSellingPoints> cost = costsRepo.findCostBetweenSellingPoints(fromSellingPointId, toSellingPointId, PageRequest.of(0, 1));
		if (CollectionUtils.isEmpty(cost)) {
			throw new ResourceNotFoundException(MessageFormat.format("No se encontro un costo entre ''{0}'' y ''{1}'' para ser borrado", fromSellingPointId, toSellingPointId));			
		}
		costsRepo.delete(cost.get(0));
	}

	@Override
	@CacheEvict(value = {"sales:costs", "sales:costs:to", "sales:cost:direct", "sales:cost:shortest", "sales:points", "sales:point:dto", "sales:point"}, allEntries = true)
	public String saveCostBetweenSellingPoints(CostBetweenSellingPointsDTO costDto) throws MissingRequiredFieldException, BusinessRuleException, ResourceNotFoundException {
		
		if (costDto == null) {
			throw new MissingRequiredFieldException("Para establecer el costo entre dos puntos se requieren los campos 'fromSellingPointId', 'toSellingPointId' y 'cost'");
		}
		
		Long fromSellingPointId = costDto.getFromSellingPointId();
		Long toSellingPointId = costDto.getToSellingPointId();
		int costValue = costDto.getCost();
		
		sellingPointValidator.validateNullableField(fromSellingPointId, "fromSellingPointId");
		sellingPointValidator.validateNullableField(toSellingPointId, "toSellingPointId");
		
		if (fromSellingPointId.equals(toSellingPointId)) {
			throw new BusinessRuleException("No se puede establecer un costo entre un punto y si mismo, ya que se presume irrisorio (0)");
		}
		
		if (costValue < 1) {
			throw new BusinessRuleException("El costo debe ser positivo");
		}
		
		SellingPoint fromSellingPoint = sellingPointService.findActiveSellingPoint(fromSellingPointId);
	    SellingPoint toSellingPoint = sellingPointService.findActiveSellingPoint(toSellingPointId);
	    
	    List<CostBetweenSellingPoints> persistedCost = costsRepo.findCostBetweenSellingPoints(fromSellingPointId, toSellingPointId, PageRequest.of(0, 1));
	    
	    String operationPerformed = "CREADO";
	    CostBetweenSellingPoints costBetweenPoints = null;
	    if (CollectionUtils.isEmpty(persistedCost)) {
	    	costBetweenPoints = new CostBetweenSellingPoints(fromSellingPoint, toSellingPoint, costValue);
	    }
	    else {
	    	operationPerformed = "ACTUALIZADO";
	    	costBetweenPoints = persistedCost.get(0);
	    	costBetweenPoints.setAmount(costValue);
	    }
	    
	    costsRepo.save(costBetweenPoints);
	    return operationPerformed;
	}
	
	

}
