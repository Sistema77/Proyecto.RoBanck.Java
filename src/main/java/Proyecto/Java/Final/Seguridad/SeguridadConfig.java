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
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) {
        try {
            return authConfig.getAuthenticationManager();
        } catch (Exception e) {
            logger.error("Error en authenticationManager: " + e.getMessage(), e);
            return null; 
        }
    }
    
    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        try {
            DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
            authProvider.setUserDetailsService(userDetailsService);
            authProvider.setPasswordEncoder(passwordEncoder());
           
            return authProvider;
        } catch (Exception e) {
            logger.error("Error en authenticationProvider: " + e.getMessage(), e);
            return null; 
        }
    }
    
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) {
        try {
            http
                .authorizeHttpRequests(auth -> 
                    auth
                        .requestMatchers("/", "/webjars/**", "/css/**", "/script/**", "/auth/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(login ->
                    login
                        .loginPage("/auth/login")
                        .defaultSuccessUrl("/privada/home", true)
                        .loginProcessingUrl("/auth/login-post")
                )
                .logout(logout ->
                    logout
                        .logoutUrl("/auth/logout")
                        .logoutSuccessUrl("/auth/login")
                );
            
            http.authenticationProvider(authenticationProvider());
    
            return http.build();
        } catch (Exception e) {
            logger.error("Error en filterChain: " + e.getMessage(), e);
            return null; 
        }
    }
}
