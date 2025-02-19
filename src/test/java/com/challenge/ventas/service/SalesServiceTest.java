package com.challenge.ventas.service;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.challenge.ventas.persistence.model.SaleAccreditation;
import com.challenge.ventas.persistence.model.CostBetweenSellingPoints;
import com.challenge.ventas.persistence.model.CostBetweenSellingPointsPk;
import com.challenge.ventas.persistence.model.SellingPoint;
import com.challenge.ventas.persistence.repository.IAccreditationsRepository;
import com.challenge.ventas.persistence.repository.ICostBetweenSellingPointsRepository;
import com.challenge.ventas.persistence.repository.ISellingPointRepository;

@TestInstance(Lifecycle.PER_CLASS)
class SalesServiceTest {
	
	@Mock
	private ISellingPointRepository sellingPointRepo;
	@Mock
	private ICostBetweenSellingPointsRepository costsRepo;
	@Mock
	private IAccreditationsRepository accreditationsRepo;
	
	@Captor
	private ArgumentCaptor<List<Long>> listCaptor;
	
	@InjectMocks
	private SalesService service;
	
	@BeforeAll
	public void init() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void testFindActiveSellingPoint() {
		service.findActiveSellingPoint();
		Mockito.verify(sellingPointRepo, Mockito.times(1)).findActiveSellingPoint();
	}
	
	@Test
	public void testFindSellingPointDto() {
		service.findSellingPointDto(22L);
		Mockito.verify(sellingPointRepo, Mockito.times(1)).findSellingPointDto(22L);
	}
	
	@Test
	public void testFindSellingPoint() {
		service.findSellingPoint(24L);
		Mockito.verify(sellingPointRepo, Mockito.times(1)).findSellingPoint(24L);
	}
	
	@Test
	public void testSaveOrUpdateSellingPoint() {
		SellingPoint p = new SellingPoint(192L);
		service.saveOrUpdateSellingPoint(p);
		Mockito.verify(sellingPointRepo, Mockito.times(1)).save(p);
	}
	
	@Test
	public void testRemoveSellingPoint() {
		SellingPoint sellingPoint = new SellingPoint(383L);
		service.removeSellingPoint(sellingPoint);
		Mockito.verify(sellingPointRepo, Mockito.times(1)).save(sellingPoint);
		Assertions.assertNotNull(sellingPoint.getDeletedDate());
	}
	
	@Test
	public void testFindCostBetweenSellingPointsDTO() {
		service.findCostBetweenSellingPointsDTO();
		Mockito.verify(costsRepo, Mockito.times(1)).findCostBetweenSellingPointsDTO();
	}
	
	@Test
	public void testFindCostBetweenSellingPointsDTOById() {
		service.findCostBetweenSellingPointsDTOById(26L);
		Mockito.verify(costsRepo, Mockito.times(1)).findCostBetweenSellingPointsDTO(26L);
	}
	
	@Test
	public void testFindCostBetweenSellingPointsDTOByIds() {
		service.findCostBetweenSellingPointsDTOByIds(39L, 72L);
		Mockito.verify(costsRepo, Mockito.times(1)).findCostBetweenSellingPointsDTO(listCaptor.capture());
		List<Long> captList = listCaptor.getValue();
		Assertions.assertEquals(2, captList.size());
		Assertions.assertTrue(captList.contains(72L));
		Assertions.assertTrue(captList.contains(39L));
	}
	
	@Test
	public void testDeleteCostBetweenSellingPoints() {
		List<SellingPoint> puntos = Arrays.asList(new SellingPoint(1111L), new SellingPoint(2222L));
		
		service.deleteCostBetweenSellingPoints(puntos.get(0).getId(), puntos.get(1).getId());
		ArgumentCaptor<CostBetweenSellingPointsPk> captor = ArgumentCaptor.forClass(CostBetweenSellingPointsPk.class);
		Mockito.verify(costsRepo, Mockito.times(1)).deleteById(captor.capture());
		CostBetweenSellingPointsPk captPk = captor.getValue();
		Assertions.assertTrue(puntos.contains(captPk.getFromSellingPoint()));
		Assertions.assertTrue(puntos.contains(captPk.getToSellingPoint()));
	}
	
	@Test
	public void testSaveCostBetweenSellingPoints() {
		service.saveCostBetweenSellingPoints(1111L, 2222L, 7000);
		ArgumentCaptor<CostBetweenSellingPoints> costCaptor = ArgumentCaptor.forClass(CostBetweenSellingPoints.class);
		Mockito.verify(costsRepo, Mockito.times(1)).save(costCaptor.capture());
		CostBetweenSellingPoints captCost = costCaptor.getValue();
		List<Long> ids = Arrays.asList(1111L, 2222L);
		Assertions.assertTrue(ids.contains(captCost.getId().getFromSellingPoint().getId()));
		Assertions.assertTrue(ids.contains(captCost.getId().getToSellingPoint().getId()));
		Assertions.assertEquals(7000, captCost.getAmount());
	}
	
	@Test
	public void testFindAccreditations() {
		service.findAccreditations();
		Mockito.verify(accreditationsRepo, Mockito.times(1)).findAll();
	}
	
	@Test
	public void testSaveAccreditation() {
		SaleAccreditation acreditacion = new SaleAccreditation(1L, "pName", 70000L);
		service.saveAccreditation(acreditacion);
		Mockito.verify(accreditationsRepo, Mockito.times(1)).save(acreditacion);
	}

}
