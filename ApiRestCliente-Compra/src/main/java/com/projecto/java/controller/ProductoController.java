package com.projecto.java.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.projecto.java.model.Producto;
import com.projecto.java.service.ProductoService;

@Controller
@RequestMapping("/api")
public class ProductoController {
	
	@Autowired
	private ProductoService servicio;

//	Methods Get

	@GetMapping("/productos")
	public List<Producto> index() {
		return servicio.findAllProductos();
	}

	@GetMapping("producto/{id}")
	public ResponseEntity<?> findProductoById(@PathVariable Long id) {

		Producto producto = null;

		Map<String, Object> response = new HashMap<>();

		try {

			producto = servicio.findById(id);

		} catch (DataAccessException e) {

			response.put("mensaje", "Error al realizar la consulta");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (producto == null) {
			response.put("mensaje", "El producto ID: " + id + " no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Producto>(producto, HttpStatus.OK);

	}

//	Method Post

	@PostMapping("/producto")
	public ResponseEntity<?> saveProducto(@RequestBody Producto producto) {
		Producto productoNew = null;

		Map<String, Object> response = new HashMap<>();

		try {

			productoNew = servicio.saveProducto(producto);

		} catch (DataAccessException e) {

			response.put("mensaje", "Error al realizar un insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", productoNew);
		response.put("producto", "El producto ha sido creado con éxito");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}

//	Method Put

	@PutMapping("/producto/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> updateProducto(@RequestBody Producto producto, @PathVariable Long id) {

		Producto productoUpdate = servicio.findById(id);

		Map<String, Object> response = new HashMap<>();

		if (productoUpdate == null) {
			response.put("mensaje", "Error: no se puedo editar el producto con ID: " + id);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			productoUpdate.setId(id);
			productoUpdate.setNombre(producto.getNombre());
			productoUpdate.setDescripcion(producto.getDescripcion());
			productoUpdate.setPrecioUnitario(producto.getPrecioUnitario());
			productoUpdate.setExistencias(producto.getExistencias());
			

			servicio.saveProducto(productoUpdate);
		} catch (DataAccessException e) {

			response.put("mensaje", "Error al realizar un update en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("producto", productoUpdate);
		response.put("mensaje", "El producto ha sido actualizado con éxito");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

//	Method Delete

	@DeleteMapping("/producto/{id}")
	public ResponseEntity<?> deleteProducto(@PathVariable Long id) {

		Producto productoDelete = servicio.findById(id);
		Map<String, Object> response = new HashMap<>();

		if (productoDelete == null) {
			response.put("mensaje", "Error: no se pudo eliminar el producto con ID: " + id);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		try {

		
			servicio.deleteProducto(id);
		} catch (DataAccessException e) {

			response.put("mensaje", "Error al eliminar la información en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("producto", productoDelete);
		response.put("mensaje", "El producto ha sido eliminado con éxito");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	
	
	


}
