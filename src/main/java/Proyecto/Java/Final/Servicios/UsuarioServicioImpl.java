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

@Service
public class UsuarioServicioImpl implements IUsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private IUsuarioToDao usuarioToDao;
    
    @Autowired
	private IUsuarioToDto usuarioToDto;

    @Override
    public UsuarioDTO registrar(UsuarioDTO usuarioDTO) {
    	try {
			// Comprueba si ya existe un usuario por el Email
			UsuarioDAO usuario = usuarioRepositorio.findByEmail(usuarioDTO.getEmail());

			if (usuario != null) {
				return null;
			}
			
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			
			usuarioDTO.setPassword(passwordEncoder.encode(usuarioDTO.getPassword())); //  encriptar la contraseña
			UsuarioDAO usuarioDao = usuarioToDao.usuarioToDao(usuarioDTO);
			usuarioDao.setTipoUsuario("ROLE_USER");
			usuarioDao.setFch_alta(Calendar.getInstance());
			usuarioRepositorio.save(usuarioDao);

			return usuarioDTO;
		} catch (IllegalArgumentException iae) {
			System.out.println("[Error UsuarioServicioImpl - registrar() ]" + iae.getMessage());
		} catch (Exception e) {
			System.out.println("[Error UsuarioServicioImpl - registrar() ]" + e.getMessage());
		}
		return null;
    }
    
    @Override
	public List<UsuarioDTO> listadoUsuario() {
		return usuarioToDto.listaUsuarioToDto(usuarioRepositorio.findAll());
	}
    
    @Override
	public List<UsuarioDAO> listadoUsuarioDAO() {
		return usuarioRepositorio.findAll();
	}
    
    @Override
	public UsuarioDAO eliminarUsuario(long id) {
		UsuarioDAO usuario = usuarioRepositorio.findById(id);

		if (usuario != null) {
			usuarioRepositorio.delete(usuario);
		} 
		return usuario;
	}
    
    public UsuarioDAO buscarUsuarioId(long id) {
    	return usuarioRepositorio.findById(id);
    }
    
    public void modificarUsuario(long id, UsuarioDTO usuarioModificado) {
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
        }
    }
    
}