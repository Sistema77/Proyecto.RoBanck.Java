package Proyecto.Java.Final.Servicios;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import Proyecto.Java.Final.DAO.UsuarioDAO;
import Proyecto.Java.Final.DTO.UsuarioDTO;
import Proyecto.Java.Final.Repositorio.UsuarioRepositorio;
import Proyecto.Java.Final.Util.ImagenBinario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UsuarioServicioImpl implements IUsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private IUsuarioToDao usuarioToDao;
    
    @Autowired
	private IUsuarioToDto usuarioToDto;

    private static final Logger logger = LoggerFactory.getLogger(UsuarioServicioImpl.class);
    
    @Override
    public UsuarioDTO registrar(UsuarioDTO usuarioDTO) {
        try {
            // Comprueba si ya existe un usuario por el Email
            UsuarioDAO usuario = usuarioRepositorio.findByEmail(usuarioDTO.getEmail());
        	
            if (usuario != null) {
                return null;
            }
            
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            
            usuarioDTO.setPassword(passwordEncoder.encode(usuarioDTO.getPassword())); // encriptar la contraseña
            UsuarioDAO usuarioDao = usuarioToDao.usuarioToDao(usuarioDTO);
            usuarioDao.setTipoUsuario("ROLE_USER");
            usuarioDao.setFch_alta(Calendar.getInstance());
            
            System.out.println("DAO Foto: " + usuarioDao.getFoto());
            
            usuarioRepositorio.save(usuarioDao);
            logger.info("Usuario " + usuarioDao.getEmail() + " REGISTRADO");
            return usuarioDTO;
        } catch (Exception e) {
            logger.error("Error en registrar: " + e.getMessage(), e);
            return null; 
        }
    }
    
    @Override
    public List<UsuarioDTO> listadoUsuario() {
        try {
            return usuarioToDto.listaUsuarioToDto(usuarioRepositorio.findAll());
        } catch (Exception e) {
            logger.error("Error en listadoUsuario: " + e.getMessage(), e);
            return null; 
        }
    }
    
    @Override
    public List<UsuarioDAO> listadoUsuarioDAO() {
        try {
            return usuarioRepositorio.findAll();
        } catch (Exception e) {
            logger.error("Error en listadoUsuarioDAO: " + e.getMessage(), e);
            return null; 
        }
    }
    
    @Override
    public UsuarioDAO eliminarUsuario(long id) {
        try {
            UsuarioDAO usuario = usuarioRepositorio.findById(id);

            if (usuario != null) {
                usuarioRepositorio.delete(usuario);
                logger.info("Usuario " + usuario.getEmail() + "Eliminado");
            } 
            return usuario;
        } catch (Exception e) {
            logger.error("Error en eliminarUsuario: " + e.getMessage(), e);
            return null; 
        }
    }
    
    public UsuarioDAO buscarUsuarioId(long id) {
        try {
            return usuarioRepositorio.findById(id);
        } catch (Exception e) {
            logger.error("Error en buscarUsuarioId: " + e.getMessage(), e);
            return null; 
        }
    }
    
    public void modificarUsuario(long id, UsuarioDTO usuarioModificado) {
        try {
            // Verificar si el usuario con el ID proporcionado existe en la base de datos
            UsuarioDAO usuario = usuarioRepositorio.findById(id);
            if (usuario != null) {
                
                // Actualizar los campos del usuario existente con los nuevos valores
                usuario.setDni(usuarioModificado.getDni());
                usuario.setEmail(usuarioModificado.getEmail());
                usuario.setFoto(usuarioModificado.getFoto());
                usuario.setLastName(usuarioModificado.getLastName());
                usuario.setName(usuarioModificado.getName());
                usuario.setTlf(usuarioModificado.getTlf());
             
                // Guardar los cambios en la base de datos
                usuarioRepositorio.save(usuario);
                logger.info("Usuario " + usuario.getEmail() +" Fue modificado");
            }
        } catch (Exception e) {
            logger.error("Error en modificarUsuario: " + e.getMessage(), e);
        }
    }
    
}
