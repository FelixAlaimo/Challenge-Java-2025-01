package com.challenge.ventas.cache.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.challenge.ventas.cache.model.PuntoDeVenta;
import com.challenge.ventas.cache.model.PuntoDeVentaDTO;

public interface IPuntoDeVentaRepository extends JpaRepository<PuntoDeVenta, Long> {
	
	@Query("select NEW com.challenge.ventas.cache.model.PuntoDeVentaDTO(p.id, p.name) from PuntoDeVenta p where p.deletedDate is null")
	List<PuntoDeVentaDTO> findActivePuntosDeVenta();
	
	@Query("select NEW com.challenge.ventas.cache.model.PuntoDeVentaDTO(p.id, p.name) from PuntoDeVenta p where p.deletedDate is null and p.id = ?1")
	PuntoDeVentaDTO findPuntoDeVentaDto(Long id);
	
	@Query("select p from PuntoDeVenta p where p.deletedDate is null and p.id = ?1")
	PuntoDeVenta findPuntoDeVenta(Long id);

}
