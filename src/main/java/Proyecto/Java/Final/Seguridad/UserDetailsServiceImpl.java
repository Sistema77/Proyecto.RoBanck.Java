package Proyecto.Java.Final.Seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import Proyecto.Java.Final.DAO.UsuarioDAO;
import Proyecto.Java.Final.Repositorio.UsuarioRepositorio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepository;
    
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.printf("\nIntento de inicio de sesión para el usuario: %s\n", username);
        
        try {
            // El nombre de usuario en la aplicación es el email
            UsuarioDAO user = usuarioRepository.findByEmail(username);
            
            // Construir la instancia de UserDetails con los datos del usuario
            UserBuilder builder = null;
            
            if (user != null) {
                System.out.printf("\nUsuario encontrado en la base de datos: %s\n", user.getEmail());
    
                builder = User.withUsername(username);
                builder.disabled(false);
                builder.password(user.getPassword());
                builder.authorities(new SimpleGrantedAuthority(user.getTipoUsuario()));
            } else { 
                System.out.println("Usuario no encontrado en la base de datos");
                throw new UsernameNotFoundException("Usuario no encontrado");
            }
            return builder.build();
        } catch (Exception e) {
            logger.error("Error en loadUserByUsername: " + e.getMessage(), e);
            throw new UsernameNotFoundException("Error en autenticación");
        }
    }
}
