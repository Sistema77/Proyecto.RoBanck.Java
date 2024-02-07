package Proyecto.Java.Final.DAO;

import java.util.Calendar;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario", schema = "schemausuario")
public class UsuarioDAO {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_usuario")
	private Long id_usuario;

	@Column(name = "name")
	private String name;

	@Column(name = "dni")
	private String dni;

	@Column(name = "lastName")
	private String lastName;

	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;

	@Column(name = "tlf")
	private String tlf;
	
	@Column(name = "token")
	private String token;
	
	@Column(name = "expiracionToken")
	private Calendar expiracionToken;
	
	@Column(name = "fch_alta")
	private Calendar fch_alta;

	@Column(name = "foto")
	private byte[] foto;

	@Column(name = "tipoUsuario")
	private String tipoUsuario;

	@OneToMany(mappedBy = "usuario")
	private List<CuentaDAO> cuentas;

	public UsuarioDAO() {
	}

	public Long getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(Long id_usuario) {
		this.id_usuario = id_usuario;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTlf() {
		return tlf;
	}

	public void setTlf(String tlf) {
		this.tlf = tlf;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public String getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public List<CuentaDAO> getCuentas() {
		return cuentas;
	}

	public void setCuentas(List<CuentaDAO> cuentas) {
		this.cuentas = cuentas;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Calendar getExpiracionToken() {
		return expiracionToken;
	}

	public void setExpiracionToken(Calendar expiracionToken) {
		this.expiracionToken = expiracionToken;
	}

	public Calendar getFch_alta() {
		return fch_alta;
	}

	public void setFch_alta(Calendar fch_alta) {
		this.fch_alta = fch_alta;
	}
	
	
}