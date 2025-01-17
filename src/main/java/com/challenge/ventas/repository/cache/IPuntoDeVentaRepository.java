package com.challenge.ventas.repository.cache;

import org.springframework.data.jpa.repository.JpaRepository;

import com.challenge.ventas.model.PuntoDeVenta;

public interface IPuntoDeVentaRepository extends JpaRepository<PuntoDeVenta, Long> {

}
