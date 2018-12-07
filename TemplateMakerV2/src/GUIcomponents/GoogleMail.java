package GUIcomponents;

import com.sun.mail.smtp.SMTPTransport;
import java.security.Security;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;


/**
 *
 * @author doraemon
 */
public class GoogleMail {
    private GoogleMail() {
    }

    /**
     * Send email using GMail SMTP server.
     *
     * @param username GMail username
     * @param password GMail password
     * @param recipientEmail TO recipient
     * @param title title of the message
     * @param message message to be sent
     * @throws if the email address parse failed
     * @throws MessagingException if the connection is dead or not in the connected state or if the message is not a MimeMessage
     */


    public static void Send(final String username, final String password, String recipientEmail, String title, String message) throws AddressException, MessagingException {
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

        // Get a Properties object
        Properties props = System.getProperties();
        props.setProperty("mail.smtps.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.setProperty("mail.smtps.auth", "true");
        props.put("mail.smtps.quitwait", "false");

        Session session = Session.getInstance(props, null);
        final MimeMessage msg = new MimeMessage(session);
        BodyPart msgBody = new MimeBodyPart();
        msgBody.setText(message);
        msg.setSubject(title);
        // -- Set the FROM and TO fields --
        msg.setFrom(new InternetAddress(username + "@gmail.com"));
        String recipient = getEmailAddress(recipientEmail);
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient, false));
        final Multipart mp = new MimeMultipart();
        mp.addBodyPart(msgBody);
        msg.setContent(mp);
        msg.setSentDate(new Date());

        SMTPTransport t = (SMTPTransport)session.getTransport("smtps");

        t.connect("smtp.gmail.com", username, password);
        t.sendMessage(msg, msg.getAllRecipients());
        t.close();
    }


    /**
     * Send email using GMail SMTP server.
     *
     * @param username GMail username
     * @param password GMail password
     * @param recipientEmail TO recipient
     * @param ccEmail CC recipient. Can be empty if there is no CC recipient
     * @param title title of the message
     * @param message message to be sent
     * @throws  if the email address parse failed
     * @throws MessagingException if the connection is dead or not in the connected state or if the message is not a MimeMessage
     */
    public static void Send(final String username, final String password, String recipientEmail, String ccEmail, String title, String message, String attachment) throws MessagingException {
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

        // Get a Properties object
        Properties props = System.getProperties();
        props.setProperty("mail.smtps.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.setProperty("mail.smtps.auth", "true");

        /*
        If set to false, the QUIT command is sent and the connection is immediately closed. If set
        to true (the default), causes the transport to wait for the response to the QUIT command.

        ref :   http://java.sun.com/products/javamail/javadocs/com/sun/mail/smtp/package-summary.html
                http://forum.java.sun.com/thread.jspa?threadID=5205249
                smtpsend.java - demo program from javamail
        */

        props.put("mail.smtps.quitwait", "false");

        Session session = Session.getInstance(props, null);

        // -- Create a new message --
        final MimeMessage msg = new MimeMessage(session);
        BodyPart msgBody = new MimeBodyPart();
        BodyPart attach0 = new MimeBodyPart();
        BodyPart attach1 = new MimeBodyPart();
        final Multipart multipart = new MimeMultipart();


        // -- Set the FROM and TO fields --
        msg.setFrom(new InternetAddress(username + "@gmail.com"));
        String recipient = getEmailAddress(recipientEmail);
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient, false));

        if (ccEmail.length() > 0) {
            msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccEmail, false));
        }

        msg.setSubject(title);
        msgBody.setText(message);


        // Second part is attachment
        msgBody = new MimeBodyPart();
        attach0 = new MimeBodyPart();
        attach1 = new MimeBodyPart();

        String[] filename = attachment.split(",");
        DataSource csvFile = new FileDataSource(filename[0]);
        DataSource plateMap = new FileDataSource(filename[1].trim());
        attach1.setDataHandler(new DataHandler(plateMap));
        msgBody.setDataHandler(new DataHandler(csvFile));

        msgBody.setFileName(title);
        attach1.setFileName(title.replace(".csv","")+"-Manifest.xlsx");


        multipart.addBodyPart(msgBody);
        multipart.addBodyPart(attach1);

        // Send the complete message parts
        msg.setContent(multipart);
        msg.setSentDate(new Date());

        SMTPTransport t = (SMTPTransport)session.getTransport("smtps");

        t.connect("smtp.gmail.com", username, password);
        t.sendMessage(msg, msg.getAllRecipients());
        t.close();
    }

    public static String getEmailAddress(String cc){
        if (cc.contains("Ashmita")){return "Ashmita.baral@gladstone.ucsf.edu";}
        else if (cc.contains("Jaslin")){return "Jaslin.Kalra@gladstone.ucsf.edu";}
        else if (cc.contains("Mariah")){return "Mariah.Dunlap@gladstone.ucsf.edu";}
        else if (cc.contains("Jeremy")){return "frogpolian83@gmail.com";}
        else if (cc.contains("Pasquale")){return "pas.pelle@gmail.com";}
        else if (cc.contains("JuliaK")){return "julia.kaye@gladstone.ucsf.edu";}
        else if (cc.contains("LisaE")){return "lisa.elia@gladstone.ucsf.edu";}
        else if (cc.contains("Mel")){return"melanie.cobb@gladstone.ucsf.edu";}
        else if (cc.contains("Amela")){return"amela.alijagic@gladstone.ucsf.edu";}
        else if (cc.contains("Jen")){return"jen.leddy@gladstone.ucsf.edu";}
        else if (cc.contains("Sid")){return "sid.mahajan@gladstone.ucsf.edu";}
        else if (cc.contains("LisaJo")){return "lisa.jo@gladstone.ucsf.edu";}
        else if (cc.contains("Nick")){return "nicholas.castello@gladstone.ucsf.edu";}
        else if (cc.contains("Caitlyn")){return "caitlyn.bonilla@gladstone.ucsf.edu";}
        else if (cc.contains("Elliot")){return"Elliot.Mount@gladstone.ucsf.edu";}
        else if (cc.contains("SaraM")){return"Sara.Modan@gladstone.ucsf.edu";}
        else if (cc.contains("LiseB")){return"Lise.Barbe@gladstone.ucsf.edu";}
        else if (cc.contains("AshkanJ")){return "Ashkan.Javaherian@gladstone.ucsf.edu";}
        else if (cc.contains("Robo6")){return "Roboticscope6@gmail.com";}
        else if (cc.contains("Seema")){return "seema.niddapu@gladstone.ucsf.edu";}
        else if (cc.contains("Michelle")){return "michelle.chan@gladstone.ucsf.edu";}
        return "";
    }

}
