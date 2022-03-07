package com.projecto.java.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClienteController {

	@GetMapping( "/cliente")
	public String index() {
		return "cliente";

	}

}
