package Proyecto.Java.Final.Servicios;

import java.util.Calendar;
import java.util.List;


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
    
}