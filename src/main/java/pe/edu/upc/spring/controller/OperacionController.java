package pe.edu.upc.spring.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.el.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pe.edu.upc.spring.model.Bono;
import pe.edu.upc.spring.model.Operacion;
import pe.edu.upc.spring.service.IFrecuenciaService;
import pe.edu.upc.spring.service.IMonedaService;
import pe.edu.upc.spring.service.IOperacionService;
import pe.edu.upc.spring.service.IUsuarioService;

@Controller
@RequestMapping("/bonos")
public class OperacionController {
	@Autowired
	IUsuarioService uService;
	@Autowired
	IFrecuenciaService fService;
	@Autowired
	IOperacionService oService;
	@Autowired
	IMonedaService mService;

	@RequestMapping("/")
	public String irPaginaOperacion(Principal logeado, Model model) {
		Operacion objO = new Operacion();
		objO.setFechaEmision(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
		model.addAttribute("operacion", objO);
		model.addAttribute("frecuenciasBono", fService.frecuenciasBono());
		model.addAttribute("frecuenciasCapitalizacion", fService.frecuenciasCapitalizacion());
		model.addAttribute("user", uService.buscarId(logeado.getName()));
		return "calculo";
	}
	@RequestMapping("/resultado")
	public String resultadoOperacion(Operacion objO, Principal logeado, Model model, BindingResult binRes, RedirectAttributes objRedir) throws ParseException {
		if(binRes.hasErrors()) objRedir.addFlashAttribute("mensaje", "Ocurrio un error");
		else {
			if(objO.getFechaEmision() == null) return "redirect:/bonos/";
			Operacion objOperacion;
			objO.setCorreoUsuario(uService.buscarId(logeado.getName()));

			List<Bono> bonos;
			//PRIMER CÁLCULO
			if(objO.getListaBonos()==null) {
				//Registro datos entrada
				objOperacion = oService.registrar(objO);

				objOperacion = oService.CalcularIntermedios(objOperacion, uService.buscarId(logeado.getName()).getTipoTasa());
				bonos = oService.CalcularBonos(objOperacion, new ArrayList<Bono>());
				objOperacion = oService.CalcularIndicadores(objOperacion, bonos);
			}
			//SIGUIENTES CÁLCULOS
			else {
				//RECALCULAR - SIN CAMBIOS EN DATOS DE ENTRADA
				if(oService.compararOperaciones(oService.buscarId(objO.getIdOperacion()), objO)) {
					//Registro datos entrada
					objOperacion = oService.registrar(objO);

					objOperacion = oService.CalcularIntermedios(objOperacion, uService.buscarId(logeado.getName()).getTipoTasa());
					bonos = oService.CalcularBonos(objOperacion, objO.getListaBonos());
					objOperacion = oService.CalcularIndicadores(objOperacion, bonos);
				}
				//CAMBIO EN DATOS ENTRADA
				else{
					//Registro datos entrada
					objO.setIdOperacion(0);
					objOperacion = oService.registrar(objO);
					
					objOperacion = oService.CalcularIntermedios(objOperacion, uService.buscarId(logeado.getName()).getTipoTasa());
					bonos = oService.CalcularBonos(objOperacion, new ArrayList<Bono>());
					objOperacion = oService.CalcularIndicadores(objOperacion, bonos);
				}
			}
			objOperacion.setListaBonos(bonos);
			model.addAttribute("operacion", objOperacion);
			model.addAttribute("listaBonos", objOperacion.getListaBonos());
			//default
			model.addAttribute("moneda", mService.buscarId(uService.buscarId(logeado.getName()).getMoneda().getIdMoneda()).getSimbolo());
			model.addAttribute("frecuenciasBono", fService.frecuenciasBono());
			model.addAttribute("frecuenciasCapitalizacion", fService.frecuenciasCapitalizacion());
			model.addAttribute("user", uService.buscarId(logeado.getName()));
			return "calculo";
		}
		return "redirect:/bonos/";
	}
	@RequestMapping("/cargar")
	public String irCargarOperacion(@RequestParam(value="id") int id, Principal logeado, Model model, RedirectAttributes objRedir) {
		Operacion objO = oService.buscarId(id);
		objRedir.addFlashAttribute("operacion", objO);
		return "redirect:/bonos/resultado";
	}

	@RequestMapping("/eliminar")
	public String eliminar(@RequestParam(value="id") int id, RedirectAttributes objRedir) {
		try {
			if(id>0) oService.eliminar(id);
		}
		catch(Exception ex) {
			objRedir.addFlashAttribute("mensaje","Ocurrio un error");
		}
		return "redirect:/historial/"; 
	}

	
	
}
