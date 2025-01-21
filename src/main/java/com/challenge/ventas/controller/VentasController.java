package com.challenge.ventas.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.ventas.model.PuntoDeVenta;
import com.challenge.ventas.model.PuntoDeVentaDTO;
import com.challenge.ventas.service.VentasService;

import io.micrometer.common.util.StringUtils;

@RestController
@RequestMapping("/ventas")
public class VentasController {
	
	@Autowired
	private VentasService ventasService;
	
	@GetMapping("/punto-de-venta/consulta")
    public List<PuntoDeVentaDTO> getPuntosDeVenta() {
        return ventasService.findActivePuntosDeVenta(); 
    }
	
	@GetMapping("/punto-de-venta/consulta/{id}")
	public PuntoDeVentaDTO getPuntoDeVenta(@PathVariable Long id) {
		return ventasService.findPuntoDeVentaDto(id); 
	}
	
	@PutMapping("/punto-de-venta/crear")
	public String createPuntoDeVenta(@RequestBody PuntoDeVentaDTO puntoDeVentaDTO) {
		if (puntoDeVentaDTO == null || StringUtils.isBlank(puntoDeVentaDTO.getName())) {
			return "Punto de venta no creado. El atributo 'name' es obligatorio";
		}
		
		PuntoDeVenta nuevoPuntoDeVenta = new PuntoDeVenta(puntoDeVentaDTO.getName());
		ventasService.saveOrUpdatePuntoDeVenta(nuevoPuntoDeVenta);
		return "Punto de venta creado OK: " + nuevoPuntoDeVenta.toString();
	}

	@PatchMapping("/punto-de-venta/actualizar")
    public String updatePuntoDeVenta(@RequestBody PuntoDeVentaDTO puntoDeVentaDTO) {
		if (puntoDeVentaDTO == null || puntoDeVentaDTO.getId() == null || StringUtils.isBlank(puntoDeVentaDTO.getName())) {
			return "Para actualizar un punto de venta, son necesarios tanto el atributo 'name' como 'id'";
		}
		
		PuntoDeVenta puntoDeVenta = ventasService.findPuntoDeVenta(puntoDeVentaDTO.getId());
		if (puntoDeVenta == null) {
			return "El punto de venta ingresado no existe o se encuentra borrado";
		}
		
        puntoDeVenta.setName(puntoDeVentaDTO.getName());
        ventasService.saveOrUpdatePuntoDeVenta(puntoDeVenta);
        return "Punto de venta actualizado OK: " + puntoDeVenta.toString();
    }

	@DeleteMapping("/punto-de-venta/borrar/{id}")
    public String deletePuntoDeVenta(@PathVariable Long id) {
		if (id == null) {
			return "Punto de venta no borrado. El atributo 'id' es obligatorio";
		}
		
		PuntoDeVenta puntoDeVenta = ventasService.findPuntoDeVenta(id);
		if (puntoDeVenta == null) {
			return "El punto de venta ingresado no existe o se encuentra borrado";
		}
		
        puntoDeVenta.setDeletedDate(new Date());
        ventasService.saveOrUpdatePuntoDeVenta(puntoDeVenta);
        return "Punto de venta borrado OK: " + puntoDeVenta.toString();
    }

}
