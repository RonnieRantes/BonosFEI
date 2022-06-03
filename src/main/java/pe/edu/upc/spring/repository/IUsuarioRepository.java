package pe.edu.upc.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.upc.spring.model.Usuario;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario,String>{
	//Implementado en JpaRepository
	public Usuario findByCorreoUsuario(String correoUsuario);
}