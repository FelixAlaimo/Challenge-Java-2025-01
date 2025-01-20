package com.challenge.ventas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.ventas.model.PuntoDeVenta;
import com.challenge.ventas.service.VentasService;

@RestController
@RequestMapping("/ventas")
public class VentasController {
	
	@Autowired
	private VentasService ventasService;
	
	@GetMapping("/punto-de-venta/{id}")
    public PuntoDeVenta getUser(@PathVariable Long id) {
        return ventasService.getPuntoDeVentaById(id); 
    }

}
