package software.simple.solutions.framework.core.components;

import com.vaadin.navigator.View;

import software.simple.solutions.framework.core.exceptions.FrameworkException;

public interface IView extends View {

	public void executeBuild() throws FrameworkException;

}
