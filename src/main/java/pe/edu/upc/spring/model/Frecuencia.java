package pe.edu.upc.spring.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Frecuencia")
public class Frecuencia implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idFrecuencia;
	
	@Column(name="nombreFrecuencia",length=15,nullable=false)
	private String nombre;
	
	@Column(name="diasFrecuencia",nullable=false)
	private int dias;

	public Frecuencia() {
		super();
	}

	public Frecuencia(int idFrecuencia, String nombre, int dias) {
		super();
		this.idFrecuencia = idFrecuencia;
		this.nombre = nombre;
		this.dias = dias;
	}

	public int getIdFrecuencia() {
		return idFrecuencia;
	}

	public void setIdFrecuencia(int idFrecuencia) {
		this.idFrecuencia = idFrecuencia;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getDias() {
		return dias;
	}

	public void setDias(int dias) {
		this.dias = dias;
	}
}
