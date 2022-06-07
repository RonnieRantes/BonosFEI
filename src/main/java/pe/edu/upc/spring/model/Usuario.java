package pe.edu.upc.spring.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Usuario")
public class Usuario implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="correoUsuario",length=75,unique=true,nullable=false)
	private String correoUsuario;

	@Column(name="nombresUsuario",length=50,nullable=false)
	private String nombres;

	@Column(name="apellidosUsuario",length=50,nullable=false)
	private String apellidos;

	@ManyToOne
	@JoinColumn(name="idRol", nullable=true)
	private Rol rol;
	
	@ManyToOne
	@JoinColumn(name="idMoneda", nullable=true)
	private Moneda moneda;

	@Column(name="tipoTasaUsuario",length=10,nullable=false)
	private String tipoTasa;
			
	@Column(name="contraseniaUsuario",length=75,nullable=false)
	private String contrasenia;
	
	private Boolean enabled;
	
	public Usuario() {
		super();
		this.enabled = true;
	}

	public Usuario(String correoUsuario, String nombres, String apellidos, Rol rol, Moneda moneda,
			String contrasenia) {
		super();
		this.correoUsuario = correoUsuario;
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.rol = rol;
		this.moneda = moneda;
		this.contrasenia = contrasenia;
		this.enabled = true;
	}

	public String getCorreoUsuario() {
		return correoUsuario;
	}

	public void setCorreoUsuario(String correoUsuario) {
		this.correoUsuario = correoUsuario;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public Moneda getMoneda() {
		return moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getTipoTasa() {
		return tipoTasa;
	}

	public void setTipoTasa(String tipoTasa) {
		this.tipoTasa = tipoTasa;
	}
}
