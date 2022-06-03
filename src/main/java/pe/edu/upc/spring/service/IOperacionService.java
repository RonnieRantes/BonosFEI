package pe.edu.upc.spring.service;

import java.util.List;

import pe.edu.upc.spring.model.Bono;
import pe.edu.upc.spring.model.Operacion;

public interface IOperacionService {
	Operacion buscarId(int idOperacion);
	Operacion registrar(Operacion objO);
	void eliminar(int idOperacion);
	Operacion ultimaOperacion(String correoUsuario);
	boolean compararOperaciones(Operacion objO1, Operacion objO2);
	void limpiarBonos(int idOperacion);
	List<Bono> CalcularBonos(Operacion objO, int idTipoTasa, List<Bono> lstBonos);
	Operacion CalcularIndicadores(int idOperacion, int idTipoTasa, List<Bono> lstBonos);
	List<Operacion> operacionesUsuario(String correoUsuario);
}
