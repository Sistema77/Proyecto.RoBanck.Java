package Proyecto.Java.Final.Servicios;

import java.util.ArrayList;
import java.util.List;

import Proyecto.Java.Final.DAO.CuentaDAO;
import Proyecto.Java.Final.DAO.UsuarioDAO;
import Proyecto.Java.Final.DTO.CuentaDTO;
import Proyecto.Java.Final.DTO.UsuarioDTO;

public class CuentaToDtoImple implements ICuentaToDto {

	@Override
	public CuentaDTO cuentaToDto(CuentaDAO u) {
		try {
            // Crear un nuevo objeto UsuarioDTO y copiar los atributos del UsuarioDAO
			CuentaDTO dto = new CuentaDTO();
           
			dto.setNumeroCuenta(u.getNumeroCuenta());
			dto.setSaldo(u.getSaldo());
			dto.setFch_apertura(u.getFch_apertura());
			dto.setConNomina(u.getConNomina());
            
            return dto;
        } catch (Exception e) {
            System.out.println(
                    "\n[ERROR UsuarioToDtoImpl - usuarioToDto()] - Error al convertir usuario DAO a usuarioDTO (return null): "
                            + e);
            return null;
        }
	}

	@Override
	public List<CuentaDTO> listaCuentaToDto(List<CuentaDAO> listaCuenta) {
		 try {
			 // Crear una nueva lista para almacenar los objetos UsuarioDTO convertidos
	            List<CuentaDTO> listaDto = new ArrayList<>();
	            
	            // Iterar sobre la lista de UsuarioDAO y convertir cada uno a UsuarioDTO
	            for (CuentaDAO u : listaCuenta) {
	                listaDto.add(this.cuentaToDto(u));
	            }
	            return listaDto;

	        } catch (Exception e) {
	            System.out.println(
	                    "\n[ERROR UsuarioToDtoImpl - listauUsuarioToDto()] - Error al convertir lista de usuario DAO a lista de usuarioDTO (return null): "
	                            + e);
	        }
	        return null;
	}

}
