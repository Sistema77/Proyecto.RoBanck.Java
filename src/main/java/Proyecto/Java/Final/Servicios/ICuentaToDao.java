package Proyecto.Java.Final.Servicios;

import java.util.List;

import Proyecto.Java.Final.DAO.CuentaDAO;
import Proyecto.Java.Final.DTO.CuentaDTO;


public interface ICuentaToDao {
	//Metodo que convierte campo a campo un objeto UsuarioDTO a DAO

	public CuentaDAO cuentaToDao(CuentaDTO cuentaDTO);
	
	//Metodo que convierte toda una lista de objetos UsuarioDTO a lista de DAOs

	public List<CuentaDAO> listCuentaToDao(List<CuentaDTO>listacuentaDTO);
	


}
