package Proyecto.Java.Final.Servicios;

import java.util.List;

import Proyecto.Java.Final.DAO.CuentaDAO;
import Proyecto.Java.Final.DTO.CuentaDTO;


public interface ICuentaToDto {
	/**
	 * Método que convierte campo a campo un objeto entidad Usuario a usuarioDTO
	 */
	public CuentaDTO cuentaToDto(CuentaDAO u);
	
	/**
	 * Metodo que convierte todos los objetos entidad usuario DAO a una lista UsuarioDTO 
	 */
	public List<CuentaDTO> listaCuentaToDto(List<CuentaDAO> listaCuenta);
}

