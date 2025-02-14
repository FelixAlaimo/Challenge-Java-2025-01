package com.challenge.ventas.service;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.challenge.ventas.cache.model.CostoEntrePuntosDeVenta;
import com.challenge.ventas.cache.model.CostoEntrePuntosDeVentaPk;
import com.challenge.ventas.cache.model.PuntoDeVenta;
import com.challenge.ventas.cache.repository.ICostoEntrePuntosDeVentaRepository;
import com.challenge.ventas.cache.repository.IPuntoDeVentaRepository;
import com.challenge.ventas.persistence.model.Acreditacion;
import com.challenge.ventas.persistence.repository.IAcreditacionesRepository;

@TestInstance(Lifecycle.PER_CLASS)
class VentasServiceTest {
	
	@Mock
	private IPuntoDeVentaRepository puntoDeVentaRepository;
	@Mock
	private ICostoEntrePuntosDeVentaRepository costoEntrePuntosDeVentaRepository;
	@Mock
	private IAcreditacionesRepository acreditacionesRepository;
	
	@InjectMocks
	private VentasService service;
	
	@BeforeAll
	public void init() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void testInit() {
		service.init();
		Mockito.verify(puntoDeVentaRepository, Mockito.times(10)).saveAndFlush(Mockito.any());
		Mockito.verify(costoEntrePuntosDeVentaRepository, Mockito.times(14)).save(Mockito.any());
	}
	
	@Test
	public void testFindActivePuntosDeVenta() {
		service.findActivePuntosDeVenta();
		Mockito.verify(puntoDeVentaRepository, Mockito.times(1)).findActivePuntosDeVenta();
	}
	
	@Test
	public void testFindPuntoDeVentaDto() {
		service.findPuntoDeVentaDto(22L);
		Mockito.verify(puntoDeVentaRepository, Mockito.times(1)).findPuntoDeVentaDto(22L);
	}
	
	@Test
	public void testFindPuntoDeVenta() {
		service.findPuntoDeVenta(24L);
		Mockito.verify(puntoDeVentaRepository, Mockito.times(1)).findPuntoDeVenta(24L);
	}
	
	@Test
	public void testSaveOrUpdatePuntoDeVenta() {
		PuntoDeVenta p = new PuntoDeVenta(192L);
		service.saveOrUpdatePuntoDeVenta(p);
		Mockito.verify(puntoDeVentaRepository, Mockito.times(1)).save(p);
	}
	
	@Test
	public void testFindCostosEntrePuntosDeVentaDto() {
		service.findCostosEntrePuntosDeVentaDto();
		Mockito.verify(costoEntrePuntosDeVentaRepository, Mockito.times(1)).findCostosEntrePuntosDeVentaDto();
	}
	
	@Test
	public void testFindCostosHaciaUnPuntoDeVentaDto() {
		service.findCostosHaciaUnPuntoDeVentaDto(26L);
		Mockito.verify(costoEntrePuntosDeVentaRepository, Mockito.times(1)).findCostosHaciaUnPuntoDeVentaDto(26L);
	}
	
	@Test
	public void testFindCostoEntrePuntosDeVentaDto() {
		List<Long> list = Arrays.asList(39L, 72L, 105L);
		service.findCostoEntrePuntosDeVentaDto(list);
		Mockito.verify(costoEntrePuntosDeVentaRepository, Mockito.times(1)).findCostoEntrePuntosDeVentaDto(list);
	}
	
	@Test
	public void testDeleteCostoEntrePuntosDeVenta() {
		List<PuntoDeVenta> puntos = Arrays.asList(new PuntoDeVenta(1111L), new PuntoDeVenta(2222L));
		
		service.deleteCostoEntrePuntosDeVenta(puntos.get(0).getId(), puntos.get(1).getId());
		ArgumentCaptor<CostoEntrePuntosDeVentaPk> captor = ArgumentCaptor.forClass(CostoEntrePuntosDeVentaPk.class);
		Mockito.verify(costoEntrePuntosDeVentaRepository, Mockito.times(1)).deleteById(captor.capture());
		CostoEntrePuntosDeVentaPk captPk = captor.getValue();
		Assertions.assertTrue(puntos.contains(captPk.getPuntoDeVentaA()));
		Assertions.assertTrue(puntos.contains(captPk.getPuntoDeVentaB()));
	}
	
	@Test
	public void testSaveOrUpdateCostoEntrePuntosDeVenta() {
		CostoEntrePuntosDeVenta c = new CostoEntrePuntosDeVenta(new PuntoDeVenta(1111L), new PuntoDeVenta(2222L), 7000);
		service.saveOrUpdateCostoEntrePuntosDeVenta(c);
		Mockito.verify(costoEntrePuntosDeVentaRepository, Mockito.times(1)).save(c);
	}
	
	@Test
	public void testFindAcreditaciones() {
		service.findAcreditaciones();
		Mockito.verify(acreditacionesRepository, Mockito.times(1)).findAll();
	}
	
	@Test
	public void testSaveOrUpdateAcreditacion() {
		Acreditacion acreditacion = new Acreditacion(1L, "pName", 70000L);
		service.saveOrUpdateAcreditacion(acreditacion);
		Mockito.verify(acreditacionesRepository, Mockito.times(1)).save(acreditacion);
	}

}
