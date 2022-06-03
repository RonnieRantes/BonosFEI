package pe.edu.upc.spring.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.spring.model.Frecuencia;
import pe.edu.upc.spring.repository.IFrecuenciaRepository;
import pe.edu.upc.spring.service.IFrecuenciaService;

@Service
public class FrecuenciaServiceImpl implements IFrecuenciaService {

	@Autowired
	private IFrecuenciaRepository dFrecuencia;
	
	@Override
	@Transactional(readOnly=true)
	public Frecuencia buscarId(int idFrecuencia) {
		Optional<Frecuencia> opt = dFrecuencia.findById(idFrecuencia);
		if(opt.isPresent()) {
			Frecuencia objFrecuencia = opt.get();
			return objFrecuencia;
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Frecuencia> frecuenciasBono() {
		return dFrecuencia.Frecuencias(29);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Frecuencia> frecuenciasCapitalizacion() {
		return dFrecuencia.Frecuencias(0);
	}

}
