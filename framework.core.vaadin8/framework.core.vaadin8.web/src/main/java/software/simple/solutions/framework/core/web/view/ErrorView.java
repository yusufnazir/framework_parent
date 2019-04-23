package software.simple.solutions.framework.core.web.view;

import com.vaadin.ui.Label;

import software.simple.solutions.framework.core.components.AbstractBaseView;

public class ErrorView extends AbstractBaseView {

	/**
	 * 
	 */
	private static final long serialVersionUID = -380782155800371663L;

	public ErrorView() {
		super();
		addComponent(new Label("An error occured."));
	}

	@Override
	public String getViewName() {
		return "Error";
	}

	@Override
	public void executeBuild() {
		// TODO Auto-generated method stub
		
	}

}
