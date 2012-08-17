package models;


import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;







 

public class SendMail {
 
	public static void main(String[] args) {
		
		Email email = new SimpleEmail();
		String authuser = "mitsuo1991@gmail.com";
		String authpwd = "megaman3";
		email.setSmtpPort(587);
		email.setAuthenticator(new DefaultAuthenticator(authuser, authpwd));
		email.setDebug(true);
		email.setHostName("smtp.gmail.com");
		try {
			email.getMailSession().getProperties().put("mail.smtps.auth", "true");
			email.getMailSession().getProperties().put("mail.debug", "true");
			email.getMailSession().getProperties().put("mail.smtps.port", "587");
			email.getMailSession().getProperties().put("mail.smtps.socketFactory.port", "587");
			email.getMailSession().getProperties().put("mail.smtps.socketFactory.class",   "javax.net.ssl.SSLSocketFactory");
			email.getMailSession().getProperties().put("mail.smtps.socketFactory.fallback", "false");
			email.getMailSession().getProperties().put("mail.smtp.starttls.enable", "true");
			email.setFrom("mitsuo1991@gmail.com", "Miguel");
			email.setSubject("Hello World");
			email.setMsg("FUS ROH DAH!!!!!");
			email.addTo("mitsuo1991@gmail.com", "Miguel");
			email.setTLS(true);
			email.send();
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}	
}

