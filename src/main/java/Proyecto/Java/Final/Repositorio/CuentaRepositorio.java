package Proyecto.Java.Final.Repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import Proyecto.Java.Final.DAO.CuentaDAO;
import Proyecto.Java.Final.DAO.UsuarioDAO;


public interface CuentaRepositorio extends JpaRepository<CuentaDAO, Long>{
	
	//Método que busca un cuenta por ID en la base de datos y lo devuelve
	public CuentaDAO findById(long id);
	
	//Método que devuelve todos los usuario de la base de datos
	
	public List<CuentaDAO> findAll();
	
	public List<CuentaDAO> findByUsuario(UsuarioDAO usuario);
}
