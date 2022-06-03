package pe.edu.upc.spring.service;

import java.util.List;

import pe.edu.upc.spring.model.Frecuencia;

public interface IFrecuenciaService {
	Frecuencia buscarId(int idFrecuencia);
	List<Frecuencia> frecuenciasBono();
	List<Frecuencia> frecuenciasCapitalizacion();
}
