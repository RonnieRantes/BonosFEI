package pe.edu.upc.spring.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.spring.model.Usuario;
import pe.edu.upc.spring.repository.IUsuarioRepository;

@Service
public class JpaUserDetailsService implements UserDetailsService{
	@Autowired
	private IUsuarioRepository dUsuario;
	
	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String correoUsuario) throws UsernameNotFoundException{
		 Usuario usuario = dUsuario.findByCorreoUsuario(correoUsuario);
		 List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		 authorities.add(new SimpleGrantedAuthority(usuario.getRol().getNombre()));
		 return new User(usuario.getCorreoUsuario(), usuario.getContrasenia(), usuario.getEnabled(),
				 true, true, true, authorities);
	}
}
