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
	
	private static final Logger logger = LoggerFactory.getLogger(UsuarioServicioImpl.class);
	
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
	
	@Override
    public CuentaDAO eliminarCuenta(long id) {
        try {
        	CuentaDAO cuenta = cuentaRepositorio.findById(id);

            if (cuenta != null) {
            	cuentaRepositorio.delete(cuenta);
                logger.info("Cuenta " + cuenta.getNumeroCuenta() + "Eliminado");
            } 
            return cuenta;
        } catch (Exception e) {
            logger.error("Error en eliminarCuenta: " + e.getMessage(), e);
            return null; 
        }
    }
	
	   // Método para buscar una Cuenta por su ID
    @Override
    public CuentaDAO buscarCuentaId(long id) {
        try {
            return cuentaRepositorio.findById(id);
        } catch (Exception e) {
            logger.error("Error en buscarCuentaId: " + e.getMessage(), e);
            return null; 
        }
    }
    
    // Método para obtener una lista de todss lss cuentas en formato DAO
    @Override
    public List<CuentaDAO> listadoCuentaDAO() {
        try {
            return cuentaRepositorio.findAll();
        } catch (Exception e) {
            logger.error("Error en listadoCuentaDAO: " + e.getMessage(), e);
            return null; 
        }
    }
    
 // Método para modificar los detalles de una cuenta
    @Override
    public void modificarCuenta(long id, CuentaDTO cuentaModificado) {
        try {
            // Verificar si la cuenta con el ID proporcionado existe en la base de datos
            CuentaDAO cuenta = cuentaRepositorio.findById(id);
            if (cuenta != null) {
            	
                // Actualizar los campos de la cuenta existente con los nuevos valores
            	cuenta.setConNomina(false);
            	cuenta.setSaldo(cuentaModificado.getSaldo());
            	cuenta.setUsuario(cuenta.getUsuario());
            	
                // Guardar los cambios en la base de datos
            	cuentaRepositorio.save(cuenta);
                logger.info("Cuenta " + cuenta.getNumeroCuenta() +" Fue modificado");
            }
        } catch (Exception e) {
            logger.error("Error en modificarCuenta: " + e.getMessage(), e);
        }
    }

}
