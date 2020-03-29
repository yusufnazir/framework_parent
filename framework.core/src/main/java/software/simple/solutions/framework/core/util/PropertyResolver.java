package software.simple.solutions.framework.core.util;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import software.simple.solutions.framework.core.config.PropertyHolder;
import software.simple.solutions.framework.core.constants.ReferenceKey;
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
		return getPropertyValueByLocale(ReferenceKey.PROPERTY, key, locale, arg, null);
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

	public static String getPropertyValueByLocale(String reference, String key, Locale locale, Arg arg,
			String defaultValue) {
		String message = null;
		try {
			Map<String, Properties> mapByReference = PropertyHolder.localization.getOrDefault(reference,
					new HashMap<String, Properties>());
			Properties properties = mapByReference.getOrDefault(locale.getISO3Language(), new Properties());
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
				if (defaultValue == null) {
					message = "NoKey: [" + key + "]";
					logger.error("NoKey: CODE [" + key + "] LOCALE [" + locale + "]");
				} else {
					message = defaultValue;
				}
			}
		} catch (NullPointerException e) {
			logger.error(e.getMessage() + " CODE [" + key + "] LOCALE [" + locale + "]");
			message = "NoKey: [" + key + "]";
		}
		return message;
	}

	public static String getPropertyValueByLocale(String reference, String key, Locale locale, String defaultValue) {
		return getPropertyValueByLocale(reference, key, locale, null, defaultValue);
	}

	public static String getPropertyValueByLocale(String reference, Long key, Locale locale, String defaultValue) {
		return getPropertyValueByLocale(reference, key.toString(), locale, null, defaultValue);
	}

}
