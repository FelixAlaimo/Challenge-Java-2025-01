package com.challenge.ventas.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.challenge.ventas.persistence.dto.SellingPointDTO;
import com.challenge.ventas.persistence.model.SellingPoint;

public interface ISellingPointRepository extends JpaRepository<SellingPoint, Long> {
	
	@Query("select NEW com.challenge.ventas.persistence.dto.SellingPointDTO(p.id, p.name) from PuntoDeVenta p where p.deletedDate is null")
	List<SellingPointDTO> findActiveSellingPoint();
	
	@Query("select NEW com.challenge.ventas.persistence.dto.SellingPointDTO(p.id, p.name) from PuntoDeVenta p where p.deletedDate is null and p.id = ?1")
	SellingPointDTO findSellingPointDto(Long id);
	
	@Query("select sp from SellingPoint sp where sp.deletedDate is null and sp.id = ?1")
	SellingPoint findSellingPoint(Long id);

}
