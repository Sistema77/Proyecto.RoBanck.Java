package Proyecto.Java.Final.DAO;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;



@Entity
@Table(name = "credito", schema = "schemabody")
public class CreditoDAO {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_Credito")
	private Long id_Credito;

	@Column(name = "tipoPrestamo")
	private String tipoPrestamo;

	@Column(name = "CantidadPrestamo")
	private Double CantidadPrestamo;

	@Column(name = "tasaInteres")
	private Double tasaInteres;

	@Column(name = "EstadoPrestamo")
	private String EstadoPrestamo;

	@Column(name = "CuotaMensual")
	private Double CuotaMensual;

	@Column(name = "fch_inicio")
	private Date fch_inicio;

	@Column(name = "fch_final")
	private Date fch_final;

	@ManyToOne
	@JoinColumn(name = "id_cuenta")
	private CuentaDAO cuenta;
	
	public CreditoDAO() {}

	public Long getId_Credito() {
		return id_Credito;
	}

	public void setId_Credito(Long id_Credito) {
		this.id_Credito = id_Credito;
	}

	public String getTipoPrestamo() {
		return tipoPrestamo;
	}

	public void setTipoPrestamo(String tipoPrestamo) {
		this.tipoPrestamo = tipoPrestamo;
	}

	public Double getCantidadPrestamo() {
		return CantidadPrestamo;
	}

	public void setCantidadPrestamo(Double cantidadPrestamo) {
		CantidadPrestamo = cantidadPrestamo;
	}

	public Double getTasaInteres() {
		return tasaInteres;
	}

	public void setTasaInteres(Double tasaInteres) {
		this.tasaInteres = tasaInteres;
	}

	public String getEstadoPrestamo() {
		return EstadoPrestamo;
	}

	public void setEstadoPrestamo(String estadoPrestamo) {
		EstadoPrestamo = estadoPrestamo;
	}

	public Double getCuotaMensual() {
		return CuotaMensual;
	}

	public void setCuotaMensual(Double cuotaMensual) {
		CuotaMensual = cuotaMensual;
	}

	public Date getFch_inicio() {
		return fch_inicio;
	}

	public void setFch_inicio(Date fch_inicio) {
		this.fch_inicio = fch_inicio;
	}

	public Date getFch_final() {
		return fch_final;
	}

	public void setFch_final(Date fch_final) {
		this.fch_final = fch_final;
	}

	public CuentaDAO getCuenta() {
		return cuenta;
	}

	public void setCuenta(CuentaDAO cuenta) {
		this.cuenta = cuenta;
	}
}
