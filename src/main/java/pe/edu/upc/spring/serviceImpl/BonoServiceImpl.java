package pe.edu.upc.spring.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.spring.model.Bono;
import pe.edu.upc.spring.repository.IBonoRepository;
import pe.edu.upc.spring.service.IBonoService;

@Service
public class BonoServiceImpl implements IBonoService {

	@Autowired
	private IBonoRepository dBono;
	
	@Override
	@Transactional(readOnly=true)
	public Bono buscarId(int idBono) {
		Optional<Bono> opt = dBono.findById(idBono);
		if(opt.isPresent()) {
			Bono objBono = opt.get();
			return objBono;
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Bono> bonosOperacion(int idOperacion){
		return dBono.bonosOperacion(idOperacion);
	}
	
}
