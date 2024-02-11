package Proyecto.Java.Final.Servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import Proyecto.Java.Final.DAO.UsuarioDAO;
import Proyecto.Java.Final.DTO.UsuarioDTO;

/**
 * Servicio que implementa los métodos de la interfaz {@link IUsuarioToDao} 
 * y en esta clase es donde se entra en detalle en la lógica de dichos métodos
 * para el paso de usuarioDTO a DAO.
 */
@Service
public class UsuarioToDaoImpl implements IUsuarioToDao {

    // Método para convertir un objeto UsuarioDTO en un objeto UsuarioDAO
    @Override
    public UsuarioDAO usuarioToDao(UsuarioDTO usuarioDTO) {

        UsuarioDAO usuarioDao = new UsuarioDAO();

        try {
            // Copiar los atributos del UsuarioDTO al UsuarioDAO
            usuarioDao.setName(usuarioDTO.getName());
            usuarioDao.setLastName(usuarioDTO.getLastName());
            usuarioDao.setEmail(usuarioDTO.getEmail());
            usuarioDao.setPassword(usuarioDTO.getPassword());
            usuarioDao.setTlf(usuarioDTO.getTlf());
            usuarioDao.setDni(usuarioDTO.getDni());
            usuarioDao.setFch_alta(usuarioDTO.getFch_alta());
            usuarioDao.setFoto(usuarioDTO.getFoto());
            
            return usuarioDao;

        } catch (Exception e) {
            System.out.println(
                    "\n[ERROR UsuarioToDaoImpl - toUsuarioDao()] - Al convertir usuarioDTO a usuarioDAO (return null): "
                            + e);
            return null;
        }

    }

    // Método para convertir una lista de objetos UsuarioDTO en una lista de objetos UsuarioDAO
    @Override
    public List<UsuarioDAO> listUsuarioToDao(List<UsuarioDTO> listaUsuarioDTO) {

        List<UsuarioDAO> listaUsuarioDao = new ArrayList<>();

        try {
            // Iterar sobre la lista de UsuarioDTO y convertir cada uno a UsuarioDAO
            for (UsuarioDTO usuarioDTO : listaUsuarioDTO) {
                listaUsuarioDao.add(usuarioToDao(usuarioDTO));
            }

            return listaUsuarioDao;

        } catch (Exception e) {
            System.out.println(
                    "\n[ERROR UsuarioToDaoImpl - toListUsuarioDao()] - Al convertir lista de usuarioDTO a lista de usuarioDAO (return null): "
                            + e);
            return null;
        }
    }
}
