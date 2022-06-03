package pe.edu.upc.spring.service;

import java.util.List;

import pe.edu.upc.spring.model.Usuario;

public interface IUsuarioService {
	public boolean registrar(Usuario usuario);
	public Usuario buscarId(String codigoUsuario);
	List<Usuario> listar();
}
