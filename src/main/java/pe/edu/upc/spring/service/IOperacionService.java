package pe.edu.upc.spring.service;

import java.util.List;

import pe.edu.upc.spring.model.Bono;
import pe.edu.upc.spring.model.Operacion;

public interface IOperacionService {
	Operacion buscarId(int idOperacion);
	Operacion registrar(Operacion objO);
	void eliminar(int idOperacion);
	void limpiar(String correoUsuario);
	List<Operacion> operacionesUsuario(String correoUsuario);
	boolean compararOperaciones(Operacion objO1, Operacion objO2);
	Operacion CalcularIntermedios(Operacion objO, String tipoTasa);
	List<Bono> CalcularBonos(Operacion objO, List<Bono> lstBonos);
	Operacion CalcularIndicadores(Operacion objO, List<Bono> lstBonos);
}
