package pe.edu.upc.spring.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.spring.model.TipoTasa;
import pe.edu.upc.spring.repository.ITipoTasaRepository;
import pe.edu.upc.spring.service.ITipoTasaService;

@Service
public class TipoTasaServiceImpl implements ITipoTasaService {

	@Autowired
	private ITipoTasaRepository dTipoTasa;
	
	@Override
	@Transactional(readOnly=true)
	public TipoTasa buscarId(int idTipoTasa) {
		Optional<TipoTasa> opt = dTipoTasa.findById(idTipoTasa);
		if(opt.isPresent()) {
			TipoTasa objTipoTasa = opt.get();
			return objTipoTasa;
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<TipoTasa> listar() {
		return dTipoTasa.findAll();
	}
}
