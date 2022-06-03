package pe.edu.upc.spring.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.spring.model.Rol;
import pe.edu.upc.spring.repository.IRolRepository;
import pe.edu.upc.spring.service.IRolService;

@Service
public class RolServiceImpl implements IRolService{
	
	@Autowired
	private IRolRepository dRol;

	@Override
	@Transactional
	public boolean registrar(Rol usuario) {
		Rol objRol = dRol.save(usuario);
		return objRol!=null;
	}
	@Override
	@Transactional
	public void eliminar(int idRol) {
		dRol.deleteById(idRol);
	}
	
	@Override
	@Transactional(readOnly=true)
	public Rol buscarId(int idRol) {
		Optional<Rol> opt = dRol.findById(idRol);
		if(opt.isPresent()) {
			Rol objRol = opt.get();
			return objRol;
		}
		return null;
	}
}
