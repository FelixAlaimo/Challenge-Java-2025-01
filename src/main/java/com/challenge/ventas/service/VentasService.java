package com.challenge.ventas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.ventas.model.CostoEntrePuntosDeVenta;
import com.challenge.ventas.model.CostoEntrePuntosDeVentaDTO;
import com.challenge.ventas.model.CostoEntrePuntosDeVentaPk;
import com.challenge.ventas.model.PuntoDeVenta;
import com.challenge.ventas.model.PuntoDeVentaDTO;
import com.challenge.ventas.repository.cache.ICostoEntrePuntosDeVentaRepository;
import com.challenge.ventas.repository.cache.IPuntoDeVentaRepository;

import jakarta.annotation.PostConstruct;

@Service
public class VentasService {
	
	@Autowired
	private IPuntoDeVentaRepository puntoDeVentaRepository;

	@Autowired
	private ICostoEntrePuntosDeVentaRepository costoEntrePuntosDeVentaRepository;
	
	@PostConstruct
	public void init() {
		PuntoDeVenta puntoDeVenta1 = new PuntoDeVenta("CABA");
		puntoDeVentaRepository.saveAndFlush(puntoDeVenta1);
		PuntoDeVenta puntoDeVenta2 = new PuntoDeVenta("GBA_1");
		puntoDeVentaRepository.saveAndFlush(puntoDeVenta2);
		PuntoDeVenta puntoDeVenta3 = new PuntoDeVenta("GBA_2");
		puntoDeVentaRepository.saveAndFlush(puntoDeVenta3);
		PuntoDeVenta puntoDeVenta4 = new PuntoDeVenta("Santa Fe");
		puntoDeVentaRepository.saveAndFlush(puntoDeVenta4);
		PuntoDeVenta puntoDeVenta5 = new PuntoDeVenta("Córdoba");
		puntoDeVentaRepository.saveAndFlush(puntoDeVenta5);
		PuntoDeVenta puntoDeVenta6 = new PuntoDeVenta("Misiones");
		puntoDeVentaRepository.saveAndFlush(puntoDeVenta6);
		PuntoDeVenta puntoDeVenta7 = new PuntoDeVenta("Salta");
		puntoDeVentaRepository.saveAndFlush(puntoDeVenta7);
		PuntoDeVenta puntoDeVenta8 = new PuntoDeVenta("Chubut");
		puntoDeVentaRepository.saveAndFlush(puntoDeVenta8);
		PuntoDeVenta puntoDeVenta9 = new PuntoDeVenta("Santa Cruz");
		puntoDeVentaRepository.saveAndFlush(puntoDeVenta9);
		PuntoDeVenta puntoDeVenta10 = new PuntoDeVenta("Catamarca");
		puntoDeVentaRepository.saveAndFlush(puntoDeVenta10);
		
		costoEntrePuntosDeVentaRepository.save(new CostoEntrePuntosDeVenta(puntoDeVenta1, puntoDeVenta2, 2));
		costoEntrePuntosDeVentaRepository.save(new CostoEntrePuntosDeVenta(puntoDeVenta1, puntoDeVenta3, 3));
		costoEntrePuntosDeVentaRepository.save(new CostoEntrePuntosDeVenta(puntoDeVenta1, puntoDeVenta4, 11));
		costoEntrePuntosDeVentaRepository.save(new CostoEntrePuntosDeVenta(puntoDeVenta2, puntoDeVenta3, 5));
		costoEntrePuntosDeVentaRepository.save(new CostoEntrePuntosDeVenta(puntoDeVenta2, puntoDeVenta4, 10));
		costoEntrePuntosDeVentaRepository.save(new CostoEntrePuntosDeVenta(puntoDeVenta2, puntoDeVenta5, 14));
		costoEntrePuntosDeVentaRepository.save(new CostoEntrePuntosDeVenta(puntoDeVenta3, puntoDeVenta8, 10));
		costoEntrePuntosDeVentaRepository.save(new CostoEntrePuntosDeVenta(puntoDeVenta4, puntoDeVenta5, 5));
		costoEntrePuntosDeVentaRepository.save(new CostoEntrePuntosDeVenta(puntoDeVenta4, puntoDeVenta6, 6));
		costoEntrePuntosDeVentaRepository.save(new CostoEntrePuntosDeVenta(puntoDeVenta5, puntoDeVenta8, 30));
		costoEntrePuntosDeVentaRepository.save(new CostoEntrePuntosDeVenta(puntoDeVenta6, puntoDeVenta7, 32));
		costoEntrePuntosDeVentaRepository.save(new CostoEntrePuntosDeVenta(puntoDeVenta8, puntoDeVenta9, 11));
		costoEntrePuntosDeVentaRepository.save(new CostoEntrePuntosDeVenta(puntoDeVenta10, puntoDeVenta5, 5));
		costoEntrePuntosDeVentaRepository.save(new CostoEntrePuntosDeVenta(puntoDeVenta10, puntoDeVenta7, 5));
		
	}
	
	public List<PuntoDeVentaDTO> findActivePuntosDeVenta() {
		return puntoDeVentaRepository.findActivePuntosDeVenta();
	}
	
	public PuntoDeVentaDTO findPuntoDeVentaDto(Long id) {
		return puntoDeVentaRepository.findPuntoDeVentaDto(id);
	}
	
	public PuntoDeVenta findPuntoDeVenta(Long id) {
		return puntoDeVentaRepository.findPuntoDeVenta(id);
	}
	
	public PuntoDeVenta saveOrUpdatePuntoDeVenta(PuntoDeVenta puntoDeVenta) {
	    return puntoDeVentaRepository.save(puntoDeVenta);
	}
	
	public List<CostoEntrePuntosDeVentaDTO> findCostosEntrePuntosDeVentaDto() {
		return costoEntrePuntosDeVentaRepository.findCostosEntrePuntosDeVentaDto();
	}
	
	
	public List<CostoEntrePuntosDeVentaDTO> findCostosHaciaUnPuntoDeVentaDto(Long id) {
		return costoEntrePuntosDeVentaRepository.findCostosHaciaUnPuntoDeVentaDto(id);
	}
	
	public CostoEntrePuntosDeVentaDTO findCostoEntrePuntosDeVentaDto(List<Long> ids) {
		return costoEntrePuntosDeVentaRepository.findCostoEntrePuntosDeVentaDto(ids);
	}
	
	public void deleteCostoEntrePuntosDeVenta(Long puntoDeVenta1, Long puntoDeVenta2) {
		CostoEntrePuntosDeVentaPk pk = new CostoEntrePuntosDeVentaPk(new PuntoDeVenta(puntoDeVenta1), new PuntoDeVenta(puntoDeVenta2));
		costoEntrePuntosDeVentaRepository.deleteById(pk);
	}

	public CostoEntrePuntosDeVenta saveOrUpdateCostoEntrePuntosDeVenta(CostoEntrePuntosDeVenta costo) {
	    return costoEntrePuntosDeVentaRepository.save(costo);
	}

}
