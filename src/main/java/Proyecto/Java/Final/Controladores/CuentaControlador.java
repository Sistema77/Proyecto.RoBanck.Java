package Proyecto.Java.Final.Controladores;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import Proyecto.Java.Final.DTO.CuentaDTO;
import Proyecto.Java.Final.Servicios.ICuentaServicio;
import Proyecto.Java.Final.Servicios.IUsuarioServicio;
import Proyecto.Java.Final.Util.ImagenBinario;

@Controller
public class CuentaControlador {

	 @Autowired
	 private ICuentaServicio cuentaServicio;
	
	 private static final Logger logger = LoggerFactory.getLogger(CuentaControlador.class);
	 
	 
	 @GetMapping("/privada/cuenta")
	    public String CuentaGet(Model model, Authentication authentication) {
	        try {
	            // Agrega el nombre de usuario al modelo
	            model.addAttribute("nombreUsuario", authentication.getName());
	          
	            return "cuenta"; 
	        } catch (Exception e) {
	            // Manejo de errores
	            logger.error("Error en CuentaGet: " + e.getMessage(), e);
	            return "login"; 
	        }
	    }
	 
	 @GetMapping("/privada/crear-cuenta")
	 public String crearCuenta(Model model, Authentication authentication) {
		 try {
			 model.addAttribute("nombreUsuario", authentication.getName());
			 
			 cuentaServicio.crearCuenta(authentication.getName());
			 
			 return "cuenta";
		 }catch(Exception e) {
			 logger.error("Error en CuentaGet: " + e.getMessage(), e);
	         return "home";
		 }
	 }
}