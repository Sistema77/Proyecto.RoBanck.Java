package Proyecto.Java.Final.Controladores;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Proyecto.Java.Final.DTO.UsuarioDTO;
import Proyecto.Java.Final.Servicios.IUsuarioServicio;

@Controller
public class RecuperarPasswordControlador {

    @Autowired
    private IUsuarioServicio usuarioServicio;

    // Método para mostrar la vista de recuperación de contraseña
    @GetMapping("/auth/recuperar")
    public String mostrarVistaRecuperar(@RequestParam(name = "token") String token, Model model) {
        try {
            // Obtiene el usuario por el token proporcionado
            UsuarioDTO usuario = usuarioServicio.obtenerUsuarioPorToken(token);
            if (usuario != null) {
                // Si se encuentra el usuario, lo agrega al modelo y muestra la vista de recuperación de contraseña
                model.addAttribute("usuarioDTO", usuario);
            } else {
                // Si no se encuentra el usuario, muestra un mensaje de error y redirige a la vista de solicitud de recuperación de contraseña
                model.addAttribute("mensajeErrorTokenValidez", "El enlace de recuperación no válido o usuario no encontrado");
                return "solicitarRecuperacionPassword";
            }
            return "recuperar";
        } catch (Exception e) {
            // Manejo de errores
            model.addAttribute("error", "Error al mostrar la vista de recuperar");
            return "solicitarRecuperacionPassword";
        }
    }

    // Método para procesar la solicitud de recuperación de contraseña
    @PostMapping("/auth/recuperar")
    public String procesarRecuperacionContraseña(@ModelAttribute UsuarioDTO usuarioDTO, Model model) {
        try {
            // Obtiene el usuario existente por el token proporcionado
            UsuarioDTO usuarioExistente = usuarioServicio.obtenerUsuarioPorToken(usuarioDTO.getToken());

            if (usuarioExistente == null) {
                // Si el usuario no existe, muestra un mensaje de error y redirige a la vista de solicitud de recuperación de contraseña
                model.addAttribute("mensajeErrorTokenValidez", "El enlace de recuperación no válido");
                return "solicitarRecuperacionPassword";
            }
            if (usuarioExistente.getExpiracionToken().before(Calendar.getInstance())) {
                // Si el token ha expirado, muestra un mensaje de error y redirige a la vista de solicitud de recuperación de contraseña
                model.addAttribute("mensajeErrorTokenExpirado", "El enlace de recuperación ha expirado");
                return "solicitarRecuperacionPassword";
            }

            // Intenta modificar la contraseña utilizando el token proporcionado
            boolean modificadaPassword = usuarioServicio.modificarContraseñaConToken(usuarioDTO);

            if (modificadaPassword) {
                // Si la contraseña se modificó correctamente, muestra un mensaje de éxito y redirige a la vista de inicio de sesión
                model.addAttribute("contraseñaModificadaExito", "Contraseña modificada OK");
                return "login";
            } else {
                // Si hay un error al cambiar la contraseña, muestra un mensaje de error y redirige a la vista de solicitud de recuperación de contraseña
                model.addAttribute("contraseñaModificadaError", "Error al cambiar de contraseña");
                return "solicitarRecuperacionPassword";
            }
        } catch (Exception e) {
            // Manejo de errores
            model.addAttribute("error", "Error al procesar la solicitud recuperar");
            return "solicitarRecuperacionPassword";
        }
    }

}
