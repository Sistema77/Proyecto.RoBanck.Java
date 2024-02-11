package Proyecto.Java.Final.Repositorio;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


import Proyecto.Java.Final.DAO.UsuarioDAO;


public interface UsuarioRepositorio extends JpaRepository<UsuarioDAO, Long> {
	
	// Método que busca un usuario por Dni en la base de datos y lo devuelve

	public UsuarioDAO findByDni(String email);
	
	//Método que busca un usuario por Email en la base de datos y lo devuelve
	 
	public UsuarioDAO findByEmail(String email);
	
	//Método que busca un usuario por ID en la base de datos y lo devuelve
	
	public UsuarioDAO findById(long id);
	
	//Método que busca un usuario por Nombre en la base de datos y lo devuelve
	
	public UsuarioDAO findByName(String name);
	
	//Método que busca un usuario por Token en la base de datos y lo devuelve
	
	public UsuarioDAO findByToken(String token);
	
	//Método que devuelve todos los usuario de la base de datos
	
	public List<UsuarioDAO> findAll();
	
	
}