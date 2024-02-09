package Proyecto.Java.Final.Servicios;

import java.util.List;

import Proyecto.Java.Final.DAO.UsuarioDAO;
import Proyecto.Java.Final.DTO.UsuarioDTO;

public interface IUsuarioServicio {

	public UsuarioDTO registrar(UsuarioDTO userDTO);
	
	public List<UsuarioDTO> listadoUsuario();
	
	public UsuarioDAO eliminarUsuario(long id);
	
	public UsuarioDAO buscarUsuarioId(long id);
	
	public List<UsuarioDAO> listadoUsuarioDAO();
	
	public UsuarioDTO buscarUsuarioEmail(String email);
	
	public void modificarUsuario(long id, UsuarioDTO usuarioModificado);
}