package com.projecto.java.service;

import java.util.List;

import com.projecto.java.model.Venta;



public interface VentaService {

	public List<Venta> findAllVentas();

	public Venta findById(Long id);

	public Venta saveVenta(Venta venta);
	
	public void deleteVenta(Long id);
}
