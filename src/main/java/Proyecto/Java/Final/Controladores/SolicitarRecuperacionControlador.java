package Proyecto.Java.Final.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import Proyecto.Java.Final.DTO.UsuarioDTO;
import Proyecto.Java.Final.Servicios.IUsuarioServicio;

/**
 * Clase que ejerce de controlador de la vista de solicitarRecuperacion para gestionar las
 * solicitudes relacionadas con el inicio del proceso de recuperación.
 */
@Controller
public class SolicitarRecuperacionControlador {

    @Autowired
    private IUsuarioServicio usuarioServicio;

    /**
     * Gestiona la solicitud HTTP GET para la url /auth/iniciarRecuperacion 
     * y muestra la vista de inicio de recuperación.
     *
     * @param model El modelo en el que se añade como atributo un objeto usuarioDTO vacío para enviarlo a la vista.
     * @return La vista de solicitarRecuperacionPassword.html.
     */
    @GetMapping("/auth/solicitar-recuperacion")
    public String mostrarVistainiciarRecuperacion(Model model) {
        try {
            model.addAttribute("usuarioDTO", new UsuarioDTO());
            return "solicitarRecuperacionPassword";
        } catch (Exception e) {
            model.addAttribute("error", "Error al procesar la solicitud. Por favor, inténtelo de nuevo.");
            return "solicitarRecuperacionPassword";
        }
    }

    /**
     * Procesa la solicitud HTTP POST para la url /auth/iniciarRecuperacion 
     * Se utiliza el email del usuario para intentar iniciar el proceso de recuperación.
     *
     * @param usuarioDTO El objeto UsuarioDTO que recibe del modelo y contiene el email del usuario.
     * @param model      El modelo que se utiliza para enviar mensajes a la vista.
     * @return La vista de login.html si el envío del email fue exitoso; 
     * en caso contrario, la vista de inicio de recuperación.html.
     */
    @PostMapping("/auth/iniciar-recuperacion")
    public String procesarInicioRecuperacion(@ModelAttribute UsuarioDTO usuarioDTO, Model model) {
        try {
            boolean envioConExito = usuarioServicio.iniciarProcesoRecuperacion(usuarioDTO.getEmail());

            if (envioConExito) {
                model.addAttribute("mensajeExitoMail", "Proceso de recuperación OK");
                return "login";
            } else {
                model.addAttribute("mensajeErrorMail", "Error en el proceso de recuperación.");
            }
            return "solicitarRecuperacionPassword";
        } catch (Exception e) {
            model.addAttribute("error", "Error al procesar la solicitud. Por favor, inténtelo de nuevo.");
            return "solicitarRecuperacionPassword";
        }
    }
}
