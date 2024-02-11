package Proyecto.Java.Final.Servicios;

import java.util.List;

import Proyecto.Java.Final.DAO.UsuarioDAO;
import Proyecto.Java.Final.DTO.UsuarioDTO;

public interface IUsuarioServicio {

    // Método para registrar un nuevo usuario
    public UsuarioDTO registrar(UsuarioDTO userDTO);
    
    // Método para obtener una lista de todos los usuarios
    public List<UsuarioDTO> listadoUsuario();
    
    // Método para eliminar un usuario por su ID
    public UsuarioDAO eliminarUsuario(long id);
    
    // Método para buscar un usuario por su ID
    public UsuarioDAO buscarUsuarioId(long id);
    
    // Método para obtener una lista de todos los usuarios en formato DAO
    public List<UsuarioDAO> listadoUsuarioDAO();
    
    // Método para buscar un usuario por su dirección de correo electrónico
    public UsuarioDTO buscarUsuarioEmail(String email);
    
    // Método para modificar los detalles de un usuario
    public void modificarUsuario(long id, UsuarioDTO usuarioModificado);
    
    // Método para obtener un usuario por su token de recuperación de contraseña
    public UsuarioDTO obtenerUsuarioPorToken(String token);
    
    // Método para modificar la contraseña de un usuario con un token de recuperación
    public boolean modificarContraseñaConToken(UsuarioDTO usuario);
    
    // Método para iniciar el proceso de recuperación de contraseña para un usuario
    public boolean iniciarProcesoRecuperacion(String emailUsuario);
    
    // Método para confirmar la cuenta de un usuario mediante un token de confirmación
    public boolean confirmarCuenta(String token);
    
    // Método para verificar si la cuenta de un usuario está confirmada
    public boolean estaLaCuentaConfirmada(String email);
}
