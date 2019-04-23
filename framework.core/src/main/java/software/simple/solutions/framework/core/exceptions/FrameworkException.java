package software.simple.solutions.framework.core.exceptions;

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

	private String messageKey;
	private Arg args;

	public FrameworkException() {
		super();
	}

	public FrameworkException(String messageKey) {
		super(messageKey);
		this.messageKey = messageKey;
	}

	public FrameworkException(String messageKey, Throwable throwable) {
		super(messageKey, throwable);
		this.messageKey = messageKey;
	}
	
	public FrameworkException(String messageKey, Arg args) {
		this(messageKey);
		this.args = args;
	}

	public FrameworkException(String messageKey, Arg args, Throwable throwable) {
		this(messageKey, throwable);
		this.args = args;
	}

	public String getMessageKey() {
		return messageKey;
	}

	public Arg getArgs() {
		return args;
	}

	public ExceptionHolder getExceptionHolder() {
		return new ExceptionHolder(messageKey, args, getCause());
	}

}
