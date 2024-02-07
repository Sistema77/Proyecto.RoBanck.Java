package Proyecto.Java.Final.Seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Clase de configuración donde se detallan los filtro de seguridad para Spring Security
 * en la que se delega la autenticación y la autorización.
 */
@Configuration
@EnableMethodSecurity
public class SeguridadConfig {

	@Autowired
    private UserDetailsService userDetailsService;

    /**
     * Configura el codificador de contraseñas utilizado para encriptar y comparar contraseñas.
     * @return Instancia del codificador de contraseñas BCrypt.
     */


}
