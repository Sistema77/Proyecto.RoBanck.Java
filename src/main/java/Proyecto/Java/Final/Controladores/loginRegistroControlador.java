package Proyecto.Java.Final.Controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import Proyecto.Java.Final.DTO.UsuarioDTO;

@Controller
@RequestMapping("/auth")
public class loginRegistroControlador {

	@GetMapping("/registrar")
	public String registrarGet(Model model) {
		model.addAttribute("usuarioDTO", new UsuarioDTO());
		return "registro";
	}
	
	@GetMapping("/login")
	public String loginGet(Model model) {
		model.addAttribute("usuarioDTO", new UsuarioDTO());
		return "login";
	}
	
	@PostMapping("/auth/registrar")
	public String registrarPost(@ModelAttribute UsuarioDTO usuarioDTO, Model model) {

		UsuarioDTO nuevoUsuario = usuarioServicio.registrar(usuarioDTO);
		
		if (nuevoUsuario != null && nuevoUsuario.getDniUsuario() != null) {
			// Si el usuario y el DNI no son null es que el registro se completo correctamente
			model.addAttribute("mensajeRegistroExitoso", "Registro del nuevo usuario OK");
			return "login";
		} else {
			// Se verifica si el DNI ya existe para mostrar error personalizado en la vista
			if (usuarioDTO.getDniUsuario() == null) {
				model.addAttribute("mensajeErrorDni", "Ya existe un usuario con ese DNI");
				return "registro";
			} else {
				model.addAttribute("mensajeErrorMail", "Ya existe un usuario con ese email");
				return "registro";
			}
		}
}
