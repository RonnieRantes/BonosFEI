package pe.edu.upc.spring.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="Bono")
public class Bono implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idBono;
	
	@Column(name="numeroBono",nullable=false)
	private int numero;
	
	@ManyToOne
	@JoinColumn(name="idOperacion", nullable=true)
	private Operacion idOperacion;
		
	@Column(name="plazoGraciaBono",nullable=false)
	private String plazoGracia;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Transient
	private Date fechaProgramada;
	
	@Transient
	private double saldoInicial;
	
	@Transient
	private double interes;

	@Transient
	private double cuota;

	@Transient
	private double amortizacion;
	
	@Transient
	private double prima;
	
	@Transient
	private double saldoFinal;
	
	@Transient
	private double flujo;

	@Transient
	private double flujoActual;
	
	@Transient
	private double flujoActualPlazo;
	
	@Transient
	private double factorConvexidad;

	public Bono() {
		super();
	}

	public Bono(int idBono, int numero, Operacion idOperacion, String plazoGracia,
			Date fechaProgramada, double saldoInicial, double interes, double cuota, double amortizacion, double prima,
			double saldoFinal, double flujo, double flujoActual, double flujoActualPlazo, double factorConvexidad) {
		super();
		this.idBono = idBono;
		this.numero = numero;
		this.idOperacion = idOperacion;
		this.plazoGracia = plazoGracia;
		this.fechaProgramada = fechaProgramada;
		this.saldoInicial = saldoInicial;
		this.interes = interes;
		this.cuota = cuota;
		this.amortizacion = amortizacion;
		this.prima = prima;
		this.saldoFinal = saldoFinal;
		this.flujo = flujo;
		this.flujoActual = flujoActual;
		this.flujoActualPlazo = flujoActualPlazo;
		this.factorConvexidad = factorConvexidad;
	}

	public int getIdBono() {
		return idBono;
	}

	public void setIdBono(int idBono) {
		this.idBono = idBono;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public Operacion getIdOperacion() {
		return idOperacion;
	}

	public void setIdOperacion(Operacion idOperacion) {
		this.idOperacion = idOperacion;
	}

	public String getPlazoGracia() {
		return plazoGracia;
	}

	public void setPlazoGracia(String plazoGracia) {
		this.plazoGracia = plazoGracia;
	}

	public Date getFechaProgramada() {
		return fechaProgramada;
	}

	public void setFechaProgramada(Date fechaProgramada) {
		this.fechaProgramada = fechaProgramada;
	}

	public double getSaldoInicial() {
		return saldoInicial;
	}

	public void setSaldoInicial(double saldoInicial) {
		this.saldoInicial = saldoInicial;
	}

	public double getInteres() {
		return interes;
	}

	public void setInteres(double interes) {
		this.interes = interes;
	}

	public double getCuota() {
		return cuota;
	}

	public void setCuota(double cuota) {
		this.cuota = cuota;
	}

	public double getAmortizacion() {
		return amortizacion;
	}

	public void setAmortizacion(double amortizacion) {
		this.amortizacion = amortizacion;
	}

	public double getPrima() {
		return prima;
	}

	public void setPrima(double prima) {
		this.prima = prima;
	}

	public double getSaldoFinal() {
		return saldoFinal;
	}

	public void setSaldoFinal(double saldoFinal) {
		this.saldoFinal = saldoFinal;
	}

	public double getFlujo() {
		return flujo;
	}

	public void setFlujo(double flujo) {
		this.flujo = flujo;
	}

	public double getFlujoActual() {
		return flujoActual;
	}

	public void setFlujoActual(double flujoActual) {
		this.flujoActual = flujoActual;
	}

	public double getFlujoActualPlazo() {
		return flujoActualPlazo;
	}

	public void setFlujoActualPlazo(double flujoActualPlazo) {
		this.flujoActualPlazo = flujoActualPlazo;
	}

	public double getFactorConvexidad() {
		return factorConvexidad;
	}

	public void setFactorConvexidad(double factorConvexidad) {
		this.factorConvexidad = factorConvexidad;
	}
	
	public double sSaldoInicial() {
		DecimalFormat twoDForm = new DecimalFormat("#.###");
		return Double.valueOf(twoDForm.format(saldoInicial));
	}
	public double sSaldoFinal() {
		int aux = 1; if(Math.round(saldoFinal) == 0)aux=0;
		DecimalFormat twoDForm = new DecimalFormat("#.###");
		return Double.valueOf(twoDForm.format(saldoFinal)) * aux;
	}
	public double sInteres() {
		DecimalFormat twoDForm = new DecimalFormat("#.###");
		return Double.valueOf(twoDForm.format(Math.abs(interes)));
	}
	public double sCuota() {
		DecimalFormat twoDForm = new DecimalFormat("#.###");
		return Double.valueOf(twoDForm.format(Math.abs(cuota)));
	}
	public double sAmortizacion() {
		DecimalFormat twoDForm = new DecimalFormat("#.###");
		return Double.valueOf(twoDForm.format(Math.abs(amortizacion)));
	}
	public double sPrima() {
		DecimalFormat twoDForm = new DecimalFormat("#.###");
		return Double.valueOf(twoDForm.format(Math.abs(prima)));
	}
}
