package com.projecto.java.service;

public interface ClienteService {
	public List<Cliente> findAll();
	
	public Cliente findById(Long id);

	public Cliente save(Cliente cliente);
		
	public void delete(Long id);

}