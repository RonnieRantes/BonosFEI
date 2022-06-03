package pe.edu.upc.spring.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.spring.model.Moneda;
import pe.edu.upc.spring.repository.IMonedaRepository;
import pe.edu.upc.spring.service.IMonedaService;

@Service
public class MonedaServiceImpl implements IMonedaService {

	@Autowired
	private IMonedaRepository dMoneda;
	
	@Override
	@Transactional(readOnly=true)
	public Moneda buscarId(int idMoneda) {
		Optional<Moneda> opt = dMoneda.findById(idMoneda);
		if(opt.isPresent()) {
			Moneda objMoneda = opt.get();
			return objMoneda;
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Moneda> listar() {
		return dMoneda.findAll();
	}
}
