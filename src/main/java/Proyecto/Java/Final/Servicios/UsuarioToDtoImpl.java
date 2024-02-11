package Proyecto.Java.Final.Servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import Proyecto.Java.Final.DAO.UsuarioDAO;
import Proyecto.Java.Final.DTO.UsuarioDTO;

/**
 * Servicio que implementa los métodos de la interfaz {@link IUsuarioToDto} 
 * y en esta clase es donde se entra en detalle en la lógica de dichos métodos
 * para el paso de UsuarioDAO a UsuarioDTO.
 */
@Service
public class UsuarioToDtoImpl implements IUsuarioToDto {

    // Método para convertir un objeto UsuarioDAO en un objeto UsuarioDTO
    @Override
    public UsuarioDTO usuarioToDto(UsuarioDAO u) {
        
        try {
            // Crear un nuevo objeto UsuarioDTO y copiar los atributos del UsuarioDAO
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
    
    // Método para convertir una lista de objetos UsuarioDAO en una lista de objetos UsuarioDTO
    @Override
    public List<UsuarioDTO> listaUsuarioToDto(List<UsuarioDAO> listaUsuario){
        try {
            // Crear una nueva lista para almacenar los objetos UsuarioDTO convertidos
            List<UsuarioDTO> listaDto = new ArrayList<>();
            
            // Iterar sobre la lista de UsuarioDAO y convertir cada uno a UsuarioDTO
            for (UsuarioDAO u : listaUsuario) {
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
