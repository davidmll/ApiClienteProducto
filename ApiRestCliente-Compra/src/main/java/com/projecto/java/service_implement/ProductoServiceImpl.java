package com.projecto.java.service_implement;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projecto.java.Dao.ProductoRepository;
import com.projecto.java.model.Producto;
import com.projecto.java.service.ProductoService;

@Service
public class ProductoServiceImpl implements ProductoService{
	
	@Autowired
	private ProductoRepository repository;

	@Override
	@Transactional(readOnly=true)
	public List<Producto> findAllProductos() {
		
		return (List<Producto>) repository.findAll();
	}

	@Override
	@Transactional
	public Producto findById(Long id) {
		
		return repository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Producto saveProducto(Producto producto) {
		return repository.save(producto);
	}

	@Override
	@Transactional
	public void deleteProducto(Long id) {
		repository.deleteById(id);
		
	}

}
