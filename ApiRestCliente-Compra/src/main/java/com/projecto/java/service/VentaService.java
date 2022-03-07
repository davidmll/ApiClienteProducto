package com.projecto.java.service;

import java.util.List;

import com.projecto.java.model.Venta;



public interface VentaService {

	public List<Venta> findAllVentas();

	public Venta findByFolio(Long folio);

	public Venta saveVenta(Venta venta);
	
	public void deleteVenta(Long folio);
}
