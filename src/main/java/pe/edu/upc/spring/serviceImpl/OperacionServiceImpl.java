package pe.edu.upc.spring.serviceImpl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.poi.ss.formula.functions.*;

import pe.edu.upc.spring.model.Bono;
import pe.edu.upc.spring.model.Frecuencia;
import pe.edu.upc.spring.model.Operacion;
import pe.edu.upc.spring.repository.IBonoRepository;
import pe.edu.upc.spring.repository.IOperacionRepository;
import pe.edu.upc.spring.service.IFrecuenciaService;
import pe.edu.upc.spring.service.IOperacionService;

@Service
public class OperacionServiceImpl implements IOperacionService {

	@Autowired
	private IOperacionRepository dOperacion;
	@Autowired
	private IBonoRepository dBono;
	@Autowired
	private IFrecuenciaService fService;
	
	@Override
	@Transactional
	public Operacion registrar(Operacion objO) {
		objO.setFechaAccion(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
		Operacion objOperacion = dOperacion.save(objO);
		return objOperacion;
	}

	@Override
	@Transactional(readOnly=true)
	public Operacion ultimaOperacion(String correoUsuario) {
		List<Operacion> lst = dOperacion.operacionesUsuario(correoUsuario);
		for(int i = 0; i < lst.size(); i++) {
			if(i == lst.size()-1) return lst.get(i);
		}
		return null;
	}

	@Override
	@Transactional(readOnly=true)
	public boolean compararOperaciones(Operacion objO1, Operacion objO2) {
		if(objO1 == null || objO2 == null)return false;
		if(objO1.getNominal() != objO2.getNominal())return false;
		if(objO1.getComercial() != objO2.getComercial())return false;
		if(objO1.getTasaInteres() != objO2.getTasaInteres())return false;
		if(objO1.getTasaDescuento() != objO2.getTasaDescuento())return false;
		if(objO1.getFechaEmision().getTime() != objO2.getFechaEmision().getTime())return false;
		if(objO1.getAnios() != objO2.getAnios())return false;
		if(objO1.getCapitalizacion() != null && objO1.getCapitalizacion().getIdFrecuencia() != objO2.getCapitalizacion().getIdFrecuencia())return false;
		if(objO1.getFrecuencia().getIdFrecuencia() != objO2.getFrecuencia().getIdFrecuencia())return false;
		if(objO1.getPrima() != objO2.getPrima())return false;
		if(objO1.getFlotacion() != objO2.getFlotacion())return false;
		if(objO1.getCavali() != objO2.getCavali())return false;
		return true;
	}

	@Override
	@Transactional
	public void limpiarBonos(int idOperacion) {
		List<Bono> lst = dBono.bonosOperacion(idOperacion);
		for(Bono b : lst) { dBono.deleteById(b.getIdBono()); }
	}

	@Override
	@Transactional(readOnly=true)
	public Operacion buscarId(int idOperacion) {
		Optional<Operacion> opt = dOperacion.findById(idOperacion);
		if(opt.isPresent()) {
			Operacion objOperacion = opt.get();
			return objOperacion;
		}
		return null;
	}
	
	@Override
	@Transactional
	public List<Bono> CalcularBonos(Operacion objO, int idTipoTasa, List<Bono> lstBonos) {
		List<Bono> lst = new ArrayList<Bono>();
		double aux, sInicial, sFinal = 0, interes, cuota, amort, prima, flujo, FA, FAP, FC; 
		int frecuenciaBono = objO.getFrecuencia().getDias();
		long auxx;
		String pGracia;

		for(int i = 1; i <= objO.getPeriodos(); i++) {
			
			//Fecha Emisión
			auxx = (long)i * (long)frecuenciaBono * (long)(24*60*60*1000);
			Date fechaBono = new Date(objO.getFechaEmision().getTime() + auxx);
	
			//Saldo Inicial
			if(i == 1) sInicial = objO.getNominal();
			else sInicial = sFinal;
			
			//Plazo Gracia
			if(lstBonos == null) pGracia = "S";
			else pGracia = lstBonos.get(i-1).getPlazoGracia();
			
			//Interes
			interes = -1 * sInicial * objO.getTEP();
						
			//Cuota
			if(pGracia.equals("S")) {
				if(objO.getTEP() == 0)cuota=-1 * objO.getNominal()/objO.getPeriodos();
				else {
					aux = Math.pow(1+objO.getTEP(),objO.getPeriodos()-i+1);
					cuota = -1 * sInicial * aux * objO.getTEP() / (aux - 1);
				}
			}
			else if (pGracia.equals("T")) cuota = 0;
			else cuota = interes;
															
			//Amortizacion
			if(pGracia.equals("S")) amort = cuota - interes;
			else amort = 0;
			
			//Prima
			if(i == objO.getPeriodos()) {
				if(objO.getPrima()==0) prima = 0;
				else prima = -1 * sInicial * objO.getPrima() / 100;
			}
			else prima = 0;
			
			//Saldo Final
			if(pGracia.equals("T")) sFinal = sInicial - interes;
			else sFinal = sInicial + amort;
			
			//Flujo
			flujo = -1 * (cuota + prima);
			
			//Flujo actual
			FA = flujo / Math.pow(1 + objO.getCOK(),i);
			
			//Flujo actual x plazo
			FAP = FA * i * (double)frecuenciaBono / (double)360;
			
			//Factor p/ convexidad
			FC = FA * i * (1 + i);
			
			Bono objBono = new Bono(0, i, objO, pGracia, fechaBono, sInicial, interes, cuota, amort, prima, sFinal, flujo, FA, FAP, FC);
			lst.add(objBono);
		}

		return lst;
	}
	
	@Override
	@Transactional
	public Operacion CalcularIndicadores(int idOperacion, int idTipoTasa, List<Bono> lstBonos) {
		Operacion objO = buscarId(idOperacion);
		Frecuencia objF = fService.buscarId(objO.getFrecuencia().getIdFrecuencia());
		double tea, tep, COK, sumaFA = 0, sumaFAP = 0, sumaFC = 0, convexidad, duracion, dModificada, tir, trea;
		int prd=0, frecuenciaCap, frecuenciaBono = objF.getDias(), tPeriodos = 360 / frecuenciaBono * objO.getAnios();
		
		//PERIODOS
		objO.setFrecuencia(objF);
		objO.setPeriodos(tPeriodos);
		
		//COK
		COK = Math.pow((double)1 + objO.getTasaDescuento()/100, (double)frecuenciaBono / (double)360) - (double)1;
		objO.setCOK(COK);
		
		//TEA
		if(idTipoTasa == 1) tea = objO.getTasaInteres() / 100; //EFECTIVA
		else{
			frecuenciaCap = 360/fService.buscarId(objO.getCapitalizacion().getIdFrecuencia()).getDias();
			tea = Math.pow(1 + objO.getTasaInteres()/(100 * frecuenciaCap), frecuenciaCap)-1; //NOMINAL
		}
		objO.setTEA(tea);
		
		//TEP
		tep = Math.pow(1 + tea, (double)frecuenciaBono / (double)360)-1;
		objO.setTEP(tep);

		//BONOS
		List<Bono> lst = CalcularBonos(objO, idTipoTasa, lstBonos);
		
		//FLUJOS
		double[] flujos = new double[objO.getPeriodos()+1];
		flujos[0] = -1 * objO.getComercial() + objO.CostesIniciales() * -1;
		for(int i = 0; i < objO.getPeriodos(); i++) {
			//Guardar flujos
			flujos[i + 1] = lst.get(i).getFlujo();
			//Sumar flujos
			sumaFA+=lst.get(i).getFlujoActual();
			sumaFAP+=lst.get(i).getFlujoActualPlazo();
			sumaFC+=lst.get(i).getFactorConvexidad();
			//PRD
			if(sumaFA >= objO.getComercial() + objO.CostesIniciales())prd=i+1;
		}
		
		//VNA
		objO.setVna(-1 * objO.getComercial() + sumaFA + objO.CostesIniciales() * -1);

		//TIR
		tir = Irr.irr(flujos, -0.1); 
		objO.setTir(tir);
		
		//TREA
		trea = Math.pow(1 + tir, 360/frecuenciaBono)-1;
		objO.setTrea(trea);

		//PRD
		String aux2="";
		switch(frecuenciaBono) {
			case 30: aux2=" meses"; break;
			case 60: aux2=" bimestres"; break;
			case 90: aux2=" trimestres"; break;
			case 120: aux2=" cuatrim."; break;
			case 180: aux2=" semestres"; break;
			case 360: aux2=" años"; break;
			default: aux2=""; break;
		}
		if(prd>0)objO.setPrd(prd + aux2);
		else objO.setPrd("No recupera");
		
		//B/C
		objO.setBc(sumaFA/(objO.getComercial() + objO.CostesIniciales()));
				
		//Convexidad
		convexidad = sumaFC / (Math.pow(1 + objO.getCOK(),2) * sumaFA * Math.pow((double)360/(double)frecuenciaBono,2));
		objO.setConvexidad(convexidad);
		
		//Duracion
		duracion = sumaFAP / sumaFA;
		objO.setDuracion(duracion); 
		
		//Duracion modificada
		dModificada = duracion / (1 + objO.getCOK());
		objO.setDuracionModificada(dModificada);
		
		return objO;
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Operacion> operacionesUsuario(String correoUsuario) {
		return dOperacion.operacionesUsuario(correoUsuario);
	}
	
	@Override
	@Transactional
	public void eliminar(int idOperacion) {
		dOperacion.deleteById(idOperacion);
	}

}
