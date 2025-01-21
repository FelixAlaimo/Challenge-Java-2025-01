package com.challenge.ventas.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.ventas.model.CostoEntrePuntosDeVenta;
import com.challenge.ventas.model.CostoEntrePuntosDeVentaDTO;
import com.challenge.ventas.model.PuntoDeVenta;
import com.challenge.ventas.service.VentasService;

@RestController
@RequestMapping("/costos")
public class CostosController {
	
	@Autowired
	private VentasService ventasService;
	
	@GetMapping("/camino/consulta")
    public List<CostoEntrePuntosDeVentaDTO> getPuntosDeVenta() {
        return ventasService.findCostosEntrePuntosDeVentaDto(); 
    }
	
	@GetMapping("/camino/consulta/{id1}/{id2}")
	public String getCostoEntrePuntos(@PathVariable Long id1, @PathVariable Long id2) {
		if (id1 == null || id2 == null) {
			return "Para buscar el costo entre dos puntos se requiere el ID de ambos puntos de venta.";
		}
		
		if (id1 == id2) {
			return "el costo entre un punto y si mismo se presume irrisorio (0)";
		}
		
		CostoEntrePuntosDeVentaDTO costo = ventasService.findCostoEntrePuntosDeVentaDto(Arrays.asList(id1, id2));
		
		if (costo == null) {
			return "No se encontró un costo directo entre ambos puntos de venta. Puede ser que no exista o que alguno de los puntos de venta se encuentre borrado.";
		}
		
		return "El costo entre ambos puntos de venta es: " + costo.getCosto();
	}
	
	@GetMapping("/camino/consulta/{id}")
	public List<CostoEntrePuntosDeVentaDTO> getCostosHaciaPuntoDeVenta(@PathVariable Long id) {
		if (id == null) {
			return null;
		}
		
		return ventasService.findCostosHaciaUnPuntoDeVentaDto(id);
	}
	
	@DeleteMapping("/camino/borrar/{id1}/{id2}")
	public String deleteCostoEntrePuntos(@PathVariable Long id1, @PathVariable Long id2) {
		if (id1 == null || id2 == null) {
			return "Para BORRAR el costo entre dos puntos se requiere el ID de ambos puntos de venta.";
		}
		
		if (id1 == id2) {
			return "No se puede borrar el costo entre un punto y si mismo, ya que se presume irrisorio (0)";
		}

		ventasService.deleteCostoEntrePuntosDeVenta(id1, id2);
		return "Solicitud exitosa de borrado de costo entre dos puntos de venta. En caso de existir, sera eliminado";
	}
	
	@PutMapping("/camino/crear")
	public String createCostoEntrePuntos(@RequestBody CostoEntrePuntosDeVentaDTO costoDto) {
		if (costoDto == null || costoDto.getPuntoAId() == null || costoDto.getPuntoBId() == null) {
			return "Costo entre Puntos de venta no creado. El atributo 'puntoAId', 'puntoBId' y 'costo' son obligatorio";
		}
		if (costoDto.getPuntoAId().equals(costoDto.getPuntoBId())) {
			return "No se puede cambiar el costo entre un punto de venta y si mismo. Se considerará siempre irrisorio (0)";
		}
		if (costoDto.getCosto() < 0) {
			return "El costo no puede tener un valor negativo";
		}
		
		CostoEntrePuntosDeVenta costo = new CostoEntrePuntosDeVenta(new PuntoDeVenta(costoDto.getPuntoAId()), new PuntoDeVenta(costoDto.getPuntoBId()), costoDto.getCosto());
		ventasService.saveOrUpdateCostoEntrePuntosDeVenta(costo);
		return "Solicitud exitosa de creacion de costo entre dos puntos de venta. En caso de existir, sera actualizado";
	}

}
