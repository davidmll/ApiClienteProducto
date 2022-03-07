package com.projecto.java;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.projecto.java.Dao.VentasRepository;
import com.projecto.java.model.Cliente;
import com.projecto.java.model.Producto;
import com.projecto.java.model.Venta;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class VentasTest {
	
	@Autowired
	private VentasRepository repository;

	@Test
	@Rollback(false)
	public void testGuardarProducto() {
		Cliente clienteNuevo = new Cliente("Juan", "alvarez", 'm',123123);
		Producto productoNuevo = new Producto("hola", "hola", 1,3);
		
		Venta venta = new Venta(productoNuevo,  clienteNuevo, 12, 123, 21, 122);

		Venta ventaGuardado = repository.save(venta);

		assertThat(ventaGuardado.getCantidad()).isEqualTo(12);
	}		
	
	@Test
	@Rollback(false)
	public void testActualizarVenta() {
		int cantidad=12;
		Cliente clienteNuevo = new Cliente("Juan", "alvarez", 'm',123123);
		Producto productoNuevo = new Producto("hola", "hola", 1,3);
		
		Venta venta= new Venta(productoNuevo,clienteNuevo,cantidad,123,1,122);
		venta.setFolio((long)1);
		repository.save(venta);
		
		Venta ventaActualizado=repository.findByCantidad(cantidad);
		
		assertThat(ventaActualizado.getCantidad()).isEqualTo(cantidad);
	}
	
	@Test
	@Rollback(false)
	public void testBorrarVenta() {
		Long id=(long)1;
		boolean existe=repository.findById(id).isPresent();
		
		repository.deleteById(id);
		
		boolean existe2=repository.findById(id).isPresent();
		
		assertTrue(existe);
		assertFalse(existe2);
	}
}