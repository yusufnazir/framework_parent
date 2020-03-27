package software.simple.solutions.framework.core.util;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import software.simple.solutions.framework.core.config.PropertyHolder;
import software.simple.solutions.framework.core.exceptions.Arg;
import software.simple.solutions.framework.core.exceptions.Arg.Value;

public class PropertyResolver {

	private static final Logger logger = LogManager.getLogger(PropertyResolver.class);

	private PropertyResolver() {
		super();
	}

	public static String getPropertyValueByLocale(String key, Locale locale) {
		return getPropertyValueByLocale(key, locale, Arg.build());
	}

	public static String getPropertyValueByLocale(String key, Locale locale, Arg arg) {
		String message = null;
		try {
			Properties properties = PropertyHolder.propertyLocalized.get(locale.getISO3Language());
			message = properties.getProperty(key);
			if (arg != null) {
				List<Value> values = arg.getValues();
				List<String> arguments = new ArrayList<String>();
				for (Value value : values) {
					if (value.isKey()) {
						arguments.add(properties.getProperty(value.getValue()));
					} else {
						arguments.add(value.getValue());
					}
				}
				MessageFormat messageFormat = new MessageFormat(message);
				message = messageFormat.format(arguments.toArray());
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

	public static String getPropertyValueByLocale(String key, Locale locale, Object[] args) {
		Arg arg = Arg.build();
		if (args != null) {
			for (Object o : args) {
				arg.norm(o);
			}
		}
		return getPropertyValueByLocale(key, locale, arg);
	}
}
