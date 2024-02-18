package Proyecto.Java.Final.Servicios;

import java.util.ArrayList;
import java.util.List;

import Proyecto.Java.Final.DAO.CuentaDAO;
import Proyecto.Java.Final.DTO.CuentaDTO;


public class CuentaToDaoImple implements ICuentaToDao{

	@Override
	public CuentaDAO cuentaToDao(CuentaDTO cuentaDTO) {
		
		CuentaDAO CuentaDao = new CuentaDAO();

        try {
        	
        	// Copiar los atributos del cuentaDTO al CuentaDAO
        	CuentaDao.setNumeroCuenta(cuentaDTO.getNumeroCuenta());
        	CuentaDao.setSaldo(cuentaDTO.getSaldo());
        	CuentaDao.setFch_apertura(cuentaDTO.getFch_apertura());
        	CuentaDao.setConNomina(cuentaDTO.getConNomina());
            
            return CuentaDao;
            
        }catch (Exception e) {
            System.out.println(
                    "\n[ERROR UsuarioToDaoImpl - toUsuarioDao()] - Al convertir usuarioDTO a usuarioDAO (return null): "
                            + e);
            return null;
        }
	}

	@Override
	public List<CuentaDAO> listCuentaToDao(List<CuentaDTO> listaCuentaDTO) {
		 List<CuentaDAO> listaCuentaDao = new ArrayList<>();

	        try {
	            // Iterar sobre la lista de UsuarioDTO y convertir cada uno a UsuarioDAO
	            for (CuentaDTO cuentaDTO : listaCuentaDTO) {
	            	listaCuentaDao.add(cuentaToDao(cuentaDTO));
	            }

	            return listaCuentaDao;

	        } catch (Exception e) {
	            System.out.println(
	                    "\n[ERROR UsuarioToDaoImpl - toListUsuarioDao()] - Al convertir lista de usuarioDTO a lista de usuarioDAO (return null): "
	                            + e);
	            return null;
	        }
	}

	

}
