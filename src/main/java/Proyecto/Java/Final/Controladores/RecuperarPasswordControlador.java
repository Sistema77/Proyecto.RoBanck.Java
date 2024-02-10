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


/**
 * Clase que ejerce de controlador de la vista de recuperación de contraseña (recuperar.html) para gestionar
 * las solicitudes relacionadas con la recuperación de contraseña.
 */
@Controller
public class RecuperarPasswordControlador {

    @Autowired
    private IUsuarioServicio usuarioServicio;

    /**
     * Gestiona la solicitud HTTP GET para la url /auth/recuperar.
     * Muestra la vista de recuperación de contraseña y envía a esta en el modelo el dto con el token
     * asociado al usuario para recuperar la contraseña, o en caso de no encontrarlo, mostrar un mensaje de error.
     *
     * @param token El token necesario para recuperar la contraseña obtenido de la url de la solicitud.
     * @param model El modelo que se utiliza para enviar mensajes y datos en el modelo a la vista.
     * @return La vista de recuperación de contraseña (recuperar.html) si el token es válido;
     * de lo contrario, la vista de inicioRecuperacion.html
     */
    @GetMapping("/auth/recuperar")
    public String mostrarVistaRecuperar(@RequestParam(name = "token") String token, Model model) {
        try {
            UsuarioDTO usuario = usuarioServicio.obtenerUsuarioPorToken(token);
            if (usuario != null) {
                model.addAttribute("usuarioDTO", usuario);
            } else {
                model.addAttribute("mensajeErrorTokenValidez", "El enlace de recuperación no válido o usuario no encontrado");
                return "solicitarRecuperacionPassword";
            }
            return "recuperar";
        } catch (Exception e) {
            model.addAttribute("error", "Error al mostrar la vista de recuperar");
            return "solicitarRecuperacionPassword";
        }
    }

    /**
     * Procesa la solicitud HTTP POST para la url /auth/recuperar.
     * Se utiliza el token del usuario para comprobar la validez e intentar recuperar la contraseña.
     *
     * @param usuarioDTO El objeto UsuarioDTO que recibe del modelo y contiene los nuevos datos de la contraseña.
     * @param model      El modelo que se utiliza para enviar mensajes a la vista.
     * @return La vista de login.html si la modificación fue exitosa; de lo contrario, la vista de iniciarRecuperación.html
     */
    @PostMapping("/auth/recuperar")
    public String procesarRecuperacionContraseña(@ModelAttribute UsuarioDTO usuarioDTO, Model model) {
        try {
            UsuarioDTO usuarioExistente = usuarioServicio.obtenerUsuarioPorToken(usuarioDTO.getToken());

            if (usuarioExistente == null) {
                model.addAttribute("mensajeErrorTokenValidez", "El enlace de recuperación no válido");
                return "solicitarRecuperacionPassword";
            }
            if (usuarioExistente.getExpiracionToken().before(Calendar.getInstance())) {
                model.addAttribute("mensajeErrorTokenExpirado", "El enlace de recuperación ha expirado");
                return "solicitarRecuperacionPassword";
            }

            boolean modificadaPassword = usuarioServicio.modificarContraseñaConToken(usuarioDTO);

            if (modificadaPassword) {
                model.addAttribute("contraseñaModificadaExito", "Contraseña modificada OK");
                return "login";
            } else {
                model.addAttribute("contraseñaModificadaError", "Error al cambiar de contraseña");
                return "solicitarRecuperacionPassword";
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error al procesar la solicitud recuperar");
            return "solicitarRecuperacionPassword";
        }
    }

}
