package software.simple.solutions.framework.core.web;

import java.io.Serializable;

import software.simple.solutions.framework.core.exceptions.FrameworkException;

public interface IForm extends Serializable, Validateable {

	public Object getFormValues() throws FrameworkException;

	public <T> T setFormValues(Object entity) throws FrameworkException;

	public void handleNewForm() throws FrameworkException;

}
