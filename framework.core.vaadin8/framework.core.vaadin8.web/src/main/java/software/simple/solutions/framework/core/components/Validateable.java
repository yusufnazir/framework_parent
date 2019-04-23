package software.simple.solutions.framework.core.components;

import software.simple.solutions.framework.core.exceptions.FrameworkException;

public interface Validateable {

	/**
	 * Validates collection of objects
	 * 
	 * @return
	 * @throws FrameworkException 
	 */
	public boolean validate() throws FrameworkException;
	
	public String getValidationMessage();
	
	public void setValidationMessage(String validationMessage);

}
