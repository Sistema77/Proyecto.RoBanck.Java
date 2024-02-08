package Proyecto.Java.Final.Controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import Proyecto.Java.Final.DAO.UsuarioDAO;
import Proyecto.Java.Final.DTO.UsuarioDTO;
import Proyecto.Java.Final.Servicios.IUsuarioServicio;
import Proyecto.Java.Final.Servicios.IUsuarioToDto;
import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class AdministradorControlador {
	
	@Autowired
	private IUsuarioServicio usuarioServicio;
	
	@Autowired
	private IUsuarioToDto usuarioToDto;
	
	private static final Logger logger = LoggerFactory.getLogger(AdministradorControlador.class);
	
	@GetMapping("/privada/administracion")
	public String listadoUsuarios(Model model, HttpServletRequest request,Authentication authentication) {
		try {
            if(request.isUserInRole("ROLE_ADMIN")) {
                
                List<UsuarioDTO> usuarios = usuarioServicio.listadoUsuario();
                
                System.out.println(usuarios);
                
                model.addAttribute("usuarios", usuarios);
                
                return "administracion";    
            } 
            
            model.addAttribute("noAdmin", "No eres admin");
            model.addAttribute("name", authentication.getName());
            
            return "home";
        } catch (Exception e) {
            logger.error("Error en listadoUsuarios: " + e.getMessage(), e);
            return "home"; 
        }
	}
	
	@PostMapping("/privada/eliminar/{id}")
	public String eliminarUsuario(@PathVariable long id, Model model, HttpServletRequest request) {
		
		 try {
	            UsuarioDAO usuario = usuarioServicio.buscarUsuarioId(id);
	            List<UsuarioDAO> usuarios = usuarioServicio.listadoUsuarioDAO();
	            
	            if(request.isUserInRole("ROLE_ADMIN") && usuario.getTipoUsuario().equals("ROLE_ADMIN")) {
	                
	                model.addAttribute("usuarios", usuarios);
	                return "administracion";
	            }
	            
	            usuarioServicio.eliminarUsuario(id);
	            return "redirect:/privada/administracion";
	        } catch (Exception e) {
	            logger.error("Error en eliminarUsuario: " + e.getMessage(), e);
	            return "redirect:/privada/administracion"; 
	        }
		
	}
	
	 // Método para mostrar el formulario de modificación de usuario
	@PostMapping("/privada/modificar/{id}")
    public String mostrarFormularioModificar(@PathVariable long id, Model model) {
		 try {
	            UsuarioDAO usuarioDto = usuarioServicio.buscarUsuarioId(id);
	            UsuarioDTO usuarioDao = usuarioToDto.usuarioToDto(usuarioDto);
	            
	            model.addAttribute("usuario", usuarioDao);
	            
	            return "editar";
	        } catch (Exception e) {
	            logger.error("Error en mostrarFormularioModificar: " + e.getMessage(), e);
	            return "editar"; 
	        }
    }
    
    // Método para procesar la solicitud de modificación de usuario
    @PostMapping("/privada/modificar/usuario/{id}")
    public String modificarUsuario(@PathVariable long id, @ModelAttribute UsuarioDTO usuarioModificado) {
    	 try {
             // Actualizar los datos del usuario
             usuarioServicio.modificarUsuario(id, usuarioModificado);
             return "redirect:/privada/administracion";
         } catch (Exception e) {
             logger.error("Error en modificarUsuario: " + e.getMessage(), e);
             return "redirect:/privada/administracion"; 
         }
    }
}
