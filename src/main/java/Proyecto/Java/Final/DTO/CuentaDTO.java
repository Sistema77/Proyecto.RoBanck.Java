package Proyecto.Java.Final.DTO;

import java.util.Calendar;

public class CuentaDTO {
	
    private Long id_cuenta;
    private String numeroCuenta;
    private Double saldo;
    private Calendar fch_apertura;
    private Boolean conNomina;

    
    // Constructor
    
    public CuentaDTO() {
    	
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

}