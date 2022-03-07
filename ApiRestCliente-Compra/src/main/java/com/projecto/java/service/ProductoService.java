package com.projecto.java.service;

import java.util.List;

import com.projecto.java.model.Producto;



public interface ProductoService {

	public List<Producto> findAllProductos();

	public Producto findById(Long id);

	public Producto saveProducto(Producto producto);
	
	public void deleteProducto(Long id);
}
