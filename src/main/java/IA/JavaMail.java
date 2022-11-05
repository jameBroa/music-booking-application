package IA;


import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class JavaMail {

    //FUNCTION SENDS EMAIL ADDRESS
    public static void sendMail(String recipient, String reportMessage, String sender) throws MessagingException {
        Properties properties = new Properties();
        // < -- sets properties of sending the email -- >
        properties.put("mail.smtp.auth", true); //PROVIDES AUTHENTICATION FOR MAIL SERVICE
        properties.put("mail.smtp.starttls.enable", true); //ENABLES TLS ENCRYPTION
        properties.put("mail.smtp.host", "smtp.gmail.com"); //SETS WHAT SMTP HOST USED
        properties.put("mail.smtp.port", "587"); //SETS PORT USED FOR EMAIL
        // <-------------------------------------------->

        String emailAddress = "musicbooker21@gmail.com"; //EMAIL ADDRESS BEING USED TO SEND EMAILS
        String emailPassword = "Compsci01"; //PASSWORD TO THE EMAIL

        Session session = Session.getInstance(properties, new Authenticator() { //CREATES A SESSION
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailAddress, emailPassword);
            }
        });

        Message message = prepareMessage(session, emailAddress, recipient, reportMessage, sender); //CREATES MESSAGE
        Transport.send(message); //SENDS MESSAGE

    } //END OF FUNCTION

    //FUNCTION GATHERS CONFIGURES MESSAGE BEFORE BEING SENT
    private static Message prepareMessage(Session session, String emailAddress, String recipient, String text, String sender) throws MessagingException {
        Message message = new MimeMessage(session); //CREATES AN INSTANCE OF TYPE MESSAGE
        message.setFrom(new InternetAddress(emailAddress)); //SETS THE SENDER OF ADDRESS
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient)); //SETS THE RECIPIENT
        message.setSubject(sender); //SETS THE SUBJECT OF THE EMAIL
        message.setText(text); //SETS THE MESSAGE OF THE EMAIL
        return message; //RETURNS MESSAGE
    }//END OF FUNCTION
}
