package software.simple.solutions.framework.core.web;

import com.vaadin.server.Resource;

public class MenuDivider extends SimpleSolutionsMenuItem {

	public MenuDivider() {
		setDivider(true);
	}

	@Override
	public Resource getIcon() {
		return null;
	}
}
