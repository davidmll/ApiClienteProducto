package com.projecto.java.service;

import java.util.List;

import com.projecto.java.model.Cliente;

public interface ClienteService {
	public List<Cliente> findAll();
	
	public Cliente findById(Long id);

	public Cliente save(Cliente cliente);
		
	public void delete(Long id);

}