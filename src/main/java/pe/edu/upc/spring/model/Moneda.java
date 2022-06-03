package pe.edu.upc.spring.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Moneda")
public class Moneda implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idMoneda;
	
	@Column(name="nombreMoneda",length=15,nullable=false)
	private String nombre;

	@Column(name="simboloMoneda",length=10,nullable=false)
	private String simbolo;

	public Moneda() {
		super();
	}

	public Moneda(int idMoneda, String nombre, String simbolo) {
		super();
		this.idMoneda = idMoneda;
		this.nombre = nombre;
		this.simbolo = simbolo;
	}

	public int getIdMoneda() {
		return idMoneda;
	}

	public void setIdMoneda(int idMoneda) {
		this.idMoneda = idMoneda;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getSimbolo() {
		return simbolo;
	}

	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}
}
