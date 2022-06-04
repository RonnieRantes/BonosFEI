package pe.edu.upc.spring.controller;

import java.security.Principal;

import org.apache.commons.lang3.text.*;
import org.apache.el.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pe.edu.upc.spring.model.Usuario;
import pe.edu.upc.spring.service.IMonedaService;
import pe.edu.upc.spring.service.IRolService;
import pe.edu.upc.spring.service.ITipoTasaService;
import pe.edu.upc.spring.service.IUsuarioService;

@Controller
@RequestMapping("/registro")
public class RegistroController {

	@Autowired
	private IMonedaService mService;
	@Autowired
	private IRolService rService;
	@Autowired
	private ITipoTasaService tService;
	@Autowired
	private IUsuarioService uService;
	@Autowired 
	private PasswordEncoder encoder;
		
	@RequestMapping("/")
	public String irPaginaRegistro(Model model) throws ParseException{
		model.addAttribute("usuario", new Usuario());
		model.addAttribute("listaMonedas", mService.listar());
		model.addAttribute("listaTipoTasas", tService.listar());
		model.addAttribute("titulo", "Crea una cuenta");
		model.addAttribute("btn", "Registrarse");
		return "registro";
	}
	
	@SuppressWarnings("deprecation")
	@RequestMapping("/registrar")
	public String registrar(@ModelAttribute Usuario objUsuario, Principal logeado, BindingResult binRes, Model model, RedirectAttributes objRedir) throws ParseException{
		//Registrar
		if(logeado == null) {
			if(binRes.hasErrors()) objRedir.addFlashAttribute("mensaje", "Ocurrio un error");
			else {
				if(uService.buscarId(objUsuario.getCorreoUsuario()) == null) {
					objUsuario.setNombres(WordUtils.capitalizeFully(objUsuario.getNombres())); objUsuario.setApellidos(WordUtils.capitalizeFully(objUsuario.getApellidos()));
					objUsuario.setContrasenia(encoder.encode(objUsuario.getContrasenia())); objUsuario.setRol(rService.buscarId(1));
					if (uService.registrar(objUsuario)) return "redirect:/inicio/";
					else objRedir.addFlashAttribute("mensaje", "Ocurrio un error");
				}
				else objRedir.addFlashAttribute("mensaje", "Existe una cuenta creada con ese correo");
			}
			return "redirect:/registro/";
		}
		//Actualizar
		else {
			if(binRes.hasErrors()) objRedir.addFlashAttribute("mensaje", "Ocurrio un error");
			else {
				objUsuario.setRol(rService.buscarId(1));
				if (uService.registrar(objUsuario))return "redirect:/inicio/";
				else objRedir.addFlashAttribute("mensaje", "Ocurrio un error");
			}
			return "redirect:/ajustes/";
		}
	}
}
