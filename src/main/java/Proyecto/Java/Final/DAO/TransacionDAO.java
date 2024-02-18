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
	
	@Column(name = "Tipo_Trasa")
	private String tipoTrasa;

	@ManyToOne
	@JoinColumn(name = "cuenta")
	private CuentaDAO cuenta;

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

	public String getTipoTrasa() {
		return tipoTrasa;
	}

	public void setTipoTrasa(String tipoTrasa) {
		this.tipoTrasa = tipoTrasa;
	}

	public CuentaDAO getCuenta() {
		return cuenta;
	}

	public void setCuenta(CuentaDAO cuenta) {
		this.cuenta = cuenta;
	}

	
	
}
