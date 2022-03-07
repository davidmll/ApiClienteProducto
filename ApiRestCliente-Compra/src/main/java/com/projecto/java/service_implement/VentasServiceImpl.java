package com.projecto.java.service_implement;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projecto.java.Dao.VentasRepository;
import com.projecto.java.model.Venta;
import com.projecto.java.service.VentaService;

@Service
public class VentasServiceImpl implements VentaService {

	@Autowired
	private VentasRepository repository;
	
	@Override
	@Transactional(readOnly=true)
	public List<Venta> findAllVentas() {
		return (List<Venta>) repository.findAll();
	}

	@Override
	@Transactional
	public Venta findById(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Venta saveVenta(Venta venta) {
		return repository.save(venta);
	}

	@Override
	@Transactional
	public void deleteVenta(Long id) {
		repository.deleteById(id);
		
	}

}
