package pe.edu.upc.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.upc.spring.model.Rol;

@Repository
public interface IRolRepository extends JpaRepository<Rol, Integer>{
	//Implementado en JpaRepository
}