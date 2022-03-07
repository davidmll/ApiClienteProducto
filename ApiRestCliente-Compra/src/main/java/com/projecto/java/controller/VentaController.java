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

import com.projecto.java.model.Venta;

import com.projecto.java.service.VentaService;

@Controller
@RequestMapping("/api")
public class VentaController {

	@Autowired
	private VentaService service;

//	Peticion get

	@GetMapping("/ventas")
	public String producto(Model model) {
		model.addAttribute("info", service.findAllVentas());

		return "venta";
	}

	@GetMapping("venta/{folio}")
	public ResponseEntity<?> findVentaByFolio(@PathVariable Long folio) {

		Venta venta = null;

		Map<String, Object> response = new HashMap<>();

		try {

			venta = service.findByFolio(folio);

		} catch (DataAccessException e) {

			response.put("mensaje", "Error al realizar la consulta");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (venta == null) {
			response.put("mensaje", "La venta ID: " + folio + " no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Venta>(venta, HttpStatus.OK);

	}

	@GetMapping("/venta/nuevo_venta")
	public String crearVenta(Model model) {
		Venta v = new Venta();
		model.addAttribute("keyventa", v);

		return "nuevo_venta";
	}

	@GetMapping("/venta/editar/{folio}")
	public String findByFolio(@PathVariable Long folio, Model modelo) {
		modelo.addAttribute("keyventa", service.findByFolio(folio));

		return "editar_venta";
	}

//	Method post

	@PostMapping("/ventas")
	public String guardarDepartamento(@ModelAttribute("keyventa") Venta venta) {
		service.saveVenta(venta);
		return "redirect:/api/ventas";
	}

	@PostMapping("/venta/{folio}")
	public String updateVenta(@PathVariable Long folio, @ModelAttribute("keyventa") Venta venta) {

		Venta ventaActual = service.findByFolio(folio);

		ventaActual.setFolio(folio);
		ventaActual.setCantidad(venta.getCantidad());
		ventaActual.setSubTotal(venta.getSubTotal());
		ventaActual.setIva(venta.getIva());
		ventaActual.setTotal(venta.getTotal());

		service.saveVenta(ventaActual);

		return "redirect:/api/ventas";
	}

	@GetMapping("/venta/eliminar/{folio}")
	public String eliminarVenta(@PathVariable Long folio) {
		service.deleteVenta(folio);
		return "redirect:/api/ventas";
	}

}
