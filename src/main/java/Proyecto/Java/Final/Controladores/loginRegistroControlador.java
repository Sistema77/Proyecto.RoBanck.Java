package Proyecto.Java.Final.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import Proyecto.Java.Final.DTO.UsuarioDTO;
import Proyecto.Java.Final.Servicios.IUsuarioServicio;
import Proyecto.Java.Final.Util.ImagenBinario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class loginRegistroControlador {

    @Autowired
    private IUsuarioServicio usuarioServicio;
    
    private static final Logger logger = LoggerFactory.getLogger(loginRegistroControlador.class);
    
    @GetMapping("/auth/registrar")
    public String registrarGet(Model model) {
        try {
            model.addAttribute("usuarioDTO", new UsuarioDTO());
            return "registro";
        } catch (Exception e) {
            logger.error("Error en registrarGet: " + e.getMessage(), e);
            return "registro"; 
        }
    }
    
    @GetMapping("/auth/login")
    public String loginGet(Model model) {
        try {
            model.addAttribute("usuarioDTO", new UsuarioDTO());
            return "login";
        } catch (Exception e) {
            logger.error("Error en loginGet: " + e.getMessage(), e);
            return "login"; 
        }
    }
    
    @PostMapping("/auth/registrar")
    public String registrarPost(@RequestParam("profilePicture") MultipartFile file,@ModelAttribute UsuarioDTO usuarioDTO, Model model) {
        try {
        	
        	
        	usuarioDTO.setFoto(ImagenBinario.convertMultipartFileToByteArray(file));
        	
        	/////////////////////////////////////////
        	System.out.println("Foto del Objeto: " );
        	System.out.println(usuarioDTO.getFoto());
        	
            UsuarioDTO nuevoUsuario = usuarioServicio.registrar(usuarioDTO);
            
            if (nuevoUsuario != null && nuevoUsuario.getEmail() != null) {
                // Si el usuario y el Email no son null es que el registro se completo correctamente
                model.addAttribute("mensajeRegistroExitoso", "Registro del nuevo usuario OK");
                return "login";
            } else {
                // Se verifica si el Email ya existe para mostrar error personalizado en la vista
                if (usuarioDTO.getDni() == null) {
                    model.addAttribute("mensajeErrorDni", "Ya existe un usuario con ese DNI");
                    return "registro";
                } else {
                    model.addAttribute("mensajeErrorMail", "Ya existe un usuario con ese email");
                    return "registro";
                }
            }
        } catch (Exception e) {
            logger.error("Error en registrarPost: " + e.getMessage(), e);
            return "registro"; 
        }
    }
    
    @GetMapping("/privada/home")
    public String loginCorrecto(Model model, Authentication authentication) {
        try {
            model.addAttribute("nombreUsuario", authentication.getName());
            System.out.println(authentication.getAuthorities());
            return "home";
        } catch (Exception e) {
            logger.error("Error en loginCorrecto: " + e.getMessage(), e);
            return "login"; 
        }
    }
}
