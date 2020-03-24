package software.simple.solutions.framework.core.exceptions;

import java.util.Locale;

public class ValidationException extends FrameworkException {

	private static final long serialVersionUID = 3211784657235061111L;

	public ValidationException(String messageKey, Locale locale, Arg arg, Throwable throwable) {
		super(messageKey, locale, arg, throwable);
	}

}
