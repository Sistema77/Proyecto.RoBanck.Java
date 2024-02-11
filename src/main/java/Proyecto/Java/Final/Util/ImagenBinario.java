package Proyecto.Java.Final.Util;

import java.io.IOException;
import java.util.Base64;

import org.springframework.web.multipart.MultipartFile;

public class ImagenBinario {

    // Convierte un arreglo de bytes a una cadena codificada en Base64
    public static String pasarBinarioAString(byte[] data) {
        if (data != null && data.length > 0) {
            return Base64.getEncoder().encodeToString(data);
        }
        return null;
    }
    
    // Convierte un objeto MultipartFile a un arreglo de bytes
    public static byte[] convertMultipartFileToByteArray(MultipartFile file) throws IOException {
        // Verificar si el archivo está vacío
        if (file.isEmpty()) {
            throw new IllegalArgumentException("El archivo está vacío");
        }
        
        // Convertir el archivo a un arreglo de bytes
        return file.getBytes();
    }
}
