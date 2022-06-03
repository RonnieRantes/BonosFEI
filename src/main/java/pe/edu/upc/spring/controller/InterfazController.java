package pe.edu.upc.spring.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pe.edu.upc.spring.service.IMonedaService;
import pe.edu.upc.spring.service.IOperacionService;
import pe.edu.upc.spring.service.ITipoTasaService;
import pe.edu.upc.spring.service.IUsuarioService;

@Controller
@RequestMapping
public class InterfazController {
	@Autowired
	IMonedaService mService;
	@Autowired
	IUsuarioService uService;
	@Autowired
	ITipoTasaService tService;
	@Autowired
	IOperacionService oService;

	@GetMapping("/")
	public String principal() { return "redirect:/inicio/"; }
	
	@GetMapping("/inicio/")
	public String inicio(Principal logeado, Model model) {
		model.addAttribute("user", uService.buscarId(logeado.getName()));
		return "inicio"; 
	}

	@GetMapping("/ajustes/")
	public String ajustes(Principal logeado, Model model) {
		model.addAttribute("user", uService.buscarId(logeado.getName()));
		model.addAttribute("usuario", uService.buscarId(logeado.getName()));
		model.addAttribute("listaMonedas", mService.listar());
		model.addAttribute("listaTipoTasas", tService.listar());
		model.addAttribute("titulo", "Ajustes");
		model.addAttribute("btn", "Actualizar");
		return "ajustes"; 
	}
	@GetMapping("/historial/")
	public String historial(Principal logeado, Model model) {
		model.addAttribute("user", uService.buscarId(logeado.getName()));
		model.addAttribute("listaOperaciones", oService.operacionesUsuario(logeado.getName()));
		model.addAttribute("moneda", mService.buscarId(uService.buscarId(logeado.getName()).getMoneda().getIdMoneda()).getSimbolo());
		return "historial"; 
	}

}
