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
    
    // Método para registrar un nuevo usuario
	@Override
	public UsuarioDTO registrar(UsuarioDTO usuarioDTO) {
		try {
			// Comprueba si ya existe un usuario por el Email
			UsuarioDAO usuario = usuarioRepositorio.findByEmail(usuarioDTO.getEmail());

			// Comprueba si ya existe un usuario con el email que quiere registrar
			if (usuario != null && usuario.isCuentaConfirmada()) {
				logger.info("Usuario ya Confirmado Cuenta Usuario " + usuario.getEmail());
				return usuarioDTO;
			}
			if (usuario != null) { // El email se encuentra registrado sin confirmar
				return null;
			}

			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

			usuarioDTO.setPassword(passwordEncoder.encode(usuarioDTO.getPassword())); // encriptar la contraseña
			UsuarioDAO usuarioDao = usuarioToDao.usuarioToDao(usuarioDTO);
			usuarioDao.setTipoUsuario("ROLE_USER");
			usuarioDao.setFch_alta(Calendar.getInstance());

			if (usuarioDTO.isCuentaConfirmada()) {
				usuarioDao.setCuentaConfirmada(true);
				usuarioRepositorio.save(usuarioDao);
			} else {
				usuarioDao.setCuentaConfirmada(false);
				// Generar token de confirmación
				String token = passwordEncoder.encode(RandomStringUtils.random(30));
				usuarioDao.setToken(token);

				// Guardar el usuario en la base de datos
				usuarioRepositorio.save(usuarioDao);

				// Enviar el correo de confirmación
				String nombreUsuario = usuarioDao.getName();
				emailServicio.enviarEmailConfirmacion(usuarioDTO.getEmail(), nombreUsuario, token);
			}

			usuarioRepositorio.save(usuarioDao);
			logger.info("Usuario " + usuarioDao.getEmail() + " REGISTRADO");
			return usuarioDTO;
		} catch (Exception e) {
			logger.error("Error en registrar: " + e.getMessage(), e);
			return null;
		}
	}
    
    // Método para obtener una lista de todos los usuarios
    @Override
    public List<UsuarioDTO> listadoUsuario() {
        try {
            return usuarioToDto.listaUsuarioToDto(usuarioRepositorio.findAll());
        } catch (Exception e) {
            logger.error("Error en listadoUsuario: " + e.getMessage(), e);
            return null; 
        }
    }
    
    // Método para obtener una lista de todos los usuarios en formato DAO
    @Override
    public List<UsuarioDAO> listadoUsuarioDAO() {
        try {
            return usuarioRepositorio.findAll();
        } catch (Exception e) {
            logger.error("Error en listadoUsuarioDAO: " + e.getMessage(), e);
            return null; 
        }
    }
    
    // Método para eliminar un usuario por su ID
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
    
    // Método para buscar un usuario por su ID
    @Override
    public UsuarioDAO buscarUsuarioId(long id) {
        try {
            return usuarioRepositorio.findById(id);
        } catch (Exception e) {
            logger.error("Error en buscarUsuarioId: " + e.getMessage(), e);
            return null; 
        }
    }
    
    // Método para buscar un usuario por su dirección de correo electrónico
    @Override
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
    
    // Método para modificar los detalles de un usuario
    @Override
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
    
    // Método para obtener un usuario por su token de recuperación de contraseña
    @Override
    public UsuarioDTO obtenerUsuarioPorToken(String token) {
        try {
            UsuarioDAO usuarioExistente = usuarioRepositorio.findByToken(token);
            if (usuarioExistente != null) {
                UsuarioDTO usuario = usuarioToDto.usuarioToDto(usuarioExistente);
                return usuario;
            } else {
                logger.info("No existe el usuario con el token " + token);
                return null;
            }
        } catch (Exception e) {
            logger.error("Error al obtener el token: " + token);
            return null;
        }
    }
    
    // Método para modificar la contraseña de un usuario con un token de recuperación
    @Override
    public boolean modificarContraseñaConToken(UsuarioDTO usuario) {
        try {
            UsuarioDAO usuarioExistente = usuarioRepositorio.findByToken(usuario.getToken());
            if (usuarioExistente != null) {
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String nuevaContraseña = passwordEncoder.encode(usuario.getPassword());
                usuarioExistente.setPassword(nuevaContraseña);
                usuarioExistente.setToken(null); // Se setea a null para invalidar el token ya consumido al cambiar de password
                usuarioRepositorio.save(usuarioExistente);
                logger.info("La contraseña de Usuario " + usuario.getEmail() +" Fue modificada");
                return true;
            }
        } catch (Exception e) {
            logger.error("[Error UsuarioServicioImpl - modificarContraseñaConToken()] Error al modificar el password con el token " + e.getMessage());
        }
        return false;
    }
    
    // Método para iniciar el proceso de recuperación de contraseña para un usuario
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
                logger.info("El usuario con email " + emailUsuario + " no existe");
                return false;
            }
        } catch (IllegalArgumentException iae) {
            logger.error("[Error UsuarioServicioImpl - iniciarProcesoRecuperacion()] Argumento no válido al iniciar el proceso de recuperación" + iae.getMessage());
            return false;
        } catch (PersistenceException e) {
            logger.error("[Error UsuarioServicioImpl - iniciarProcesoRecuperacion()] Error de persistencia al iniciar el proceso de recuperación de contraseña" + e.getMessage());
            return false;
        }
    }
    
    // Método para confirmar la cuenta de un usuario mediante un token de confirmación
    @Override
    public boolean confirmarCuenta(String token) {
        try {
            UsuarioDAO usuarioExistente = usuarioRepositorio.findByToken(token);
            if (usuarioExistente != null && !usuarioExistente.isCuentaConfirmada()) {
                // Entra en esta condición si el usuario existe y su cuenta no se ha confirmado
                usuarioExistente.setCuentaConfirmada(true);
                usuarioExistente.setToken(null);
                usuarioRepositorio.save(usuarioExistente);
                return true;
            } else {
                logger.info("La cuenta no existe o ya está confirmada");
                return false;
            }
        } catch (IllegalArgumentException iae) {
            logger.error("[Error UsuarioServicioImpl - confirmarCuenta()] Error al confirmar la cuenta " + iae.getMessage());
            return false;
        } catch (PersistenceException e) {
            logger.error("[Error UsuarioServicioImpl - confirmarCuenta()] Error de persistencia al confirmar la cuenta" + e.getMessage());
            return false;
        }
    }

    // Método para verificar si la cuenta de un usuario está confirmada
    @Override
    public boolean estaLaCuentaConfirmada(String email) {
        try {
            UsuarioDAO usuarioExistente = usuarioRepositorio.findByEmail(email);
            if (usuarioExistente != null && usuarioExistente.isCuentaConfirmada()) {
                return true;
            }
        } catch (Exception e) {
            logger.error("[Error UsuarioServicioImpl - estaLaCuentaConfirmada()] Error al comprobar si la cuenta ya ha sido confirmada" + e.getMessage());
        }    
        return false;
    }
}
