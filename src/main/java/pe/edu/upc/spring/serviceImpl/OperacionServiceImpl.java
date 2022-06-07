package pe.edu.upc.spring.serviceImpl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.poi.ss.formula.functions.*;

import pe.edu.upc.spring.model.Bono;
import pe.edu.upc.spring.model.Operacion;
import pe.edu.upc.spring.repository.IOperacionRepository;
import pe.edu.upc.spring.service.IFrecuenciaService;
import pe.edu.upc.spring.service.IOperacionService;

@Service
public class OperacionServiceImpl implements IOperacionService {

	@Autowired
	private IOperacionRepository dOperacion;
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
	@Transactional
	public void eliminar(int idOperacion) {
		dOperacion.deleteById(idOperacion);
	}
	
	@Override
	@Transactional
	public void limpiar(String correoUsuario) {
		for(Operacion o: operacionesUsuario(correoUsuario))eliminar(o.getIdOperacion());
	}

	@Override
	@Transactional(readOnly=true)
	public List<Operacion> operacionesUsuario(String correoUsuario) {
		return dOperacion.operacionesUsuario(correoUsuario);
	}

	@Override
	@Transactional(readOnly=true)
	public boolean compararOperaciones(Operacion objO1, Operacion objO2) {
		if(objO1 == null || objO2 == null)return false;
		if(objO1.getDiasAnio() != objO2.getDiasAnio())return false;
		if(objO1.getNominal() != objO2.getNominal())return false;
		if(objO1.getComercial() != objO2.getComercial())return false;
		if(objO1.getTasaInteres() != objO2.getTasaInteres())return false;
		if(objO1.getTasaDescuento() != objO2.getTasaDescuento())return false;
		if(objO1.getFechaEmision().getTime() != objO2.getFechaEmision().getTime())return false;
		if(objO1.getAnios() != objO2.getAnios())return false;
		if(objO1.getCapitalizacion() != null && objO2.getCapitalizacion() != null && objO1.getCapitalizacion().getIdFrecuencia() != objO2.getCapitalizacion().getIdFrecuencia())return false;
		if(objO1.getFrecuencia().getIdFrecuencia() != objO2.getFrecuencia().getIdFrecuencia())return false;
		if(objO1.getPrima() != objO2.getPrima())return false;
		if(objO1.getFlotacion() != objO2.getFlotacion())return false;
		if(objO1.getCavali() != objO2.getCavali())return false;
		return true;
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
	@Transactional(readOnly=true)
	public Operacion CalcularIntermedios(Operacion objO, String tipoTasa) {
		double tea, tep, COK;
		int tPeriodos, frecuenciaCap, frecuenciaBono = fService.buscarId(objO.getFrecuencia().getIdFrecuencia()).getDias(); 

		//PERIODOS
		tPeriodos = objO.getDiasAnio() / frecuenciaBono * objO.getAnios();
		objO.setPeriodos(tPeriodos);
		
		//COK
		COK = Math.pow((double)1 + objO.getTasaDescuento()/100, (double)frecuenciaBono / (double)objO.getDiasAnio()) - (double)1;
		objO.setCOK(COK);
		
		//TEA
		if(tipoTasa.equals("Efectiva")) tea = objO.getTasaInteres() / 100; //EFECTIVA
		else{
			frecuenciaCap = objO.getDiasAnio()/fService.buscarId(objO.getCapitalizacion().getIdFrecuencia()).getDias();
			tea = Math.pow(1 + objO.getTasaInteres()/(100 * frecuenciaCap), frecuenciaCap)-1; //NOMINAL
		}
		objO.setTEA(tea);
		
		//TEP
		tep = Math.pow(1 + tea, (double)frecuenciaBono / (double)objO.getDiasAnio())-1;
		objO.setTEP(tep);

		return objO;
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Bono> CalcularBonos(Operacion objO, List<Bono> lst) {
		double aux, sInicial, sFinal = 0, interes, cuota, amort, prima, flujo, FA, FAP, FC; 
		int size = lst.size(), frecuenciaBono = fService.buscarId(objO.getFrecuencia().getIdFrecuencia()).getDias();
		long auxx;
		String pGracia;
		System.out.println("----SIZE LST PRE: " + lst.size());
		for(int i = 1; i <= objO.getPeriodos(); i++) {
			System.out.println("----i: " + i);
			//Fecha Emisión
			auxx = (long)i * (long)frecuenciaBono * (long)(24*60*60*1000);
			Date fechaBono = new Date(objO.getFechaEmision().getTime() + auxx);
	
			//Saldo Inicial
			if(i == 1) sInicial = objO.getNominal();
			else sInicial = sFinal;
			
			//Plazo Gracia
			if(size ==0) pGracia = "S";
			else pGracia = lst.get(i-1).getPlazoGracia();
			
			//Interes
			interes = -1 * sInicial * objO.getTEP();
						
			//Cuota
			if(pGracia.equals("S")) {
				if(objO.getTEP() == 0) {
					cuota = -1 * sInicial / (objO.getPeriodos() - i + 1);
				}
				else {
					aux = Math.pow(1+objO.getTEP(),objO.getPeriodos() - i + 1);
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
				if(objO.getPrima() == 0) prima = 0;
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
			FAP = FA * i * (double)frecuenciaBono / (double)objO.getDiasAnio();
			
			//Factor p/ convexidad
			FC = FA * i * (1 + i);
			
			Bono objB = new Bono(i, pGracia, fechaBono, sInicial, interes, cuota, amort, prima, sFinal, flujo, FA, FAP, FC);
			if(size == 0)lst.add(objB);
			else lst.set(i-1, objB);
		}
		System.out.println("----SIZE LST DSPS: " + lst.size());
		return lst;
	}
	
	@Override
	@Transactional
	public Operacion CalcularIndicadores(Operacion objO, List<Bono> lst) {
		double sumaFA = 0, sumaFAP = 0, sumaFC = 0, convexidad, duracion, dModificada, tir, trea;
		int prd = 0, frecuenciaBono = fService.buscarId(objO.getFrecuencia().getIdFrecuencia()).getDias();
				
		//FLUJOS
		double[] flujos = new double[objO.getPeriodos()+1];
		flujos[0] = -1 * objO.getComercial() + objO.CostesIniciales() * -1;
		for(int i = 0; i < objO.getPeriodos(); i++) {
			//Recopilar flujos
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
		trea = Math.pow(1 + tir, objO.getDiasAnio()/frecuenciaBono)-1;
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
		convexidad = sumaFC / (Math.pow(1 + objO.getCOK(),2) * sumaFA * Math.pow((double)objO.getDiasAnio()/(double)frecuenciaBono,2));
		objO.setConvexidad(convexidad);
		
		//Duracion
		duracion = sumaFAP / sumaFA;
		objO.setDuracion(duracion); 
		
		//Duracion modificada
		dModificada = duracion / (1 + objO.getCOK());
		objO.setDuracionModificada(dModificada);
		
		return objO;
	}
}
