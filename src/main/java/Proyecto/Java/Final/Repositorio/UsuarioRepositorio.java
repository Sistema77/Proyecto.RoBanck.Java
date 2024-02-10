package Proyecto.Java.Final.Repositorio;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


import Proyecto.Java.Final.DAO.UsuarioDAO;


public interface UsuarioRepositorio extends JpaRepository<UsuarioDAO, Long> {
	/**
	 * Método que busca un usuario por Dni en la base de datos y lo devuelve
	 * @param dni
	 * @return Usuario o null
	 */
	public UsuarioDAO findByDni(String email);
	
	/**
	 * Método que busca un usuario por Email en la base de datos y lo devuelve
	 * @param email
	 * @return Usuario o null
	 */
	public UsuarioDAO findByEmail(String email);
	
	public UsuarioDAO findById(long id);
	
	public UsuarioDAO findByName(String name);
	
	public UsuarioDAO findByToken(String token);
	
	public List<UsuarioDAO> findAll();
	
	
}