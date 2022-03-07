package com.projecto.java.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.projecto.java.model.Producto;
import com.projecto.java.service.ProductoService;

@Controller
@RequestMapping("/api")
public class ProductoController {

	@Autowired
	private ProductoService service;

	
//	Peticion get
	
	@GetMapping("/productos")
	public String hola(Model model) {
		model.addAttribute("info", service.findAllProductos());

		return "producto";
	}

	@GetMapping("producto/{id}")
	public ResponseEntity<?> findProductoById(@PathVariable Long id) {

		Producto producto = null;

		Map<String, Object> response = new HashMap<>();

		try {

			producto = service.findById(id);

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

	@GetMapping("/producto/nuevo_producto")
	public String crearProducto(Model model) {
		Producto p = new Producto();
		model.addAttribute("keyproducto", p);

		return "nuevo_producto";
	}

	@PostMapping("/productos")
	public String guardarDepartamento(@ModelAttribute("keyproducto") Producto producto) {
		service.saveProducto(producto);
		return "redirect:/api/productos";
	}

//	@GetMapping("/producto/editar/{id}")
//	public String editarProyecto(@PathVariable Long id, Model modelo) {
//		modelo.addAttribute("keyproducto", service.saveProducto(producto));
//		return "editarDepartamento";
//	}
//
//	@PostMapping("/departamento/{id}")
//	public String actualizarProyecto(@PathVariable Long id,
//			@ModelAttribute("keyDepartamento") Departamento departamento) {
//		Departamento departamentoExistente = service.obtenerDepartamento(id);
//		departamentoExistente.setId(id);
//		departamentoExistente.setNombre(departamento.getNombre());
//
//		service.guardarDepartamento(departamentoExistente);
//
//		return "redirect:/departamentos";
//
//	}

	@GetMapping("/api/producto/eliminar/{id}")
	public String eliminarProducto(@PathVariable Long id) {
		service.deleteProducto(id);
		return "redirect:/api/productos";
	}

}
