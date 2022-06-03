package pe.edu.upc.spring.service;

import pe.edu.upc.spring.model.Rol;

public interface IRolService {
	public boolean registrar(Rol rol);
	public void eliminar(int idRol);
	Rol buscarId(int idRol);
}
