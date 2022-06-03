package pe.edu.upc.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.spring.model.Bono;

@Repository
public interface IBonoRepository extends JpaRepository<Bono, Integer> {
	@Query("from Bono b where b.idOperacion.idOperacion = :idOperacion")
	List<Bono> bonosOperacion(@Param("idOperacion") int idOperacion); //findAll
}
