package software.simple.solutions.framework.core.pojo;

public class PersistenceBean {

	private boolean valid = true;
	private Object persistedObject;
	private String validationMessageKey;
	private Object[] validationArgs;

	public PersistenceBean() {
		super();
	}

	public PersistenceBean(boolean valid) {
		this();
		this.valid = valid;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public Object getPersistedObject() {
		return persistedObject;
	}

	public void setPersistedObject(Object persistedObject) {
		this.persistedObject = persistedObject;
	}

	public String getValidationMessageKey() {
		return validationMessageKey;
	}

	public void setValidationMessageKey(String validationMessageKey) {
		this.validationMessageKey = validationMessageKey;
	}

	public Object[] getValidationArgs() {
		return validationArgs;
	}

	public void setValidationArgs(Object[] validationArgs) {
		this.validationArgs = validationArgs;
	}

}
