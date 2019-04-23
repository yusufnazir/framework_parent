package software.simple.solutions.framework.core.mailing;

import java.util.List;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mail {

	private static final Logger logger = LogManager.getLogger(Mail.class);

	private MailSettings mailSettings;
	private Message message;
	private Multipart multipart;

	public Mail(MailSettings mailSettings) {
		super();
		this.mailSettings = mailSettings;
	}

	public void createMail(String addressFrom, List<String> addresses, String subject, String messageBody)
			throws AddressException, MessagingException {
		createMail(addressFrom == null ? null : new InternetAddress(addressFrom),
				InternetAddress.parse(StringUtils.join(addresses, ",")), subject, messageBody);
	}

	public void createMail(Address addressFrom, List<Address> addresses, String subject, String messageBody)
			throws AddressException, MessagingException {
		createMail(addressFrom, addresses.toArray(new Address[addresses.size()]), subject, messageBody);
	}

	public void createMail(Address addressFrom, Address[] addresses, String subject, String messageBody)
			throws AddressException, MessagingException {
		message = new MimeMessage(mailSettings.getMailSession());
		if (addressFrom != null) {
			message.setFrom(addressFrom);
		} else {
			message.setFrom(new InternetAddress(mailSettings.getDefaultNoReply()));

		}
		message.setRecipients(Message.RecipientType.TO, addresses);
		message.setSubject(subject);

		multipart = new MimeMultipart();
		message.setContent(multipart);

		MimeBodyPart htmlPart = new MimeBodyPart();
		// Now set the actual message
		htmlPart.setContent(messageBody, "text/html");
		multipart.addBodyPart(htmlPart);
	}

	public void addAttachment(String filename, String simplename) throws MessagingException {
		BodyPart messageBodyPart = new MimeBodyPart();
		DataSource source = new FileDataSource(filename);
		messageBodyPart.setDataHandler(new DataHandler(source));
		messageBodyPart.setFileName(simplename);
		multipart.addBodyPart(messageBodyPart);
	}

	public void sendMail() throws MessagingException {
		Transport.send(message);
		if (message != null && message.getAllRecipients() != null) {
			for (Address address : message.getAllRecipients()) {
				logger.info("Mail sent to [" + address + "]");
			}
		}
	}
}
