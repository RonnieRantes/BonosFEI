package pe.edu.upc.spring.service;

import java.util.List;

import pe.edu.upc.spring.model.TipoTasa;

public interface ITipoTasaService {
	TipoTasa buscarId(int idTipoTasa);
	List<TipoTasa> listar();
}
