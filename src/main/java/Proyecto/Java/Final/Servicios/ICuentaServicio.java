package Proyecto.Java.Final.Servicios;

import java.util.List;

import Proyecto.Java.Final.DAO.CuentaDAO;
import Proyecto.Java.Final.DTO.CuentaDTO;
import Proyecto.Java.Final.DTO.UsuarioDTO;

public interface ICuentaServicio {
	public CuentaDAO crearCuenta(String usuario);
	
	public List<CuentaDTO> verCuenta(String usuario);
	
	public CuentaDAO eliminarCuenta(long id);
	
	public CuentaDAO buscarCuentaId(long id);
	
	public List<CuentaDAO> listadoCuentaDAO();
	
	public void modificarCuenta(long id, CuentaDTO cuentaModificado);
}
