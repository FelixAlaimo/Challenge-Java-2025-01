package com.challenge.ventas.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.challenge.ventas.persistence.dto.CostBetweenSellingPointsDTO;
import com.challenge.ventas.persistence.dto.PathResultDTO;
import com.challenge.ventas.persistence.dto.SellingPointDTO;
import com.challenge.ventas.persistence.model.SaleAccreditation;
import com.challenge.ventas.persistence.model.CostBetweenSellingPoints;
import com.challenge.ventas.persistence.model.CostBetweenSellingPointsPk;
import com.challenge.ventas.persistence.model.SellingPoint;
import com.challenge.ventas.persistence.repository.IAccreditationsRepository;
import com.challenge.ventas.persistence.repository.ICostBetweenSellingPointsRepository;
import com.challenge.ventas.persistence.repository.ISellingPointRepository;

@Service
public class SalesService {
	
	@Autowired
	private ISellingPointRepository sellingPointRepo;

	@Autowired
	private ICostBetweenSellingPointsRepository costsRepo;
	
	@Autowired
	private IAccreditationsRepository accreditationsRepo;
	
	@Autowired
	private PathFinderService pathFinderService;
	
	// ******** Selling points ********
	@Cacheable(value = "sales:points", key = "'sales:points:active'")
	public List<SellingPointDTO> findActiveSellingPoint() {
		return sellingPointRepo.findActiveSellingPoint();
	}
	
	@Cacheable(value = "sales:point:dto", key = "#id")
	public SellingPointDTO findSellingPointDto(Long id) {
		return sellingPointRepo.findSellingPointDto(id);
	}
	
	@Cacheable(value = "sales:point", key = "#id")
	public SellingPoint findSellingPoint(Long id) {
		return sellingPointRepo.findSellingPoint(id);
	}
	
	@CacheEvict(value = {"sales:costs", "sales:costs:to", "sales:cost:direct", "sales:cost:shortest", "sales:points", "sales:point:dto", "sales:point"}, allEntries = true)
	public SellingPoint saveOrUpdateSellingPoint(SellingPoint sellingPoint) {
		return sellingPointRepo.save(sellingPoint);
	}
	
	@CacheEvict(value = {"sales:costs", "sales:costs:to", "sales:cost:direct", "sales:cost:shortest", "sales:points", "sales:point:dto", "sales:point"}, allEntries = true)
	public SellingPoint removeSellingPoint(SellingPoint sellingPoint) {
		sellingPoint.setDeletedDate(new Date());
	    return sellingPointRepo.save(sellingPoint);
	}
	
	// ******** Selling Costs ********
	@Cacheable(value = "sales:costs", key = "'sales:costs:active'")
	public List<CostBetweenSellingPointsDTO> findCostBetweenSellingPointsDTO() {
		return costsRepo.findCostBetweenSellingPointsDTO();
	}
	
	@Cacheable(value = "sales:costs:to", key = "#id")
	public List<CostBetweenSellingPointsDTO> findCostBetweenSellingPointsDTOById(Long id) {
		return costsRepo.findCostBetweenSellingPointsDTO(id);
	}
	
	@Cacheable(value = "sales:cost:direct", key = "#idFrom + ':' + #idTo")
	public CostBetweenSellingPointsDTO findDirectCostBetweenSellingPointsDTOByIds(Long idFrom, Long idTo) {
		return costsRepo.findCostBetweenSellingPointsDTO(Arrays.asList(idFrom, idTo));
	}
	
	@Cacheable(value = "sales:cost:shortest", key = "#idFrom + ':' + #idTo")
	public PathResultDTO findShortestCostBetweenSellingPointsDTOByIds(Long idFrom, Long idTo) {
		List<CostBetweenSellingPointsDTO> currentCosts = findCostBetweenSellingPointsDTO();
		return pathFinderService.findShortestPath(idFrom, idTo, currentCosts);
	}
	
	@CacheEvict(value = {"sales:costs", "sales:costs:to", "sales:cost:direct", "sales:cost:shortest", "sales:points", "sales:point:dto", "sales:point"}, allEntries = true)
	public void deleteCostBetweenSellingPoints(Long fromSellingPoint, Long toSellingPoint) {
		CostBetweenSellingPointsPk pk = new CostBetweenSellingPointsPk(new SellingPoint(fromSellingPoint), new SellingPoint(toSellingPoint));
		costsRepo.deleteById(pk);
	}

	@CacheEvict(value = {"sales:costs", "sales:costs:to", "sales:cost:direct", "sales:cost:shortest", "sales:points", "sales:point:dto", "sales:point"}, allEntries = true)
	public CostBetweenSellingPoints saveCostBetweenSellingPoints(Long fromSellingPoint, Long toSellingPoint, int cost) {
		CostBetweenSellingPoints costBetweenPoints = new CostBetweenSellingPoints(new SellingPoint(fromSellingPoint), new SellingPoint(toSellingPoint), cost);
	    return costsRepo.save(costBetweenPoints);
	}
	
	// ******** Sale Accreditation ********
	public List<SaleAccreditation> findAccreditations() {
		return accreditationsRepo.findAll();
	}
	
	public SaleAccreditation saveAccreditation(SaleAccreditation accreditation) {
		return accreditationsRepo.save(accreditation);
	}

}
