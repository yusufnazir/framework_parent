package software.simple.solutions.framework.core.exceptions;

import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Main exception class for the framework.
 * 
 * @author yusuf
 *
 */
public class FrameworkException extends Exception {

	private static final long serialVersionUID = 1865054150568059175L;

	protected static final Logger serviceLogger = LogManager.getLogger(FrameworkException.class);

	private final String messageKey;
	private final Locale locale;
	private final Arg arg;

	public FrameworkException(String messageKey, Locale locale, Arg arg, Throwable throwable) {
		super(throwable);
		this.messageKey = messageKey;
		this.locale = locale;
		this.arg = arg;
	}

	public String getMessageKey() {
		return messageKey;
	}

	public Locale getLocale() {
		return locale;
	}

	public Arg getArg() {
		return arg;
	}

}
