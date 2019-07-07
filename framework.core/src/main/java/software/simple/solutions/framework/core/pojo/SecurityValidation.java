package software.simple.solutions.framework.core.pojo;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import software.simple.solutions.framework.core.exceptions.Arg;

public class SecurityValidation {

	private Boolean success;
	private String messageKey;
	private Arg args;

	public SecurityValidation() {
		super();
		success = true;
	}

	public SecurityValidation(boolean success) {
		this();
		this.success = success;
	}

	public SecurityValidation(boolean success, String messageKey) {
		this(success);
		this.messageKey = messageKey;
	}

	public SecurityValidation(boolean success, String messageKey, Arg args) {
		this(success, messageKey);
		this.args = args;
	}

	public Boolean isSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMessageKey() {
		return messageKey;
	}

	public void setMessageKey(String messageKey) {
		this.messageKey = messageKey;
	}

	public Object[] getArgs(Function<Arg.Value, Object> function) {
		if (args == null) {
			return null;
		}
		List<Object> list = args.getValues().stream().map(function).collect(Collectors.toList());
		return list.toArray();
	}

	public void setArgs(Arg args) {
		this.args = args;
	}

	public static SecurityValidation build(String messageKey) {
		return new SecurityValidation(false, messageKey);
	}

	public static SecurityValidation build(String messageKey, Arg args) {
		return new SecurityValidation(false, messageKey, args);
	}

}
