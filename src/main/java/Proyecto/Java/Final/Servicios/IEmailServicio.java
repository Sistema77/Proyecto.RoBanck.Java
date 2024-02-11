package Proyecto.Java.Final.Servicios;

/**
 * Interface donde se declaran los metodos necesarios para la gestión de correos electrónicos.
 */
public interface IEmailServicio {
	
	/**
	 * Envía un correo electrónico de recuperación de contraseña.
	 */
	public void enviarEmailRecuperacion(String emailDestino, String nombreUsuario, String token);

	/**
	 * Envía un correo electrónico de confirmación de nueva cuenta de usuario.
	 */
	void enviarEmailConfirmacion(String emailDestino, String nombreUsuario, String token);


}