package Proyecto.Java.Final.Servicios;

import java.util.List;

import Proyecto.Java.Final.DTO.UsuarioDTO;

public interface IUsuarioServicio {

	public UsuarioDTO registrar(UsuarioDTO userDTO);
	
	public List<UsuarioDTO> listadoUsuario();
}