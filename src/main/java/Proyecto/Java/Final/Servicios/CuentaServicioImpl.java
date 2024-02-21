package Proyecto.Java.Final.Servicios;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import Proyecto.Java.Final.DAO.CuentaDAO;
import Proyecto.Java.Final.DAO.UsuarioDAO;
import Proyecto.Java.Final.DTO.CuentaDTO;
import Proyecto.Java.Final.DTO.UsuarioDTO;
import Proyecto.Java.Final.Repositorio.CuentaRepositorio;
import Proyecto.Java.Final.Repositorio.UsuarioRepositorio;

@Service
public class CuentaServicioImpl implements ICuentaServicio{
	 
	@Autowired
	private CuentaRepositorio cuentaRepositorio; 
	
	@Autowired
	private ICuentaToDto toDto;
	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	@Override
	public CuentaDAO crearCuenta(String usuarioDTO) {
	    try {
	        // Comprueba si ya existe un usuario por el Email
	    	
	        UsuarioDAO usuario = usuarioRepositorio.findByEmail(usuarioDTO);

	        // Comprueba si ya existe un usuario con el email que quiere registrar
	        if (usuario != null && usuario.isCuentaConfirmada()) {
	            CuentaDAO cuenta = new CuentaDAO();
	            cuenta.setConNomina(false);
	            cuenta.setNumeroCuenta("ES21 1465 0100 72 2030876293");
	            cuenta.setFch_apertura(Calendar.getInstance());
	            cuenta.setSaldo(0.0);
	            cuenta.setUsuario(usuario);
	            cuentaRepositorio.save(cuenta);
	            //logger.info("Cuenta " + cuenta.getId_cuenta() + " Creada");
	            return cuenta;
	        } else if (usuario != null) { 
	            return null;
	        } else {
	            return null; // Devuelve null si no se encuentra el usuario
	        }
	    } catch (Exception e) {
	        //logger.error("Error en registrar: " + e.getMessage(), e);
	        return null; // Devuelve null en caso de excepción
	    }
	}

	@Override
	public List<CuentaDTO> verCuenta(String usuarioDTO) {
		  try {
		        // Comprueba si ya existe un usuario por el Email
		    	
		        UsuarioDAO usuario = usuarioRepositorio.findByEmail(usuarioDTO);

		        // Comprueba si ya existe un usuario con el email que quiere registrar
		        if (usuario != null && usuario.isCuentaConfirmada()) {
		        	
		        	// Devuelve una lista de las cuentas con relacion con el Usuario
		            
		        	return toDto.listaCuentaToDto(cuentaRepositorio.findByUsuario(usuario));
		            
		        } else if (usuario != null) { 
		            return null;
		        } else {
		            return null; // Devuelve null si no se encuentra el usuario
		        }
		    } catch (Exception e) {
		        //logger.error("Error en registrar: " + e.getMessage(), e);
		        return null; // Devuelve null en caso de excepción
		    }
	}
	
	

}
