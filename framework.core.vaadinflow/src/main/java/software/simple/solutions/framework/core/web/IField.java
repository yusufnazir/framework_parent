package software.simple.solutions.framework.core.web;

public interface IField {

	/**
	 * Check if this field is required.
	 * 
	 * @return
	 */
	boolean isThisRequired();

	/**
	 * Make this field required.
	 */
	void setRequired();

	/**
	 * Remove error or required styles
	 */
	void setDefault();

	Object getValue();

	void setCaptionByKey(String string);

	public void clear();
}
