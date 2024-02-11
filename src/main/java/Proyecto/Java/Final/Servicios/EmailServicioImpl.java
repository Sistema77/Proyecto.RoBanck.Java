package Proyecto.Java.Final.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

/**
 * Servicio que implementa los metodos de la interface {@link IEmailServicio} 
 * y en esta clase es donde se entra al detalle de la logica de dichos métodos
 * para la gestión de envio de emails.
 */
@Service
public class EmailServicioImpl implements IEmailServicio {

	@Autowired
	private JavaMailSender javaMailSender;	
	

	@Override
	public void enviarEmailRecuperacion(String emailDestino, String nombreUsuario, String token) {

		   try {
	            MimeMessage mensaje = javaMailSender.createMimeMessage();
	            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

	            helper.setFrom("parafirebaseestudiar@gmail.com");
	            helper.setTo(emailDestino);
	            helper.setSubject("Recuperación de contraseña RoBanck");
	            String urlDominio = "http://localhost:8080";
	            String urlDeRecuperacion = String.format("%s/auth/recuperar?token=%s", urlDominio, token);
	            
	            String cuerpoMensaje = String.format(
	    				"﻿<!DOCTYPE html> <html lang='es'> <body> <div style='width: 600px; padding: 20px; border: 2px solid black; border-radius: 13px; background-color: #DEDEDE;"
	    				+ " font-family: Sans-serif'> <h1 style='color:#1f3c85'>Restablecer contraseña<b style='color:#5993d3'> RoBanck</b></h1>"
	    				+ " <p style='margin-bottom:25px'>Estimado/a&nbsp;<b>%s</b>:</p> <p style='margin-bottom:25px'>"
	    				+ "Recibiste este correo porque se solicitó un restablecimiento de contraseña para tu cuenta. Haz clic en el botón que aparece a continuación para cambiar tu contraseña.</p>"
	    				+ " <a style='padding: 10px 15px; border-radius: 10px; background-color: #5993d3; color: white; text-decoration: none' href='%s' target='_blank'>Cambiar contraseña</a>"
	    				+ " <p style='margin-top:25px'>Si no solicitaste este restablecimiento de contraseña, puedes ignorar este correo de forma segura.</p>"
	    				+ " <p>Gracias por utilizar nuestros servicios.</p> </div> </body> </html>",
	                nombreUsuario, urlDeRecuperacion);

	            helper.setText(cuerpoMensaje, true);
	            System.out.println("4");
	            javaMailSender.send(mensaje);
	            System.out.println("5");
	        } catch (MailException me) {
	            System.out.println("[Error EmailServicioImpl - enviarEmailRecuperacion()] Ha ocurrido un error al enviar el email! " + me.getMessage());
	        } catch (MessagingException e) {
	            System.out.println("[Error EmailServicioImpl - enviarEmailRecuperacion()] Ha ocurrido un error al enviar el email! " + e.getMessage());
	        }		

	}
	
	@Override
    public void enviarEmailConfirmacion(String emailDestino, String nombreUsuario, String token) {
        try {
            MimeMessage mensaje = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

            helper.setFrom("parafirebaseestudiar@gmail.com");
            helper.setTo(emailDestino);
            helper.setSubject("Confirmación de cuenta RoBanck");

            String urlDominio = "http://localhost:8080";
            String urlDeConfirmacion = String.format("%s/auth/confirmar-cuenta?token=%s", urlDominio, token);

            String cuerpoMensaje = String.format(
                "﻿<!DOCTYPE html> <html lang='es'> <body> <div style='width: 600px; padding: 20px; border: 2px solid black; border-radius: 13px; background-color: #DEDEDE; font-family: Sans-serif'> <h1 style='color:#1f3c85'>Confirmar cuenta<b style='color:#5993d3'> RoBanck</b></h1>"
                + " <p style='margin-bottom:25px'>Estimado/a&nbsp;<b>%s</b>:</p> <p style='margin-bottom:25px'>"
                + "Bienvenido/a a RoBanck. Para confirmar tu cuenta, haz clic en el botón que aparece a continuación:</p>"
                + " <a style='padding: 10px 15px; border-radius: 10px; background-color: #5993d3; color: white; text-decoration: none' href='%s' target='_blank'>Confirmar cuenta</a>"
                + " <p style='margin-top:25px'>Gracias por unirte a RoBanck.</p> </div> </body> </html>",
                nombreUsuario, urlDeConfirmacion);

            helper.setText(cuerpoMensaje, true);

            javaMailSender.send(mensaje);

        } catch (MailException me) {
            System.out.println("[Error EmailServicioImpl - enviarEmailConfirmacion()] Ha ocurrido un error al enviar el email! " + me.getMessage());
        } catch (MessagingException e) {
            System.out.println("[Error EmailServicioImpl - enviarEmailConfirmacion()] Ha ocurrido un error al enviar el email! " + e.getMessage());
        }
    }
	

}