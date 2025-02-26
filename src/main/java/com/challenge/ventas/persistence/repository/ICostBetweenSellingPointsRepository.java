package com.challenge.ventas.persistence.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.challenge.ventas.persistence.dto.CostBetweenSellingPointsDTO;
import com.challenge.ventas.persistence.model.CostBetweenSellingPoints;
import com.challenge.ventas.persistence.model.CostBetweenSellingPointsPk;

public interface ICostBetweenSellingPointsRepository extends JpaRepository<CostBetweenSellingPoints, CostBetweenSellingPointsPk> {
	
	@Query("select NEW com.challenge.ventas.persistence.dto.CostBetweenSellingPointsDTO("
			+ "fromPoint.id, fromPoint.name, toPoint.id, toPoint.name, cost.amount) "
			+ "from CostBetweenSellingPoints cost join cost.id.fromSellingPoint fromPoint join cost.id.toSellingPoint toPoint "
			+ "where fromPoint.deletedDate is null and toPoint.deletedDate is null")
	List<CostBetweenSellingPointsDTO> findCostBetweenSellingPointsDTO();
	
	@Query("select NEW com.challenge.ventas.persistence.dto.CostBetweenSellingPointsDTO("
			+ "fromPoint.id, fromPoint.name, toPoint.id, toPoint.name, cost.amount) "
			+ "from CostBetweenSellingPoints cost join cost.id.fromSellingPoint fromPoint join cost.id.toSellingPoint toPoint "
			+ "where (fromPoint.id = :id or toPoint.id = :id) "
			+ "and fromPoint.deletedDate is null and toPoint.deletedDate is null")
	List<CostBetweenSellingPointsDTO> findCostBetweenSellingPointsDTO(Long id);
	
	@Query("select cost from CostBetweenSellingPoints cost "
			+ "join cost.id.fromSellingPoint fromPoint join cost.id.toSellingPoint toPoint "
			+ "where ((fromPoint.id = :id1 and toPoint.id = :id2) OR (fromPoint.id = :id2 and toPoint.id = :id1)) "
			+ "and fromPoint.deletedDate is null and toPoint.deletedDate is null")
	List<CostBetweenSellingPoints> findCostBetweenSellingPoints(Long id1, Long id2, Pageable pageable);


}
