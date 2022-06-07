package pe.edu.upc.spring.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

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
@Table(name="Operacion")
public class Operacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idOperacion;
		
	@ManyToOne
	@JoinColumn(name="correoUsuario", nullable=false)
	private Usuario correoUsuario;
	
	@Column(name="diasAnioOperacion",nullable=false)
	private int diasAnio;
	
	@Column(name="nominalOperacion",nullable=false)
	private double nominal;

	@Column(name="comercialOperacion",nullable=false)
	private double comercial;
	
	@Column(name="tasaInteresOperacion",nullable=false)
	private double tasaInteres;
	
	@ManyToOne
	@JoinColumn(name="capitalizacionOperacion", nullable=true)
	private Frecuencia capitalizacion;
	
	@Column(name="tasaDescuentoOperacion",nullable=false)
	private double tasaDescuento;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fechaAccion", nullable=false)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
	private Date fechaAccion;

	@Temporal(TemporalType.DATE)
	@Column(name="fechaEmision", nullable=false)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date fechaEmision;
	
	@Column(name="aniosOperacion",nullable=false)
	private int anios;
		
	@ManyToOne
	@JoinColumn(name="frecuenciaOperacion", nullable=true)
	private Frecuencia frecuencia;

	@Column(name="primaOperacion", nullable=false)
	private double prima;

	@Column(name="flotacionOperacion", nullable=false)
	private double flotacion;

	@Column(name="cavaliOperacion", nullable=false)
	private double cavali;
	
	@Transient
	private double TEA;
	
	@Transient
	private double TEP;
	
	@Transient
	private double COK;
	
	@Transient
	private int periodos;
	 
	@Transient
	private double vna;
	
	@Transient
	private double tir;
	
	@Transient
	private double trea;
	
	@Transient
	private String prd;
	
	@Transient
	private double bc;

	@Transient
	private double convexidad;

	@Transient
	private double duracion;
	
	@Transient
	private double duracionModificada;
	
	@Transient
	private List<Bono> listaBonos;
		
	public Operacion() {
		super();
	}
	
	public double CostesIniciales() {
		DecimalFormat twoDForm = new DecimalFormat("#.###");
		return Double.valueOf(twoDForm.format(comercial * (flotacion/100 + cavali/100)));
	}

	public int getIdOperacion() {
		return idOperacion;
	}

	public void setIdOperacion(int idOperacion) {
		this.idOperacion = idOperacion;
	}

	public Usuario getCorreoUsuario() {
		return correoUsuario;
	}

	public void setCorreoUsuario(Usuario correoUsuario) {
		this.correoUsuario = correoUsuario;
	}

	public double getNominal() {
		return nominal;
	}

	public void setNominal(double nominal) {
		this.nominal = nominal;
	}

	public double getComercial() {
		return comercial;
	}

	public void setComercial(double comercial) {
		this.comercial = comercial;
	}

	public double getTasaInteres() {
		return tasaInteres;
	}

	public void setTasaInteres(double tasaInteres) {
		this.tasaInteres = tasaInteres;
	}

	public Frecuencia getCapitalizacion() {
		return capitalizacion;
	}

	public void setCapitalizacion(Frecuencia capitalizacion) {
		this.capitalizacion = capitalizacion;
	}

	public double getTasaDescuento() {
		return tasaDescuento;
	}

	public void setTasaDescuento(double tasaDescuento) {
		this.tasaDescuento = tasaDescuento;
	}

	public Date getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public int getAnios() {
		return anios;
	}

	public void setAnios(int anios) {
		this.anios = anios;
	}

	public Frecuencia getFrecuencia() {
		return frecuencia;
	}

	public void setFrecuencia(Frecuencia frecuencia) {
		this.frecuencia = frecuencia;
	}

	public double getPrima() {
		return prima;
	}

	public void setPrima(double prima) {
		this.prima = prima;
	}

	public double getFlotacion() {
		return flotacion;
	}

	public void setFlotacion(double flotacion) {
		this.flotacion = flotacion;
	}

	public double getCavali() {
		return cavali;
	}

	public void setCavali(double cavali) {
		this.cavali = cavali;
	}

	public double getCOK() {
		return COK;
	}

	public void setCOK(double cOK) {
		COK = cOK;
	}

	public int getPeriodos() {
		return periodos;
	}

	public void setPeriodos(int periodos) {
		this.periodos = periodos;
	}

	public double getVna() {
		return vna;
	}

	public void setVna(double vna) {
		this.vna = vna;
	}

	public double getTir() {
		return tir;
	}

	public void setTir(double tir) {
		this.tir = tir;
	}

	public double getTrea() {
		return trea;
	}

	public void setTrea(double trea) {
		this.trea = trea;
	}

	public String getPrd() {
		return prd;
	}

	public void setPrd(String prd) {
		this.prd = prd;
	}

	public double getBc() {
		return bc;
	}

	public void setBc(double bc) {
		this.bc = bc;
	}

	public double getConvexidad() {
		return convexidad;
	}

	public void setConvexidad(double convexidad) {
		this.convexidad = convexidad;
	}

	public double getDuracion() {
		return duracion;
	}

	public void setDuracion(double duracion) {
		this.duracion = duracion;
	}

	public double getDuracionModificada() {
		return duracionModificada;
	}

	public void setDuracionModificada(double duracionModificada) {
		this.duracionModificada = duracionModificada;
	}
	
	public double sTEA() {
		DecimalFormat twoDForm = new DecimalFormat("#.#####");
		return Double.valueOf(twoDForm.format(TEA * 100));
	}
	
	public double sTEP() {
		DecimalFormat twoDForm = new DecimalFormat("#.#####");
		return Double.valueOf(twoDForm.format(TEP * 100));
	}
	
	public double sCOK() {
		DecimalFormat twoDForm = new DecimalFormat("#.#####");
		return Double.valueOf(twoDForm.format(COK * 100));
	}
	
	public double sVna() {
		DecimalFormat twoDForm = new DecimalFormat("#.###");
		return Double.valueOf(twoDForm.format(Math.abs(vna)));
	}

	public String sTir() {
		if(Double.isNaN(tir)) return "-";
		DecimalFormat twoDForm = new DecimalFormat("#.#####");
		return Double.valueOf(twoDForm.format(Math.abs(tir) * 100)).toString();
	}
	
	public String sTrea() {
		if(Double.isNaN(trea)) return "-";
		DecimalFormat twoDForm = new DecimalFormat("#.#####");
		return Double.valueOf(twoDForm.format(trea * 100)).toString();
	}
	
	public double sBc() {
		DecimalFormat twoDForm = new DecimalFormat("#.###");
		return Double.valueOf(twoDForm.format(bc));
	}
	
	public double sConvexidad() {
		DecimalFormat twoDForm = new DecimalFormat("#.###");
		return Double.valueOf(twoDForm.format(convexidad));
	}
	
	public double sDuracion() {
		DecimalFormat twoDForm = new DecimalFormat("#.###");
		return Double.valueOf(twoDForm.format(duracion));
	}
	
	public double sDuracionModificada() {
		DecimalFormat twoDForm = new DecimalFormat("#.###");
		return Double.valueOf(twoDForm.format(duracionModificada));
	}

	public List<Bono> getListaBonos() {
		return listaBonos;
	}

	public void setListaBonos(List<Bono> listaBonos) {
		this.listaBonos = listaBonos;
	}

	public double getTEA() {
		return TEA;
	}

	public void setTEA(double tEA) {
		TEA = tEA;
	}

	public double getTEP() {
		return TEP;
	}

	public void setTEP(double tEP) {
		TEP = tEP;
	}

	public Date getFechaAccion() {
		return fechaAccion;
	}

	public void setFechaAccion(Date fechaAccion) {
		this.fechaAccion = fechaAccion;
	}

	public int getDiasAnio() {
		return diasAnio;
	}

	public void setDiasAnio(int diasAnio) {
		this.diasAnio = diasAnio;
	}
}
