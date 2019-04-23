package software.simple.solutions.framework.core.util;

import java.text.MessageFormat;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import software.simple.solutions.framework.core.entities.MessagePerLocale;

public class MessageResolver {

	private static final Logger logger = LogManager.getLogger(MessageResolver.class);

	public static String getMessageValueByLocale(MessagePerLocale messageHolder, Locale locale, Object[] args) {
		if (messageHolder == null) {
			return "No message holder found.";
		}
		String message = null;
		try {
			message = messageHolder.getReason();
			if (args != null) {
				MessageFormat messageFormat = new MessageFormat(message);
				message = messageFormat.format(args);
			}
			if (message == null) {
				message = "NM: [" + messageHolder.getId() + "]";
				logger.error("NM: ID [" + messageHolder.getId() + "] LOCALE [" + locale + "]");
			}
		} catch (NullPointerException e) {
			logger.error(e.getMessage() + " ID [" + messageHolder.getId() + "] LOCALE [" + locale + "]");
			message = "NM: [" + messageHolder.getId() + "]";
		}
		return message;
	}

	public static String getMessageValueByLocale(MessagePerLocale messageHolder, Locale locale) {
		return getMessageValueByLocale(messageHolder, locale, null);
	}
}
