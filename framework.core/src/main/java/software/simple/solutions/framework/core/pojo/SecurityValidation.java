package software.simple.solutions.framework.core.pojo;

public class SecurityValidation {

	private Boolean success;
	private String messageKey;

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

	public static SecurityValidation build(String messageKey) {
		return new SecurityValidation(false, messageKey);
	}

}
