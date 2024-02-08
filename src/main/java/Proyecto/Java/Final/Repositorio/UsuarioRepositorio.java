package Proyecto.Java.Final.Repositorio;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
	
	
	public UsuarioDAO findByName(String name);
	
	 // Método para obtener todos los usuarios
    public List<UsuarioDAO> findAll();
	
}