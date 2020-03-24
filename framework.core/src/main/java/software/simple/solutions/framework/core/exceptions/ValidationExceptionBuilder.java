package software.simple.solutions.framework.core.exceptions;

import java.util.Locale;

public class ValidationExceptionBuilder {

	public ValidationExceptionBuilder() {
		super();
	}

	public FrameworkException build(String messageKey, Locale locale, Arg arg, Throwable e) {
		return new FrameworkException(messageKey, locale, arg, e);
	}

	public FrameworkException build(String messageKey, Locale locale, Arg arg) {
		return new FrameworkException(messageKey, locale, arg, null);
	}

	public FrameworkException build(String messageKey, Locale locale) {
		return new FrameworkException(messageKey, locale, null, null);
	}

	public FrameworkException build(String messageKey) {
		return new FrameworkException(messageKey, null, null, null);
	}

	public FrameworkException build(String messageKey, Locale locale, Throwable e) {
		return new FrameworkException(messageKey, locale, null, e);
	}

	public FrameworkException build(String messageKey, Arg arg, Throwable e) {
		return new FrameworkException(messageKey, null, arg, e);
	}

	public FrameworkException build(String messageKey, Arg arg) {
		return new FrameworkException(messageKey, null, arg, null);
	}

	public FrameworkException build(String messageKey, Throwable e) {
		return new FrameworkException(messageKey, null, null, e);
	}
}
