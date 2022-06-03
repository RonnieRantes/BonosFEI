package pe.edu.upc.spring.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TipoTasa")
public class TipoTasa implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idTipoTasa;
	
	@Column(name="nombreMoneda",length=15,nullable=false)
	private String nombre;

	public TipoTasa() {
		super();
	}

	public TipoTasa(int idTipoTasa, String nombre) {
		super();
		this.idTipoTasa = idTipoTasa;
		this.nombre = nombre;
	}

	public int getIdTipoTasa() {
		return idTipoTasa;
	}

	public void setIdTipoTasa(int idTipoTasa) {
		this.idTipoTasa = idTipoTasa;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
