package software.simple.solutions.framework.core.web.lookup;

import software.simple.solutions.framework.core.components.LookUpField;
import software.simple.solutions.framework.core.entities.Menu;
import software.simple.solutions.framework.core.web.view.MenuView;

public class MenuLookUpField extends LookUpField {

	private static final long serialVersionUID = 994848491488378790L;

	public MenuLookUpField() {
		super();
		setEntityClass(Menu.class);
		setViewClass(MenuView.class);
	}

}
