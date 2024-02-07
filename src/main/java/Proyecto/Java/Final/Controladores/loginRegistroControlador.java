package Proyecto.Java.Final.Controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
}
