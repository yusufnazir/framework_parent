package software.simple.solutions.framework.core.exceptions;

import java.util.Locale;

import software.simple.solutions.framework.core.util.PropertyResolver;

public class ValidationException extends FrameworkException {

	private static final long serialVersionUID = 3211784657235061111L;

	private final Locale locale;
	private final String message;

	public ValidationException(String messageKey, Locale locale) {
		super(messageKey);
		if (locale == null) {
			locale = Locale.ENGLISH;
		}
		this.locale = locale;
		this.message = PropertyResolver.getPropertyValueByLocale(messageKey, locale);
	}

	public ValidationException(String messageKey, Arg arg, Locale locale) {
		super(messageKey, arg);
		if (locale == null) {
			locale = Locale.ENGLISH;
		}
		this.locale = locale;
		this.message = PropertyResolver.getPropertyValueByLocale(messageKey, locale, arg);
	}

	public Locale getLocale() {
		return locale;
	}

	public String getMessage() {
		return message;
	}

}
