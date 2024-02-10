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
import jakarta.persistence.PersistenceException;

import org.apache.commons.lang3.RandomStringUtils;
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
    
    @Autowired
	private IEmailServicio emailServicio;

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
    
    public UsuarioDTO buscarUsuarioEmail(String email) {
        try {
        	
        	UsuarioDAO usuario = usuarioRepositorio.findByEmail(email);
        	
        	UsuarioDTO usuarioDto = usuarioToDto.usuarioToDto(usuario);
        	
            return usuarioDto;
            
            
        } catch (Exception e) {
            logger.error("Error en buscarUsuarioEmail: " + e.getMessage(), e);
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
    
    @Override
	public UsuarioDTO obtenerUsuarioPorToken(String token) {
		try {
			UsuarioDAO usuarioExistente = usuarioRepositorio.findByToken(token);

			if (usuarioExistente != null) {
				UsuarioDTO usuario = usuarioToDto.usuarioToDto(usuarioExistente);
				return usuario;
			} else {
				System.out.println("No existe el usuario con el token " + token);
				return null;
			}
		} catch (Exception e) {
			logger.error("Error al obtener el token: " + token);
			System.out.println("[Error UsuarioServicioImpl - obtenerUsuarioPorToken()] Error al obtener usuario por token " + e.getMessage());
			return null;
		}
	}
    
    @Override
	public boolean modificarContraseñaConToken(UsuarioDTO usuario) {
		try {
			UsuarioDAO usuarioExistente = usuarioRepositorio.findByToken(usuario.getToken());

			if (usuarioExistente != null) {
				
				 BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				
				String nuevaContraseña = passwordEncoder.encode(usuario.getPassword());
				usuarioExistente.setPassword(nuevaContraseña);
				usuarioExistente.setToken(null); // Se setea a null para invalidar el token ya consumido al cambiar de
													// password
				usuarioRepositorio.save(usuarioExistente);

				logger.info("La contraseña de Usuario " + usuario.getEmail() +" Fue modificado");
				return true;
			}

		} catch (Exception e) {
			System.out.println("[Error UsuarioServicioImpl - modificarContraseñaConToken()] Error al modificar el password con el token " + e.getMessage());
		}
		return false;
	}
    
    @Override
	public boolean iniciarProcesoRecuperacion(String emailUsuario) {
		try {
			UsuarioDAO usuarioExistente = usuarioRepositorio.findByEmail(emailUsuario);

			if (usuarioExistente != null) {
				 BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				// Generar el token y establece la fecha de expiración
				String token = passwordEncoder.encode(RandomStringUtils.random(30));
				Calendar fechaExpiracion = Calendar.getInstance();
				fechaExpiracion.add(Calendar.MINUTE, 10);
				// Actualizar el usuario con el nuevo token y la fecha de expiración
				usuarioExistente.setToken(token);
				usuarioExistente.setExpiracionToken(fechaExpiracion);

				// Actualizar el usuario en la base de datos
				usuarioRepositorio.save(usuarioExistente);

				// Enviar el correo de recuperación
				String nombreUsuario = usuarioExistente.getName();
				emailServicio.enviarEmailRecuperacion(emailUsuario, nombreUsuario, token);

				return true;

			} else {
				System.out.println("El usuario con email "+ emailUsuario + " no existe");
				return false;
			}
		} catch (IllegalArgumentException iae) {
			System.out.println("[Error UsuarioServicioImpl - iniciarProcesoRecuperacion()] Argumento no valido al iniciar el proceso de recuperación" + iae.getMessage());
			return false;
		} catch (PersistenceException e) {
			System.out.println("[Error UsuarioServicioImpl - iniciarProcesoRecuperacion()] Error de persistencia al iniciar el proceso de recuperación de contraseña" + e.getMessage());
			return false;
		}
	}
    
}
