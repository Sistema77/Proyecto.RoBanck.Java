package Proyecto.Java.Final.Servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import Proyecto.Java.Final.DAO.UsuarioDAO;
import Proyecto.Java.Final.DTO.UsuarioDTO;


/**
 * Pasa de DAO a DTO
 */
@Service
public class UsuarioToDtoImpl implements IUsuarioToDto {

	@Override
	public UsuarioDTO usuarioToDto(UsuarioDAO u) {
		
		try {
			
			UsuarioDTO dto = new UsuarioDTO();
			dto.setName(u.getName());
			dto.setLastName(u.getLastName());
			dto.setDni(u.getDni());
			dto.setTlf(u.getTlf());
			dto.setEmail(u.getEmail());
			dto.setPassword(u.getPassword());
			dto.setToken(u.getToken());
			dto.setExpiracionToken(u.getExpiracionToken());
			dto.setId_usuario(u.getId_usuario());
			dto.setFch_alta(u.getFch_alta());
			
			return dto;
		} catch (Exception e) {
			System.out.println(
					"\n[ERROR UsuarioToDtoImpl - usuarioToDto()] - Error al convertir usuario DAO a usuarioDTO (return null): "
							+ e);
			return null;
		}
	}
	
	@Override
	public List<UsuarioDTO> listaUsuarioToDto(List<UsuarioDAO> listaUsuario){
		try {
				
			List<UsuarioDTO> listaDto = new ArrayList<>();
			
			for (UsuarioDAO u : listaUsuario) {
				
				System.out.println(u.toString());
				
				listaDto.add(this.usuarioToDto(u));
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