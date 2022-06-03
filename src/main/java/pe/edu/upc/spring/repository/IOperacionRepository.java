package pe.edu.upc.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.spring.model.Operacion;

@Repository
public interface IOperacionRepository extends JpaRepository<Operacion, Integer> {
	@Query("from Operacion o where o.correoUsuario.correoUsuario = :correoUsuario")
	List<Operacion> operacionesUsuario(@Param("correoUsuario") String correoUsuario); //findAll
}
