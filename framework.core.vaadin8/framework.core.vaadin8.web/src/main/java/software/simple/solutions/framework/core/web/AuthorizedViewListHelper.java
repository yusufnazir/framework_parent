package software.simple.solutions.framework.core.web;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.ui.UI;

import software.simple.solutions.framework.core.components.SessionHolder;
import software.simple.solutions.framework.core.entities.Menu;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.facade.MenuServiceFacade;

public class AuthorizedViewListHelper {

	public List<SimpleSolutionsMenuItem> viewItems;
	private SessionHolder sessionHolder;

	public AuthorizedViewListHelper() {
		super();
		viewItems = new ArrayList<SimpleSolutionsMenuItem>();
		sessionHolder = (SessionHolder) UI.getCurrent().getData();
	}

	// public List<SimpleSolutionsMenuItem> createMenuItems(Menu parentMenu)
	// throws FrameworkException {
	// viewItems.clear();
	// List<Menu> listOfMenus = null;
	// if (parentMenu == null) {
	// listOfMenus = menuService.listParentMenus();
	// } else {
	// listOfMenus = menuService.listChildMenus(parentMenu.getId());
	// parentMenu.setIcon(FontAwesome.ARROW_LEFT.getCodepoint());
	// SimpleSolutionsMenuItem simpleSolutionsViewItem = new
	// SimpleSolutionsMenuItem(parentMenu, true);
	// viewItems.add(simpleSolutionsViewItem);
	// viewItems.add(new MenuDivider());
	// }
	// for (Menu menu : listOfMenus) {
	// viewItems.add(new SimpleSolutionsMenuItem(menu));
	// }
	//
	// return viewItems;
	// }

	public List<SimpleSolutionsMenuItem> getTabMenus(Long id) throws FrameworkException {
		List<SimpleSolutionsMenuItem> viewItems = new ArrayList<SimpleSolutionsMenuItem>();
		List<Menu> menus = MenuServiceFacade.get(UI.getCurrent()).findTabMenus(id,
				sessionHolder.getSelectedRole().getId());

		if (menus != null && !menus.isEmpty()) {
			for (Menu menu : menus) {
				viewItems.add(new SimpleSolutionsMenuItem(menu));
			}
		}

		return viewItems;
	}

}
