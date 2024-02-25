package Proyecto.Java.Final.Controladores;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import Proyecto.Java.Final.DAO.CuentaDAO;
import Proyecto.Java.Final.DAO.UsuarioDAO;
import Proyecto.Java.Final.DTO.CuentaDTO;
import Proyecto.Java.Final.DTO.UsuarioDTO;
import Proyecto.Java.Final.Servicios.ICuentaServicio;
import Proyecto.Java.Final.Servicios.ICuentaToDto;
import Proyecto.Java.Final.Servicios.IUsuarioServicio;
import Proyecto.Java.Final.Util.ImagenBinario;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CuentaControlador {

	 @Autowired
	 private ICuentaServicio cuentaServicio;
	 
	 @Autowired
	 private IUsuarioServicio usuarioServicio;
	 
	 @Autowired
	 private ICuentaToDto cuentaToDto;
	
	 private static final Logger logger = LoggerFactory.getLogger(CuentaControlador.class);
	 
	 
	 @GetMapping("/privada/cuenta")
	    public String CuentaGet(Model model, Authentication authentication) {
	        try {
	            // Agrega el nombre de usuario al modelo
	            model.addAttribute("nombreUsuario", authentication.getName());
	            model.addAttribute("foto", usuarioServicio.verFoto(authentication.getName()));

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
			 logger.error("Error en crearCuenta: " + e.getMessage(), e);
	         return "home";
		 }
	 }
	 
	 @GetMapping("/privada/ver-cuenta")
	 public String verCuenta(Model model, Authentication authentication) {
		    try {
		        model.addAttribute("nombreUsuario", authentication.getName());
		        model.addAttribute("foto", usuarioServicio.verFoto(authentication.getName()));
		        // Aquí estás tratando de acceder directamente a la lista de cuentas bancarias desde el servicio
		        model.addAttribute("cuentas", cuentaServicio.verCuenta(authentication.getName()));
		        return "listacuenta";
		    } catch(Exception e) {
		        logger.error("Error en crearCuenta: " + e.getMessage(), e);
		        return "home";
		    }
	 }
	 
     // Método para mostrar el formulario de modificación de cuenta
    @PostMapping("/privada/modificarcuenta/{id}")
    public String mostrarFormularioModificar(@PathVariable long id, Model model,Authentication authentication) {
         try {
            // Busca la cuenta por ID y lo convierte en un DTO para mostrar en el formulario
            CuentaDAO cuentaDao = cuentaServicio.buscarCuentaId(id);
            CuentaDTO cuentaDto = cuentaToDto.cuentaToDto(cuentaDao);
            
            // Agrega el usuario al modelo
            model.addAttribute("cuenta", cuentaDto);
            model.addAttribute("foto", usuarioServicio.verFoto(authentication.getName()));
            return "editarcuenta";
        } catch (Exception e) {
            // Manejo de errores
            logger.error("Error en mostrarFormularioModificar: " + e.getMessage(), e);
            return "listacuenta"; 
        }
    }
	 
	    // Método para procesar la solicitud de modificación de Cuenta
	    @PostMapping("/privada/modificar/cuenta/{id}")
	    public String modificarCuenta(@PathVariable long id, @ModelAttribute CuentaDTO cuentaModificado) {
	         try {
	             // Actualiza los datos del cuenta
	        	 cuentaServicio.modificarCuenta(id, cuentaModificado);
	        	 return "redirect:/privada/ver-cuenta"; 
	         } catch (Exception e) {
	             // Manejo de errores
	             logger.error("Error en modificarUsuario: " + e.getMessage(), e);
	             return "redirect:/privada/ver-cuenta";
	         }
	    }
	    
	    // Método para eliminar un cuenta
	    @PostMapping("/privada/eliminar/cuenta/{id}")
	    public String eliminarCuenta(@PathVariable long id, Model model, HttpServletRequest request) {
	        
	        try {
	            // Busca el cuenta por ID
	        	CuentaDAO cuenta = cuentaServicio.buscarCuentaId(id);
	            // Obtiene la lista de Cuenta
	            List<CuentaDAO> cuentas = cuentaServicio.listadoCuentaDAO();
	            
	            // Elimina la cuenta y redirige a la página a la lista de cuentas
	            cuentaServicio.eliminarCuenta(id);
	            
	            return "redirect:/privada/ver-cuenta";
	        } catch (Exception e) {
	            // Manejo de errores
	            logger.error("Error en eliminarUsuario: " + e.getMessage(), e);
	            return "redirect:/privada/ver-cuenta"; 
	        }
	        
	    }
}
