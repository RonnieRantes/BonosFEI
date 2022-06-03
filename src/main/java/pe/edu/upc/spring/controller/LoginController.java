package pe.edu.upc.spring.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pe.edu.upc.spring.service.IUsuarioService;

@Controller
@RequestMapping
public class LoginController {
	@Autowired
	IUsuarioService uService;
	
	@GetMapping("/login/")
	public String login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, Model model, Principal logeado,
			RedirectAttributes objRedir, HttpServletRequest request, HttpServletResponse response, HttpSession sesion) {
		// login/?logout
		if (logout != null) {
	        Authentication auth = SecurityContextHolder.getContext().getAuthentication();  
	        if (auth != null) {
	        	new SecurityContextLogoutHandler().logout(request, response, auth);
	        }
			objRedir.addFlashAttribute("mensaje", "¡Ha cerrado sesión con éxito!");
			return "redirect:/login/";
		}
		else {
			if (logeado != null) {
				return "redirect:/inicio/";
			}
			// login/?error
			if (error != null) {
				objRedir.addFlashAttribute("mensaje", "¡Los datos no son válidos!");
				return "redirect:/login/";
			}
		}
		return "login";
	}
	
}
