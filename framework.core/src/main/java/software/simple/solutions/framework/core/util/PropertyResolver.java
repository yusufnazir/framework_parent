package software.simple.solutions.framework.core.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import software.simple.solutions.framework.core.config.PropertyHolder;

public class PropertyResolver {

	private static final Logger logger = LogManager.getLogger(PropertyResolver.class);

	private PropertyResolver() {
		super();
	}

	public static String getPropertyValueByLocale(String key, Locale locale, Object[] args) {
		String message = null;
		try {
			Properties properties = PropertyHolder.propertyLocalized.get(locale.getISO3Language());
			message = properties.getProperty(key);
			if (args != null) {
				MessageFormat messageFormat = new MessageFormat(message);
				message = messageFormat.format(args);
			}
			if (message == null) {
				message = "NoKey: [" + key + "]";
				logger.error("NoKey: CODE [" + key + "] LOCALE [" + locale + "]");
			}
		} catch (NullPointerException e) {
			logger.error(e.getMessage() + " CODE [" + key + "] LOCALE [" + locale + "]");
			message = "NoKey: [" + key + "]";
		}
		return message;
	}

	public static String getPropertyValueByLocale(String key, Locale locale) {
		return getPropertyValueByLocale(key, locale, null);
	}
}
