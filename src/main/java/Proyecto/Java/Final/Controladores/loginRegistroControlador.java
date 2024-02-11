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
    
    // Método para mostrar el formulario de registro
    @GetMapping("/auth/registrar")
    public String registrarGet(Model model) {
        try {
            // Agrega un nuevo objeto UsuarioDTO al modelo para vincular con el formulario de registro
            model.addAttribute("usuarioDTO", new UsuarioDTO());
            return "registro";
        } catch (Exception e) {
            logger.error("Error en registrarGet: " + e.getMessage(), e);
            return "registro"; 
        }
    }
    
    // Método para mostrar el formulario de inicio de sesión
    @GetMapping("/auth/login")
    public String loginGet(Model model) {
        try {
            // Agrega un nuevo objeto UsuarioDTO al modelo para vincular con el formulario de inicio de sesión
            model.addAttribute("usuarioDTO", new UsuarioDTO());
            return "login";
        } catch (Exception e) {
            logger.error("Error en loginGet: " + e.getMessage(), e);
            return "login"; 
        }
    }
    
    // Método para procesar la solicitud de registro
    @PostMapping("/auth/registrar")
    public String registrarPost(@RequestParam("profilePicture") MultipartFile file,@ModelAttribute UsuarioDTO usuarioDTO, Model model) {
        try {
            // Convierte la imagen de perfil a un arreglo de bytes y lo asigna al DTO del usuario
            usuarioDTO.setFoto(ImagenBinario.convertMultipartFileToByteArray(file));
            
            // Registra al nuevo usuario utilizando el servicio de usuario
            UsuarioDTO nuevoUsuario = usuarioServicio.registrar(usuarioDTO);
            
            if (nuevoUsuario != null && nuevoUsuario.getEmail() != null) {
                // Si el registro fue exitoso, muestra un mensaje de éxito y redirige al formulario de inicio de sesión
                model.addAttribute("mensajeRegistroExitoso", "Registro del nuevo usuario OK");
                return "login";
            } else {
                // Si el registro falla, verifica si el correo electrónico o el DNI ya existen y muestra mensajes de error adecuados
                if (usuarioDTO.getDni() == null) {
                    model.addAttribute("mensajeErrorDni", "Ya existe un usuario con ese DNI");
                    return "registro";
                } else {
                    model.addAttribute("mensajeErrorMail", "Ya existe un usuario con ese email");
                    return "registro";
                }
            }
        } catch (Exception e) {
            // Manejo de errores
            logger.error("Error en registrarPost: " + e.getMessage(), e);
            return "registro"; 
        }
    }
    
    // Método para mostrar la página de inicio después de un inicio de sesión exitoso
    @GetMapping("/privada/home")
    public String loginCorrecto(Model model, Authentication authentication) {
        try {
            // Agrega el nombre de usuario al modelo
            model.addAttribute("nombreUsuario", authentication.getName());
            
            // Verifica si la cuenta del usuario está confirmada
            boolean cuentaConfirmada = usuarioServicio.estaLaCuentaConfirmada(authentication.getName());
            
            if (cuentaConfirmada) {
                // Si la cuenta está confirmada, recupera y muestra la foto de perfil del usuario
                byte[] fotoby = usuarioServicio.buscarUsuarioEmail(authentication.getName()).getFoto();
                String foto = ImagenBinario.pasarBinarioAString(fotoby);
                model.addAttribute("foto", foto);
                return "home";
            } else {
                // Si la cuenta no está confirmada, redirige al formulario de inicio de sesión
                return "login";
            }
        } catch (Exception e) {
            // Manejo de errores
            logger.error("Error en loginCorrecto: " + e.getMessage(), e);
            return "login"; 
        }
    }

    // Método para confirmar la cuenta del usuario utilizando un token
    @GetMapping("/auth/confirmar-cuenta")
    public String confirmarCuenta(Model model, @RequestParam("token") String token) {
        try {
            // Intenta confirmar la cuenta utilizando el token proporcionado
            boolean confirmacionExitosa = usuarioServicio.confirmarCuenta(token);

            if (confirmacionExitosa) {
                // Si la confirmación es exitosa, muestra un mensaje de éxito
                model.addAttribute("cuentaVerificada", "Su dirección de correo ha sido confirmada correctamente");
            } else {
                // Si la confirmación falla, muestra un mensaje de error
                model.addAttribute("cuentaNoVerificada", "Error al confirmar su email");
            }

            return "login";
        } catch (Exception e) {
            // Manejo de errores
            model.addAttribute("error", "Error al procesar la solicitud. Por favor, inténtelo de nuevo.");
            return "login";
        }
    }
    
}
