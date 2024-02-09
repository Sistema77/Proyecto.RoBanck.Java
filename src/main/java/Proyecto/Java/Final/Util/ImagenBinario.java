package Proyecto.Java.Final.Util;

import java.io.IOException;
import java.util.Base64;

import org.springframework.web.multipart.MultipartFile;

public class ImagenBinario {

	public static String pasarBinarioAString(byte[] data) {
		if (data != null && data.length > 0) {
			return Base64.getEncoder().encodeToString(data);
		}
		return null;
	}
	
	public static byte[] convertMultipartFileToByteArray(MultipartFile file) throws IOException {
        // Verificar si el archivo está vacío
        if (file.isEmpty()) {
            throw new IllegalArgumentException("El archivo está vacío");
        }
        
        // Convertir el archivo a un arreglo de bytes
        return file.getBytes();
    }
	
	
}
