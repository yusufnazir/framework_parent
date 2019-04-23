package software.simple.solutions.framework.core.exceptions;

public class ExceptionHolder {

	private String messageKey;
	private Arg args;
	private Throwable throwable;

	public ExceptionHolder(String messageKey) {
		this.messageKey = messageKey;
	}

	public ExceptionHolder(String messageKey, Arg args) {
		this(messageKey);
		this.args = args;
	}

	public ExceptionHolder(String messageKey, Throwable throwable) {
		this(messageKey);
		this.throwable = throwable;
	}

	public ExceptionHolder(String messageKey, Arg args, Throwable throwable) {
		this(messageKey, args);
		this.throwable = throwable;
	}

	public String getMessageKey() {
		return messageKey;
	}

	public void setMessageKey(String messageKey) {
		this.messageKey = messageKey;
	}

	public Arg getArgs() {
		return args;
	}

	public void setArgs(Arg args) {
		this.args = args;
	}

	public Throwable getThrowable() {
		return throwable;
	}

	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}

}
