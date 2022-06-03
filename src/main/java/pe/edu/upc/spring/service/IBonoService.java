package pe.edu.upc.spring.service;

import java.util.List;

import pe.edu.upc.spring.model.Bono;

public interface IBonoService {
	Bono buscarId(int idBono);
	List<Bono> bonosOperacion(int idOperacion);
}
