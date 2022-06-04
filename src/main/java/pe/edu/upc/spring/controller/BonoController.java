package pe.edu.upc.spring.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.apache.el.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pe.edu.upc.spring.model.Operacion;
import pe.edu.upc.spring.service.IBonoService;
import pe.edu.upc.spring.service.IFrecuenciaService;
import pe.edu.upc.spring.service.IMonedaService;
import pe.edu.upc.spring.service.IOperacionService;
import pe.edu.upc.spring.service.ITipoTasaService;
import pe.edu.upc.spring.service.IUsuarioService;

@Controller
@RequestMapping("/bonos")
public class BonoController {
	@Autowired
	IBonoService bService;
	@Autowired
	IUsuarioService uService;
	@Autowired
	IFrecuenciaService fService;
	@Autowired
	IOperacionService oService;
	@Autowired
	ITipoTasaService tService;
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
			Operacion cmpbOperacion, objOperacion, ultOperacion;
			ultOperacion = oService.ultimaOperacion(logeado.getName());
			//PRIMER CÁLCULO
			if(objO.getListaBonos()==null) {
				objO.setCorreoUsuario(uService.buscarId(logeado.getName()));
				cmpbOperacion = oService.registrar(objO);
				objOperacion = oService.CalcularIndicadores(cmpbOperacion.getIdOperacion(), uService.buscarId(logeado.getName()).getTipoTasa().getIdTipoTasa(), null);
				objOperacion.setListaBonos(oService.CalcularBonos(cmpbOperacion.getIdOperacion(), uService.buscarId(logeado.getName()).getTipoTasa().getIdTipoTasa(), null));
			}
			//SIGUIENTES CÁLCULOS
			else {
				//RECALCULAR CON TABLA
				if(oService.compararOperaciones(ultOperacion, objO)) {
					objO.setCorreoUsuario(uService.buscarId(logeado.getName()));
					cmpbOperacion = oService.registrar(objO);
					objOperacion = oService.CalcularIndicadores(cmpbOperacion.getIdOperacion(), uService.buscarId(logeado.getName()).getTipoTasa().getIdTipoTasa(), objO.getListaBonos());
					objOperacion.setListaBonos(oService.CalcularBonos(cmpbOperacion.getIdOperacion(), uService.buscarId(logeado.getName()).getTipoTasa().getIdTipoTasa(), objO.getListaBonos()));
				}
				//CAMBIO EN DATOS ENTRADA
				else{
					objO.setIdOperacion(0);
					objO.setCorreoUsuario(uService.buscarId(logeado.getName()));
					cmpbOperacion = oService.registrar(objO);
					objOperacion = oService.CalcularIndicadores(cmpbOperacion.getIdOperacion(), uService.buscarId(logeado.getName()).getTipoTasa().getIdTipoTasa(), null);
					objOperacion.setListaBonos(oService.CalcularBonos(cmpbOperacion.getIdOperacion(), uService.buscarId(logeado.getName()).getTipoTasa().getIdTipoTasa(), null));
				}
			}
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
			if(id>0){
				oService.limpiarBonos(id);
				oService.eliminar(id);
			}
		}
		catch(Exception ex) {
			objRedir.addFlashAttribute("mensaje","Ocurrio un error");
		}
		return "redirect:/historial/"; 
	}

	
	
}
