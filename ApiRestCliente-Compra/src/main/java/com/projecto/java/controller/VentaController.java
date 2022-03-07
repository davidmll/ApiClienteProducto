package com.projecto.java.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projecto.java.model.Venta;
import com.projecto.java.service.VentaService;



@RestController
@RequestMapping("/api")
public class VentaController {

	@Autowired
	private VentaService servicio;

	@GetMapping({ "/ventas" })
	public List<Venta> index() {
		return servicio.findAllVentas();
	}

	@GetMapping("/ventas/{id}")
	public ResponseEntity<?> findVentaById(@PathVariable Long id) {

		Venta venta = null;
		Map<String, Object> response = new HashMap<>();

		try {
			venta = servicio.findById(id);

		} catch (DataAccessException e) {
			
			response.put("mensaje", "Error al realizar consulta a base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (venta == null) {

			response.put("mensaje", "La venta ID: ".concat(id.toString().concat("no existe en la base de datos")));

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Venta>(venta, HttpStatus.OK);
	}	

	@PostMapping("/venta")
	public ResponseEntity<?> saveCliente(@RequestBody Venta venta) {

		Venta ventaNew = null;
		Map<String, Object> response = new HashMap<>();

		try {

			venta = servicio.saveVenta(ventaNew);

		} catch (DataAccessException e) {
			
			response.put("mensaje", "Error al realizar un insert a la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "La venta ha sido creado con éxito");
		response.put("venta", ventaNew);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}

	@PutMapping("/venta/{id}")
	public ResponseEntity<?> updateVenta(@RequestBody Venta venta, @PathVariable Long id) {

		Map<String, Object> response = new HashMap<>();
		Venta ventaActual = servicio.findById(id);

		if (ventaActual == null) {

			response.put("mensaje",
					"Error: no se pudo editar, la venta con ID: " + id.toString() + "no existe en la BBDD");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);

		}

		try {
			ventaActual.setProducto(venta.getProducto());
			ventaActual.setIva(venta.getIva());
			ventaActual.setCantidad(venta.getCantidad());
			ventaActual.setSubTotal(venta.getSubTotal());
			ventaActual.setTotal(venta.getTotal());
			ventaActual.setCliente(venta.getCliente());

			servicio.saveVenta(ventaActual);

		} catch (DataAccessException e) {
			
			response.put("mensaje", "Error al realizar un update a la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La venta ha sido actualizada con éxito");
		response.put("venta", ventaActual);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@DeleteMapping("/venta/{id}")
	public ResponseEntity<?> deleteVenta(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		Venta ventaActual = servicio.findById(id);

		if (ventaActual == null) {

			response.put("mensaje",
					"Error: no se pudo editar, la venta con ID: " + id.toString() + "no existe en la BBDD");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			servicio.deleteVenta(id);

		} catch (DataAccessException e) {
			
			response.put("mensaje", "Error al realizar un delete a la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} 

		response.put("venta", ventaActual);
		response.put("mensaje", "Se ha borrado con exito la venta");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}


