package com.challenge.ventas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.ventas.persistence.dto.CostBetweenSellingPointsDTO;
import com.challenge.ventas.persistence.dto.SellingPointDTO;
import com.challenge.ventas.persistence.model.SaleAccreditation;
import com.challenge.ventas.persistence.model.CostBetweenSellingPoints;
import com.challenge.ventas.persistence.model.CostBetweenSellingPointsPk;
import com.challenge.ventas.persistence.model.SellingPoint;
import com.challenge.ventas.persistence.repository.IAccreditationsRepository;
import com.challenge.ventas.persistence.repository.ICostBetweenSellingPointsRepository;
import com.challenge.ventas.persistence.repository.ISellingPointRepository;

import jakarta.annotation.PostConstruct;

@Service
public class SalesService {
	
	@Autowired
	private ISellingPointRepository sellingPointRepo;

	@Autowired
	private ICostBetweenSellingPointsRepository costsRepo;
	
	@Autowired
	private IAccreditationsRepository accreditationsRepo;
	
	@PostConstruct
	public void init() {
		SellingPoint puntoDeVenta1 = new SellingPoint("CABA");
		sellingPointRepo.saveAndFlush(puntoDeVenta1);
		SellingPoint puntoDeVenta2 = new SellingPoint("GBA_1");
		sellingPointRepo.saveAndFlush(puntoDeVenta2);
		SellingPoint puntoDeVenta3 = new SellingPoint("GBA_2");
		sellingPointRepo.saveAndFlush(puntoDeVenta3);
		SellingPoint puntoDeVenta4 = new SellingPoint("Santa Fe");
		sellingPointRepo.saveAndFlush(puntoDeVenta4);
		SellingPoint puntoDeVenta5 = new SellingPoint("Córdoba");
		sellingPointRepo.saveAndFlush(puntoDeVenta5);
		SellingPoint puntoDeVenta6 = new SellingPoint("Misiones");
		sellingPointRepo.saveAndFlush(puntoDeVenta6);
		SellingPoint puntoDeVenta7 = new SellingPoint("Salta");
		sellingPointRepo.saveAndFlush(puntoDeVenta7);
		SellingPoint puntoDeVenta8 = new SellingPoint("Chubut");
		sellingPointRepo.saveAndFlush(puntoDeVenta8);
		SellingPoint puntoDeVenta9 = new SellingPoint("Santa Cruz");
		sellingPointRepo.saveAndFlush(puntoDeVenta9);
		SellingPoint puntoDeVenta10 = new SellingPoint("Catamarca");
		sellingPointRepo.saveAndFlush(puntoDeVenta10);
		
		costsRepo.save(new CostBetweenSellingPoints(puntoDeVenta1, puntoDeVenta2, 2));
		costsRepo.save(new CostBetweenSellingPoints(puntoDeVenta1, puntoDeVenta3, 3));
		costsRepo.save(new CostBetweenSellingPoints(puntoDeVenta1, puntoDeVenta4, 11));
		costsRepo.save(new CostBetweenSellingPoints(puntoDeVenta2, puntoDeVenta3, 5));
		costsRepo.save(new CostBetweenSellingPoints(puntoDeVenta2, puntoDeVenta4, 10));
		costsRepo.save(new CostBetweenSellingPoints(puntoDeVenta2, puntoDeVenta5, 14));
		costsRepo.save(new CostBetweenSellingPoints(puntoDeVenta3, puntoDeVenta8, 10));
		costsRepo.save(new CostBetweenSellingPoints(puntoDeVenta4, puntoDeVenta5, 5));
		costsRepo.save(new CostBetweenSellingPoints(puntoDeVenta4, puntoDeVenta6, 6));
		costsRepo.save(new CostBetweenSellingPoints(puntoDeVenta5, puntoDeVenta8, 30));
		costsRepo.save(new CostBetweenSellingPoints(puntoDeVenta6, puntoDeVenta7, 32));
		costsRepo.save(new CostBetweenSellingPoints(puntoDeVenta8, puntoDeVenta9, 11));
		costsRepo.save(new CostBetweenSellingPoints(puntoDeVenta10, puntoDeVenta5, 5));
		costsRepo.save(new CostBetweenSellingPoints(puntoDeVenta10, puntoDeVenta7, 5));
		
	}
	
	// ******** Selling points ********
	public List<SellingPointDTO> findActiveSellingPoint() {
		return sellingPointRepo.findActiveSellingPoint();
	}
	
	public SellingPointDTO findSellingPointDto(Long id) {
		return sellingPointRepo.findSellingPointDto(id);
	}
	
	public SellingPoint findSellingPoint(Long id) {
		return sellingPointRepo.findSellingPoint(id);
	}
	
	public SellingPoint saveOrUpdateSellingPoint(SellingPoint sellingPoint) {
	    return sellingPointRepo.save(sellingPoint);
	}
	
	// ******** Selling Costs ********
	public List<CostBetweenSellingPointsDTO> findCostBetweenSellingPointsDTO() {
		return costsRepo.findCostBetweenSellingPointsDTO();
	}
	
	public List<CostBetweenSellingPointsDTO> findCostBetweenSellingPointsDTOById(Long id) {
		return costsRepo.findCostBetweenSellingPointsDTO(id);
	}
	
	public CostBetweenSellingPointsDTO findCostBetweenSellingPointsDTOByIds(List<Long> ids) {
		return costsRepo.findCostBetweenSellingPointsDTO(ids);
	}
	
	public void deleteCostBetweenSellingPoints(Long fromSellingPoint, Long toSellingPoint) {
		CostBetweenSellingPointsPk pk = new CostBetweenSellingPointsPk(new SellingPoint(fromSellingPoint), new SellingPoint(toSellingPoint));
		costsRepo.deleteById(pk);
	}

	public CostBetweenSellingPoints saveCostBetweenSellingPoints(CostBetweenSellingPoints cost) {
	    return costsRepo.save(cost);
	}
	
	// ******** Sale Accreditation ********
	public List<SaleAccreditation> findAccreditations() {
		return accreditationsRepo.findAll();
	}
	
	public SaleAccreditation saveAccreditation(SaleAccreditation accreditation) {
		return accreditationsRepo.save(accreditation);
	}

}
