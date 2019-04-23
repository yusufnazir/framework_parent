package software.simple.solutions.framework.core.web.view.password;

import com.vaadin.navigator.View;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public class SecurityErrorView extends Panel implements View {
	public static final String NAME = "error";

	public SecurityErrorView() {
		super(new VerticalLayout());
		setCaption("Error");
	}

}
