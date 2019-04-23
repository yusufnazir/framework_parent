package software.simple.solutions.framework.core.mailing;

import java.util.Properties;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MailSettings {

	private static final Logger logger = LogManager.getLogger(MailSettings.class);

	private Properties properties;
	private String username;
	private String password;
	private boolean useAuthorisation = false;
	private String defaultNoReply;

	public MailSettings() {
		properties = System.getProperties();
	}

	private void setMailServerSettings(String exchangeServer, String port, boolean useAuthorisation) {
		this.useAuthorisation = useAuthorisation;
		properties.put("mail.smtp.auth", String.valueOf(useAuthorisation));
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.socketFactory.fallback", "true");
		properties.put("mail.smtp.host", exchangeServer);
		properties.put("mail.smtp.port", port);
		properties.put("mail.smtp.ssl.trust", exchangeServer);
	}

	public void setMailServerSettings(String exchangeServer, String port, String username, String password) {
		if (username != null && password != null) {
			setMailServerSettings(exchangeServer, port, true);
		} else {
			setMailServerSettings(exchangeServer, port, false);
		}
		this.username = username;
		this.password = password;
	}

	public Session getMailSession() {
		Session session = null;
		if (useAuthorisation) {
			session = Session.getInstance(properties, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});
		} else {
			session = Session.getDefaultInstance(properties);
		}
		return session;
	}

	public String getDefaultNoReply() {
		return defaultNoReply;
	}

	public void setDefaultNoReply(String defaultNoReply) {
		this.defaultNoReply = defaultNoReply;
	}

}
