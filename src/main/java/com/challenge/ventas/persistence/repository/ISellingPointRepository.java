package com.challenge.ventas.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.challenge.ventas.persistence.dto.SellingPointDTO;
import com.challenge.ventas.persistence.model.SellingPoint;

public interface ISellingPointRepository extends JpaRepository<SellingPoint, Long> {
	
	@Query("select NEW com.challenge.ventas.persistence.dto.SellingPointDTO(sp.id, sp.name) from SellingPoint sp where sp.deletedDate is null")
	List<SellingPointDTO> findActiveSellingPointDTOs();
	
	@Query("select NEW com.challenge.ventas.persistence.dto.SellingPointDTO(sp.id, sp.name) from SellingPoint sp where sp.deletedDate is null and sp.id = ?1")
	Optional<SellingPointDTO> findActiveSellingPointDTO(Long id);
	
	@Query("select sp from SellingPoint sp where sp.deletedDate is null and sp.id = ?1")
	Optional<SellingPoint> findActiveSellingPoint(Long id);
	
	@Query("select case when count(sp) > 0 then true else false end from SellingPoint sp where sp.deletedDate is null and sp.id = ?1")
	boolean existsAndIsActiveById(Long id);

}
