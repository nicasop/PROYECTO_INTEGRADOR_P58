package com.daos.proyecto;

//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//import java.util.Properties;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Stateless
public class EmailDao {
	
	@Resource(name = "java:jboss/mail/gmail")
	private Session sesion;
	
	public void send(String destino,String asunto,String cuerpo) {
		try {
			Message mensaje = new MimeMessage(sesion);
			mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destino));
			mensaje.setSubject(asunto);
			mensaje.setText(cuerpo);
			
			Transport.send(mensaje);
		}catch (MessagingException ms) {
			ms.printStackTrace();
		}
	}
	    
}
