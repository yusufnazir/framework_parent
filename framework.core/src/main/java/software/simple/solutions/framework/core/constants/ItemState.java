package software.simple.solutions.framework.core.constants;

public class ItemState {

	public static final String ACTIVE = "A";
	public static final String IN_ACTIVE = "I";
	public static final String DELETED = "D";

	public static Boolean isActive(String value) {
		return (value != null && value.equalsIgnoreCase(ACTIVE));
	}

	public static Boolean isInActive(String value) {
		return (value != null && value.equalsIgnoreCase(IN_ACTIVE));
	}

	public static Boolean isDeleted(String value) {
		return (value != null && value.equalsIgnoreCase(DELETED));
	}

}