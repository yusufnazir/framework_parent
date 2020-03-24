package software.simple.solutions.framework.core.exceptions;

public class ExceptionBuilder {

	public static final FrameworkExceptionBuilder FRAMEWORK_EXCEPTION;
	public static final ValidationExceptionBuilder VALIDATION_EXCEPTION;

	static {
		FRAMEWORK_EXCEPTION = new FrameworkExceptionBuilder();
		VALIDATION_EXCEPTION = new ValidationExceptionBuilder();
	}

	private ExceptionBuilder() {
		super();
	}

}
