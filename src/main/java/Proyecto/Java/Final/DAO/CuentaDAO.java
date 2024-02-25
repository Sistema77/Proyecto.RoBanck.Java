package Proyecto.Java.Final.DAO;

import java.util.Calendar;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;



@Entity
@Table(name = "cuenta", schema = "schemabody")
public class CuentaDAO {
	
	// Atributos
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id_cuenta")
    private Long id_cuenta;

    @Column(name = "numeroCuenta")
    private String numeroCuenta;

    @Column(name = "saldo")
    private Double saldo;

    @Column(name = "fch_apertura")
    private Calendar fch_apertura;

    @Column(name = "conNomina")
    private Boolean conNomina;

    @ManyToOne
    @JoinColumn(name="id_user", referencedColumnName = "id_usuario")
    private UsuarioDAO usuario;

    @OneToMany(mappedBy="id_trasaciones")
    private List<TransacionDAO> transaciones;

    @OneToMany(mappedBy="id_Credito")
    private List<CreditoDAO> creditos;
    
    // Constructor
    
    public CuentaDAO() {
    	
    }

    // Getter / Setter
    
	public Long getId_cuenta() {
		return id_cuenta;
	}

	public void setId_cuenta(Long id_cuenta) {
		this.id_cuenta = id_cuenta;
	}

	public String getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	public Calendar getFch_apertura() {
		return fch_apertura;
	}

	public void setFch_apertura(Calendar fch_apertura) {
		this.fch_apertura = fch_apertura;
	}

	public Boolean getConNomina() {
		return conNomina;
	}

	public void setConNomina(Boolean conNomina) {
		this.conNomina = conNomina;
	}

	public UsuarioDAO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDAO usuario) {
		this.usuario = usuario;
	}



	public List<TransacionDAO> getTransaciones() {
		return transaciones;
	}

	public void setTransaciones(List<TransacionDAO> transaciones) {
		this.transaciones = transaciones;
	}

	public List<CreditoDAO> getCreditos() {
		return creditos;
	}

	public void setCreditos(List<CreditoDAO> creditos) {
		this.creditos = creditos;
	}
}
