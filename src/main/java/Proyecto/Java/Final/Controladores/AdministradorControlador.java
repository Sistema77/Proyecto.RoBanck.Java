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
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AdministradorControlador {
	
	@Autowired
	private IUsuarioServicio usuarioServicio;
	
	@GetMapping("/privada/administracion")
	public String listadoUsuarios(Model model, HttpServletRequest request,Authentication authentication) {
		
		if(request.isUserInRole("ROLE_ADMIN")) {
			
			List<UsuarioDTO> usuarios = usuarioServicio.listadoUsuario();
			
			System.out.println(usuarios);
			
			model.addAttribute("usuarios", usuarios);
			
			return "administracion";	
		} 
		
		model.addAttribute("noAdmin", "No eres admin");
		model.addAttribute("name", authentication.getName());
		
		return "home";
	}
	
	
	@PostMapping("/privada/eliminar/{id}")
	public String eliminarUsuario(@PathVariable long id, Model model, HttpServletRequest request) {
		
		/////////////////////////////////////
		System.out.println("Buscando Usuario");
		UsuarioDAO usuario = usuarioServicio.buscarUsuarioId(id);
		List<UsuarioDAO> usuarios = usuarioServicio.listadoUsuarioDAO();
		
		if(request.isUserInRole("ROLE_ADMIN") && usuario.getTipoUsuario().equals("ROLE_ADMIN")) {
			///////////////////////////////
			System.out.println("No es ADMIN");
			
			model.addAttribute("usuarios", usuarios);
			return "administracion";
		}
		
		usuarioServicio.eliminarUsuario(id);
		return "redirect:/privada/administracion";
		
	}
	
	
}
