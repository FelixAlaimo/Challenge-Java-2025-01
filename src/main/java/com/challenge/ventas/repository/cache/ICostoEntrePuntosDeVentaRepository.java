package com.challenge.ventas.repository.cache;

import org.springframework.data.jpa.repository.JpaRepository;

import com.challenge.ventas.model.CostoEntrePuntosDeVenta;
import com.challenge.ventas.model.CostoEntrePuntosDeVentaPk;

public interface ICostoEntrePuntosDeVentaRepository extends JpaRepository<CostoEntrePuntosDeVenta, CostoEntrePuntosDeVentaPk> {

}
