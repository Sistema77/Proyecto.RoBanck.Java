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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
@EnableMethodSecurity
public class SeguridadConfig {

    @Autowired
    private UserDetailsService userDetailsService;
    
    private static final Logger logger = LoggerFactory.getLogger(SeguridadConfig.class);
    
    // Configuración del codificador de contraseñas BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configuración del administrador de autenticación
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) {
        try {
            return authConfig.getAuthenticationManager();
        } catch (Exception e) {
            // Manejo de errores
            logger.error("Error en authenticationManager: " + e.getMessage(), e);
            return null; 
        }
    }
    
    // Configuración del proveedor de autenticación DAO
    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        try {
            DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
            authProvider.setUserDetailsService(userDetailsService);
            authProvider.setPasswordEncoder(passwordEncoder());
           
            return authProvider;
        } catch (Exception e) {
            // Manejo de errores
            logger.error("Error en authenticationProvider: " + e.getMessage(), e);
            return null; 
        }
    }
    
    // Configuración del filtro de seguridad
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) {
        try {
            http
                .authorizeHttpRequests(auth -> 
                    auth
                        // Configura las rutas que están permitidas para todos los usuarios y las que requieren autenticación
                        .requestMatchers("/", "/webjars/**", "/css/**", "/script/**", "/auth/**").permitAll()
                        .anyRequest().authenticated()
                )
                // Configura el formulario de inicio de sesión
                .formLogin(login ->
                    login
                        .loginPage("/auth/login")
                        .defaultSuccessUrl("/privada/home", true)
                        .loginProcessingUrl("/auth/login-post")
                )
                // Configura la funcionalidad de cierre de sesión
                .logout(logout ->
                    logout
                        .logoutUrl("/auth/logout")
                        .logoutSuccessUrl("/auth/login")
                );
            
            // Configura el proveedor de autenticación personalizado
            http.authenticationProvider(authenticationProvider());
    
            return http.build();
        } catch (Exception e) {
            // Manejo de errores
            logger.error("Error en filterChain: " + e.getMessage(), e);
            return null; 
        }
    }
}
