package Proyecto.Java.Final.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import Proyecto.Java.Final.DTO.UsuarioDTO;
import Proyecto.Java.Final.Servicios.IUsuarioServicio;

@Controller
public class SolicitarRecuperacionControlador {

    @Autowired
    private IUsuarioServicio usuarioServicio;

    // Método para mostrar la vista de inicio de recuperación de contraseña
    @GetMapping("/auth/solicitar-recuperacion")
    public String mostrarVistainiciarRecuperacion(Model model) {
        try {
            // Agrega un nuevo objeto UsuarioDTO al modelo para vincular con el formulario de inicio de recuperación
            model.addAttribute("usuarioDTO", new UsuarioDTO());
            return "solicitarRecuperacionPassword";
        } catch (Exception e) {
            // Manejo de errores
            model.addAttribute("error", "Error al procesar la solicitud. Por favor, inténtelo de nuevo.");
            return "solicitarRecuperacionPassword";
        }
    }

    // Método para procesar la solicitud de inicio de recuperación de contraseña
    @PostMapping("/auth/iniciar-recuperacion")
    public String procesarInicioRecuperacion(@ModelAttribute UsuarioDTO usuarioDTO, Model model) {
        try {
            // Intenta iniciar el proceso de recuperación utilizando el correo electrónico proporcionado
            boolean envioConExito = usuarioServicio.iniciarProcesoRecuperacion(usuarioDTO.getEmail());

            if (envioConExito) {
                // Si el proceso de recuperación se inicia correctamente, muestra un mensaje de éxito y redirige a la vista de inicio de sesión
                model.addAttribute("mensajeExitoMail", "Proceso de recuperación OK");
                return "login";
            } else {
                // Si hay un error en el proceso de recuperación, muestra un mensaje de error y vuelve a la vista de inicio de recuperación
                model.addAttribute("mensajeErrorMail", "Error en el proceso de recuperación.");
            }
            return "solicitarRecuperacionPassword";
        } catch (Exception e) {
            // Manejo de errores
            model.addAttribute("error", "Error al procesar la solicitud. Por favor, inténtelo de nuevo.");
            return "solicitarRecuperacionPassword";
        }
    }
}
