package models;

public class SendMailTest {
	 
	public static void main(String[] args) {
 
		String from = "mitsuo1991@gmail.com";
		String to = "mitsuo1991@gmail.com";
		String subject = "Test";
		String message = "A test message";
 
		SendMail sendMail = new SendMail(from, to, subject, message);
		sendMail.send();
	}
}