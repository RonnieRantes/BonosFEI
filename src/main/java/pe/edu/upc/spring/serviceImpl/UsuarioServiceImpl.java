package pe.edu.upc.spring.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.spring.model.Usuario;
import pe.edu.upc.spring.repository.IUsuarioRepository;
import pe.edu.upc.spring.service.IUsuarioService;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

	@Autowired
	private IUsuarioRepository dUsuario;
		
	@Override
	@Transactional
	public boolean registrar(Usuario usuario) {
		Usuario objUsuario = dUsuario.save(usuario);
		return objUsuario!=null;
	}

	@Override
	@Transactional(readOnly=true)
	public Usuario buscarId(String codigoUsuario) {
		Optional<Usuario> opt = dUsuario.findById(codigoUsuario);
		if(opt.isPresent()) {
			Usuario objUsuario = opt.get();
			return objUsuario;
		}
		return null;
	}

	@Override
	@Transactional(readOnly=true)
	public List<Usuario> listar() {
		return dUsuario.findAll();
	}
}
