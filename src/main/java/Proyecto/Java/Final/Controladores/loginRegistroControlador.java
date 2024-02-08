package Proyecto.Java.Final.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import Proyecto.Java.Final.DTO.UsuarioDTO;
import Proyecto.Java.Final.Servicios.IUsuarioServicio;

@Controller
public class loginRegistroControlador {

	@Autowired
	private IUsuarioServicio usuarioServicio;
	
	@GetMapping("/auth/registrar")
	public String registrarGet(Model model) {
		model.addAttribute("usuarioDTO", new UsuarioDTO());
		return "registro";
	}
	
	@GetMapping("/auth/login")
	public String loginGet(Model model) {
		model.addAttribute("usuarioDTO", new UsuarioDTO());
		return "login";
	}
	
	@PostMapping("/auth/registrar")
	public String registrarPost(@ModelAttribute UsuarioDTO usuarioDTO, Model model) {

		UsuarioDTO nuevoUsuario = usuarioServicio.registrar(usuarioDTO);

		System.out.println(nuevoUsuario.getDni());
		
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
	}
	
	@GetMapping("/privada/home")
	public String loginCorrecto(Model model, Authentication authentication) {
		model.addAttribute("nombreUsuario", authentication.getName());
		System.out.println(authentication.getAuthorities());
		return "home";
	}
}