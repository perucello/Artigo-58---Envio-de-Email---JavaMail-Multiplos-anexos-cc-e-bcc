package JavaMail;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class JavaMail {

	public static void main(String[] args) {
		
		//authentication
		final String seuEmail = "seuEmail@provedor.com.br";
		final String senha = "suaSenha";
		String fromEmail = seuEmail;
		String emailTo = "emailDestino@provedor.com.br";
		String emailCopia = "emailCopia@provedor.com.br";
		String emailCopiaOculta = "emailCopiaOculta@provedor.com.br";

		//dados de validação do provedor
		Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", "smtp.live.com");
        props.put("mail.smtp.socketFactory.port", "587");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");
        
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(seuEmail,senha);
			}
		});
 
		MimeMessage msg = new MimeMessage(session);
		try {
			//Email De:
			msg.setFrom(new InternetAddress(fromEmail));
			
			//Email Para:
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
			//Email com cópia para:
			msg.addRecipient(Message.RecipientType.CC, new InternetAddress(emailCopia));
			//Email com cópia oculta para:
			msg.addRecipient(Message.RecipientType.BCC, new InternetAddress(emailCopiaOculta));
					
			//Subtitulo do email
			msg.setSubject("Teste de envio de email com anexo.");
			
			Multipart emailContent = new MimeMultipart();
			
			//Corpo do email
			MimeBodyPart corpoEmail = new MimeBodyPart();
			corpoEmail.setText(
					"Email EducaCiencia FastCode com anexo, verificar anexo do email e comprove o envio do documento !\n"
					+ "Email enviado com cópia e cópia oculta !" );
		
			//Anexando arquivo pdf
			MimeBodyPart anexoPDF = new MimeBodyPart();
			String pathArquivoPDF = "C:\\teste\\emailPDF.pdf";
			anexoPDF.attachFile(pathArquivoPDF);
		
			//Anexando arquivo word
			MimeBodyPart anexoWord = new MimeBodyPart();
			String pathArquivoWord = "C:\\teste\\emailDOC.docx";
			anexoWord.attachFile(pathArquivoWord);
			
			emailContent.addBodyPart(corpoEmail);
			emailContent.addBodyPart(anexoPDF);
			emailContent.addBodyPart(anexoWord);

			msg.setContent(emailContent);
			
			//Enviando
			Transport.send(msg);
			//validando no console - sysout
			System.out.println("Email enviado por EducaCiencia FastCode!");
			System.out.println("Email enviado para: " + emailTo);
			System.out.println("Email enviado com copia para : " + emailCopia);
			System.out.println("Email enviado com copia oculta para: " + emailCopiaOculta);
			System.out.println("Email - path anexado: " + pathArquivoPDF);
			System.out.println("Email - path anexado2: " + pathArquivoWord);
		} 
		catch (MessagingException em) {
			em.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}