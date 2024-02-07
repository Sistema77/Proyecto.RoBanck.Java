package Proyecto.Java.Final.DAO;

import java.util.Calendar;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "transacion", schema = "schemabody")
public class TransacionDAO {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_trasaciones")
	private Long id_trasaciones;

	@Column(name = "cantidadDinero")
	private Double cantidadDinero;

	@Column(name = "fecha_Hora")
	private Calendar fecha_Hora;

	@ManyToOne
	@JoinColumn(name = "cuentaEnvia")
	private CuentaDAO cuentaEnvia;

	@ManyToOne
	@JoinColumn(name = "cuentaRecive")
	private CuentaDAO cuentaRecive;

	public TransacionDAO() {
	}

	public Long getId_trasaciones() {
		return id_trasaciones;
	}

	public void setId_trasaciones(Long id_trasaciones) {
		this.id_trasaciones = id_trasaciones;
	}

	public Double getCantidadDinero() {
		return cantidadDinero;
	}

	public void setCantidadDinero(Double cantidadDinero) {
		this.cantidadDinero = cantidadDinero;
	}

	public Calendar getFecha_Hora() {
		return fecha_Hora;
	}

	public void setFecha_Hora(Calendar fecha_Hora) {
		this.fecha_Hora = fecha_Hora;
	}

	public CuentaDAO getCuentaEnvia() {
		return cuentaEnvia;
	}

	public void setCuentaEnvia(CuentaDAO cuentaEnvia) {
		this.cuentaEnvia = cuentaEnvia;
	}

	public CuentaDAO getCuentaRecive() {
		return cuentaRecive;
	}

	public void setCuentaRecive(CuentaDAO cuentaRecive) {
		this.cuentaRecive = cuentaRecive;
	}
	
}
