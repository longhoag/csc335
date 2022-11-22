package gmail;

// -- To prepare your gmail smtp account follow the steps described here
//    https://support.google.com/accounts/answer/185833
//
//    Sign in with App Passwords
//    Tip: App Passwords aren’t recommended and are unnecessary in most cases. 
//         To help keep your account secure, use "Sign in with Google" to connect apps to your Google Account. 
//
//    An App Password is a 16-digit passcode that gives a less secure app or 
//    device permission to access your Google Account. App Passwords can only 
//    be used with accounts that have 2-Step Verification turned on.
//                                    =============================
//    Basically, you are creating a password that is specific to the program you
//    are using to access the account. (this will bypass 2-step verification)
//
//    When to use App Passwords
//    =========================
//    Tip: iPhones and iPads with iOS 11 or up don’t require App Passwords. 
//    Instead use “Sign in with Google.”
//
//    If the app doesn’t offer “Sign in with Google,” you can either:
//
//    Use App Passwords
//    =================
//    Switch to a more secure app or device
//    Create & use App Passwords
//    If you use 2-Step-Verification and get a "password incorrect" error when you sign in, 
//    you can try to use an App Password.
//
//    Go to your Google Account.
//    1) Select Security.
//    2) Under "Signing in to Google," select App Passwords. You may need to sign in. 
//       If you don’t have this option, it might be because:
//       a) 2-Step Verification is not set up for your account.
//       b) 2-Step Verification is only set up for security keys.
//       c) Your account is through work, school, or other organization.
//       d) You turned on Advanced Protection.
//    3) At the bottom, choose Select app and choose the app you using and then 
//       Select device and choose the device you’re using and then Generate.
//    4) Follow the instructions to enter the App Password. 
//       The App Password is the 16-character code in the yellow bar on your device.
//       This is the password you will use in your code when connecting to the gmail
//       smtp server.
//    5) Tap Done.
//
//    Tip: Most of the time, you’ll only have to enter an App Password once per app or device, 
//    so don’t worry about memorizing it.



// -- Download JavaMail API from here: http://www.oracle.com/technetwork/java/javamail/index.html
// -- Download JavaBeans Activation Framework from here: http://www.oracle.com/technetwork/java/javasebusiness/downloads/java-archive-downloads-java-plat-419418.html#jaf-1.1.1-fcs-oth-JPR
import java.util.Properties;
import java.util.Scanner;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmailUsingGMailSMTP {

	// -- set the gmail host URL
	final static private String host = "smtp.gmail.com";

	// -- You must have a valid gmail username/password pair to use
	// gmail as a SMTP service
	static private String username = "<<insert your gmail username here>>";
	static private String password = "<<insert your password here>>";

	public static void main(String[] args) {

		Scanner kb = new Scanner(System.in);
		System.out.print("SMTP email username: ");
		username = kb.next();
		System.out.print("Recipient email address: ");
		String to = kb.next();
		String messagetext = "Here is your password: " + "abcd1234";
		
		// -- set up host properties
		//    refer to https://javaee.github.io/javamail/docs/api/com/sun/mail/smtp/package-summary.html for additional properties
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.ssl.enable", "true"); // -- to use port 465, the SSL port
		props.put("mail.smtp.port", "465");        // -- TLS port is 587);

		// -- Get the Session object.
		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		// -- Set up the sender's email account information
		String from = username + "@gmail.com";

		try {
			// -- Create a default MimeMessage object.
			Message message = new MimeMessage(session);

			// -- Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// -- Set To: header field of the header.
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

			// -- Set Subject: header field
			message.setSubject("CSC335 Project Email");

			// Now set the actual message
			message.setText(messagetext);

			// -- Send message
			// -- use either these three lines...
			// Transport t = session.getTransport("smtp");
			// t.connect();
			// t.sendMessage(message, message.getAllRecipients());
			
			// -- ...or this one (which ultimately calls sendMessage(...)
			Transport.send(message);

			System.out.println("Sent message successfully....");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}