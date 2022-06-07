package pe.edu.upc.spring.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;

public class Bono implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int numero;
			
	private String plazoGracia;
	
	private Date fechaProgramada;
	
	private double saldoInicial;
	
	private double interes;

	private double cuota;

	private double amortizacion;
	
	private double prima;
	
	private double saldoFinal;
	
	private double flujo;

	private double flujoActual;
	
	private double flujoActualPlazo;
	
	private double factorConvexidad;

	public Bono() {
		super();
	}

	public Bono(int numero, String plazoGracia,
			Date fechaProgramada, double saldoInicial, double interes, double cuota, double amortizacion, double prima,
			double saldoFinal, double flujo, double flujoActual, double flujoActualPlazo, double factorConvexidad) {
		super();
		this.numero = numero;
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

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
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
		DecimalFormat twoDForm = new DecimalFormat("#.###");
		return Double.valueOf(twoDForm.format(Math.abs(saldoFinal)));
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
