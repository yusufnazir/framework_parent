package software.simple.solutions.framework.core.constants;

public class MailTemplatePlaceholderItem {

	private String group;
	private String key;
	private Object value;

	public MailTemplatePlaceholderItem(String group, String key) {
		this.group = group;
		this.key = key;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
