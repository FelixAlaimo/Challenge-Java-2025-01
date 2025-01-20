package com.challenge.ventas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.ventas.model.PuntoDeVenta;
import com.challenge.ventas.repository.cache.IPuntoDeVentaRepository;

import jakarta.annotation.PostConstruct;

@Service
public class VentasService {

	@Autowired
	private IPuntoDeVentaRepository puntoDeVentaRepository;
	
	@PostConstruct
	public void init() {
		puntoDeVentaRepository.save(new PuntoDeVenta(1L, "CABA"));
		puntoDeVentaRepository.save(new PuntoDeVenta(2L, "GBA_1"));
		puntoDeVentaRepository.save(new PuntoDeVenta(3L, "GBA_2"));
		puntoDeVentaRepository.save(new PuntoDeVenta(4L, "Santa Fe"));
		puntoDeVentaRepository.save(new PuntoDeVenta(5L, "Córdoba"));
		puntoDeVentaRepository.save(new PuntoDeVenta(6L, "Misiones"));
		puntoDeVentaRepository.save(new PuntoDeVenta(7L, "Salta"));
		puntoDeVentaRepository.save(new PuntoDeVenta(8L, "Chubit"));
		puntoDeVentaRepository.save(new PuntoDeVenta(9L, "Stanta Cruz"));
		puntoDeVentaRepository.save(new PuntoDeVenta(10L, "Catamarca"));
		
	}
	
	public PuntoDeVenta getPuntoDeVentaById(Long id) {
	    return puntoDeVentaRepository.findById(id).orElse(null);
	}
	
	public PuntoDeVenta saveOrUpdatePuntoDeVenta(PuntoDeVenta puntoDeVenta) {
	    return puntoDeVentaRepository.save(puntoDeVenta);
	}

}
