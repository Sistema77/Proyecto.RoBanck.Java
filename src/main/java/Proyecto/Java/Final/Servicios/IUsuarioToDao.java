package Proyecto.Java.Final.Servicios;

import java.util.List;

import Proyecto.Java.Final.DAO.UsuarioDAO;
import Proyecto.Java.Final.DTO.UsuarioDTO;


/**
 * Interface donde se declaran los metodos necesarios para el paso de un usuarioDTO a DAO
 */
public interface IUsuarioToDao {
	
	/**
	 * Metodo que convierte campo a campo un objeto UsuarioDTO a DAO
	 * @param ausuarioDTO el objeto usuarioDTO
	 * @return Usuario convertido a DAO
	 */
	public UsuarioDAO usuarioToDao(UsuarioDTO usuarioDTO);
	
	/**
	 * Metodo que convierte toda una lista de objetos UsuarioDTO a lista de DAOs
	 * @param listaUsuarioDTO lista cargadas de objetos usuarioDTO
	 * @return Lista de usuarios DAO
	 */
	public List<UsuarioDAO> listUsuarioToDao(List<UsuarioDTO>listaUsuarioDTO);
	


}