package com.challenge.ventas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.ventas.cache.model.PuntoDeVenta;
import com.challenge.ventas.persistence.model.Acreditacion;
import com.challenge.ventas.persistence.model.AcreditacionDTO;
import com.challenge.ventas.service.VentasService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/acreditaciones")
public class AcreditacionesController {
	
	@Autowired
	private VentasService ventasService;
	
	@GetMapping("/consulta")
    public List<Acreditacion> getAcreditaciones() {
        return ventasService.findAcreditaciones(); 
    }

	@PutMapping("/carga")
    public String guardarAcreditacion(@RequestBody AcreditacionDTO acreditacionDTO) {
		if (acreditacionDTO == null || acreditacionDTO.getPuntoDeVentaId() == null || acreditacionDTO.getImporte() == null) {
			return "No se procesó la carga de acreditación. Los campos 'puntoDeVentaId' e 'importe' son requeridos";
		}
		
		PuntoDeVenta puntoDeVenta = ventasService.findPuntoDeVenta(acreditacionDTO.getPuntoDeVentaId());
		if (puntoDeVenta == null) {
			return "El punto de venta enviado no es válido.";
		}
		
		Acreditacion acreditacion = new Acreditacion(acreditacionDTO.getPuntoDeVentaId(), puntoDeVenta.getName(), acreditacionDTO.getImporte());
		ventasService.saveOrUpdateAcreditacion(acreditacion);
        return "Se procesó exitosamente la carga de la acreditación."; 
    }

}
