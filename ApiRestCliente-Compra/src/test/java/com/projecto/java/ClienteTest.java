package com.projecto.java;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.projecto.java.Dao.ClienteRepository;
import com.projecto.java.model.Cliente;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class ClienteTest {

	@Autowired
	private ClienteRepository repository;
	
	
	@Test
	@Rollback(false)
	public void testGuardarCliente() {

		Cliente cliente = new Cliente("Berto", "gonzalez", 'h',2222222);

		Cliente clienteGuardado = repository.save(cliente);

		assertThat(clienteGuardado.getNombre()).isEqualTo("Berto");
	}
	
	
	@Test
	public void testBuscarProductoPorNombre() {
		String nombre = "Berto";

		Cliente cliente =repository.findByNombre(nombre);

		assertThat(cliente.getNombre()).isEqualTo(nombre);
	}
	
	
	@Test
	public void testBuscarClientePorNombreNoExiste() {

		String nombre = "calabasa";

		Cliente cliente = repository.findByNombre(nombre);

		assertNull(cliente);
	}
	
	@Test
	@Rollback(false)
	public void testActualizarCliente() {
		String nombre="Javi";
		Cliente cliente= new Cliente(nombre,"rubiales",'m',2213);
		cliente.setId((long)1);
		repository.save(cliente);
		
		Cliente clienteActualizado=repository.findByNombre(nombre);
		
		assertThat(clienteActualizado.getNombre()).isEqualTo(nombre);
	}
	
	@Test
	@Rollback(false)
	public void testBorrarCliente() {
		Long id=(long)1;
		boolean existe=repository.findById(id).isPresent();
		
		repository.deleteById(id);
		
		boolean existe2=repository.findById(id).isPresent();
		
		assertTrue(existe);
		assertFalse(existe2);
	}
	
}
