package pe.edu.upc.spring.service;

import java.util.List;

import pe.edu.upc.spring.model.Moneda;

public interface IMonedaService {
	Moneda buscarId(int idMoneda);
	List<Moneda> listar();
}
