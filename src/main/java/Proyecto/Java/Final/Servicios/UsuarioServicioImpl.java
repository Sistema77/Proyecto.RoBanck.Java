package Proyecto.Java.Final.Servicios;

import java.util.Calendar;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
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

    @Override
    public UsuarioDTO registrar(UsuarioDTO usuarioDTO) {
    	try {
			// Comprueba si ya existe un usuario por el DNI
			UsuarioDAO usuarioDNI = usuarioRepositorio.findByDni(usuarioDTO.getDni());

			if (usuarioDNI != null) {
				return null;
			}
			
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			
			usuarioDTO.setPassword(passwordEncoder.encode(usuarioDTO.getPassword())); //  encriptar la contraseña
			UsuarioDAO usuarioDao = usuarioToDao.usuarioToDao(usuarioDTO);
			usuarioDao.setTipoUsuario("USER");
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
}