package com.projecto.java.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.projecto.java.model.Cliente;
import com.projecto.java.service.ClienteService;

@Controller
@RequestMapping("/api")
public class ClienteController {

	@Autowired
	private ClienteService service;
	
//	Method Gets
	
	@GetMapping("/clientes")
	public String producto(Model model) {
		model.addAttribute("info", service.findAll());

		return "cliente";
	}
	@GetMapping("cliente/{id}")
	public ResponseEntity<?> findProductoById(@PathVariable Long id) {

		Cliente cliente = null;

		Map<String, Object> response = new HashMap<>();

		try {

			cliente = service.findById(id);

		} catch (DataAccessException e) {

			response.put("mensaje", "Error al realizar la consulta");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (cliente == null) {
			response.put("mensaje", "El cliente ID: " + id + " no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);

	}
	
	@GetMapping("/cliente/nuevo_cliente")
	public String crearCliente(Model modelo) {
		Cliente p = new Cliente();
		modelo.addAttribute("keycliente", p);
		return "nuevo_cliente";
	}
	

	@GetMapping("/cliente/editar/{id}")
	public String findByCliente(@PathVariable Long id, Model modelo) {
		modelo.addAttribute("keycliente", service.findById(id));

		return "editar_cliente";
	}
	
//	Method Post
	
	@PostMapping("/clientes")
	public String guardarDepartamento(@ModelAttribute("keycliente") Cliente cliente) {
		service.save(cliente);
		return "redirect:/api/clientes";
	}
	
	@PostMapping("/cliente/{id}")
	public String updateCliente(@PathVariable Long id, @ModelAttribute("keycliente") Cliente cliente) {

		Cliente clienteActual = service.findById(id);
		
		clienteActual.setId(id);
		clienteActual.setNombre(cliente.getNombre());
		clienteActual.setApellidos(cliente.getApellidos());
		clienteActual.setSexo(cliente.getSexo());
		clienteActual.setTelefono(cliente.getTelefono());

		service.save(clienteActual);

		return "redirect:/api/clientes";
	}

	@GetMapping("/cliente/eliminar/{id}")
	public String eliminarCliente(@PathVariable Long id) {
		service.delete(id);
		return "redirect:/api/clientes";
	}
}


