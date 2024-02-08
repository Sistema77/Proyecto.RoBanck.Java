package Proyecto.Java.Final.Controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import Proyecto.Java.Final.DTO.UsuarioDTO;
import Proyecto.Java.Final.Servicios.IUsuarioServicio;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AdministradorControlador {
	
	@Autowired
	private IUsuarioServicio usuarioServicio;
	
	@GetMapping("/privada/administracion")
	public String listadoUsuarios(Model model, HttpServletRequest request,Authentication authentication) {
		
		//if(request.isUserInRole("ADMIN")) {
			
			//List<UsuarioDTO> usuarios = usuarioServicio.listadoUsuario();
			
			//System.out.println(usuarios);
			
			//model.addAttribute("usuarios", usuarios);
			
			return "administracion";	
		//} 
		
		//model.addAttribute("noAdmin", "No eres admin");
		//model.addAttribute("nombreUsuario", authentication.getName());
		
		//return "home";
	}
}
