package com.projecto.java;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.projecto.java.Dao.ProductoRepository;
import com.projecto.java.model.Producto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class ProductoTest {
	
	@Autowired
	private ProductoRepository repository;

	@Test
	@Rollback(false)
	public void testGuardarProducto() {

		Producto producto = new Producto("hola", "hola", 2,2);

		Producto productoGuardado = repository.save(producto);

		assertThat(productoGuardado.getNombre()).isEqualTo("hola");
	}
	
	@Test
	public void testBuscarProductoPorNombre() {
		String nombre = "hola";

		Producto producto = repository.findByNombre(nombre);

		assertThat(producto.getNombre()).isEqualTo(nombre);
	}
	
	@Test
	public void testBuscarProductoPorNombreNoExiste() {

		String nombre = "calabasa";

		Producto producto = repository.findByNombre(nombre);

		assertNull(producto);
	}
	
	
	@Test
	@Rollback(false)
	public void testActualizarProducto() {
		String nombre="Adios";
		Producto producto= new Producto(nombre,"venga",2000,2000);
		producto.setId((long)1);
		repository.save(producto);
		
		Producto productoActualizado=repository.findByNombre(nombre);
		
		assertThat(productoActualizado.getNombre()).isEqualTo(nombre);
	}
	
	@Test
	@Rollback(false)
	public void testBorrarProducto() {
		Long id=(long)1;
		boolean existe=repository.findById(id).isPresent();
		
		repository.deleteById(id);
		
		boolean existe2=repository.findById(id).isPresent();
		
		assertTrue(existe);
		assertFalse(existe2);
	}

}
