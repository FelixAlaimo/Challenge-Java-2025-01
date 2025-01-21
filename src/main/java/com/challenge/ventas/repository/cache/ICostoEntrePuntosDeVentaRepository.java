package com.challenge.ventas.repository.cache;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.challenge.ventas.model.CostoEntrePuntosDeVenta;
import com.challenge.ventas.model.CostoEntrePuntosDeVentaDTO;
import com.challenge.ventas.model.CostoEntrePuntosDeVentaPk;

public interface ICostoEntrePuntosDeVentaRepository extends JpaRepository<CostoEntrePuntosDeVenta, CostoEntrePuntosDeVentaPk> {
	
	@Query("select NEW com.challenge.ventas.model.CostoEntrePuntosDeVentaDTO(puntoA.id, puntoA.name, puntoB.id, puntoB.name, c.amount) "
			+ "from CostoEntrePuntosDeVenta c join c.id.puntoDeVentaA puntoA join c.id.puntoDeVentaB puntoB "
			+ "where puntoA.deletedDate is null and puntoB.deletedDate is null")
	List<CostoEntrePuntosDeVentaDTO> findCostosEntrePuntosDeVentaDto();
	
	@Query("select NEW com.challenge.ventas.model.CostoEntrePuntosDeVentaDTO(puntoA.id, puntoA.name, puntoB.id, puntoB.name, c.amount) "
			+ "from CostoEntrePuntosDeVenta c join c.id.puntoDeVentaA puntoA join c.id.puntoDeVentaB puntoB "
			+ "where (puntoA.id = :id or puntoB.id = :id) "
			+ "and puntoA.deletedDate is null and puntoB.deletedDate is null")
	List<CostoEntrePuntosDeVentaDTO> findCostosHaciaUnPuntoDeVentaDto(Long id);
	
	@Query("select NEW com.challenge.ventas.model.CostoEntrePuntosDeVentaDTO(puntoA.id, puntoA.name, puntoB.id, puntoB.name, c.amount) "
			+ "from CostoEntrePuntosDeVenta c join c.id.puntoDeVentaA puntoA join c.id.puntoDeVentaB puntoB "
			+ "where puntoA.id in :ids and puntoB.id in :ids "
			+ "and puntoA.deletedDate is null and puntoB.deletedDate is null")
	CostoEntrePuntosDeVentaDTO findCostoEntrePuntosDeVentaDto(List<Long> ids);


}
