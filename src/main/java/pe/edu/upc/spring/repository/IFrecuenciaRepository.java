package pe.edu.upc.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.spring.model.Frecuencia;

@Repository
public interface IFrecuenciaRepository extends JpaRepository<Frecuencia, Integer> {
	@Query("from Frecuencia f where f.dias > :dias")
	List<Frecuencia> Frecuencias(@Param("dias") int dias); //findAll
}