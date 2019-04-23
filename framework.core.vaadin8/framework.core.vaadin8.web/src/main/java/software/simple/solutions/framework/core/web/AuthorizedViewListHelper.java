package software.simple.solutions.framework.core.web;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.server.FontAwesome;

import software.simple.solutions.framework.core.entities.Menu;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.IMenuService;
import software.simple.solutions.framework.core.service.ISubMenuService;
import software.simple.solutions.framework.core.util.ContextProvider;

public class AuthorizedViewListHelper {

	public List<SimpleSolutionsMenuItem> viewItems;

	public AuthorizedViewListHelper() {
		super();
		viewItems = new ArrayList<SimpleSolutionsMenuItem>();
	}

	public List<SimpleSolutionsMenuItem> createMenuItems(Menu parentMenu) throws FrameworkException {
		viewItems.clear();
		IMenuService menuService = ContextProvider.getBean(IMenuService.class);
		List<Menu> listOfMenus = null;
		if (parentMenu == null) {
			listOfMenus = menuService.listParentMenus();
		} else {
			listOfMenus = menuService.listChildMenus(parentMenu.getId());
			parentMenu.setIcon(FontAwesome.ARROW_LEFT.getCodepoint());
			SimpleSolutionsMenuItem simpleSolutionsViewItem = new SimpleSolutionsMenuItem(parentMenu, true);
			viewItems.add(simpleSolutionsViewItem);
			viewItems.add(new MenuDivider());
		}
		for (Menu menu : listOfMenus) {
			viewItems.add(new SimpleSolutionsMenuItem(menu));
		}

		return viewItems;
	}

	public List<SimpleSolutionsMenuItem> getTabMenus(Long id) throws FrameworkException {
		List<SimpleSolutionsMenuItem> viewItems = new ArrayList<SimpleSolutionsMenuItem>();
		IMenuService menuService = ContextProvider.getBean(IMenuService.class);
		List<Menu> menus = menuService.findTabMenus(id);

		if (menus != null && !menus.isEmpty()) {
			for (Menu menu : menus) {
				viewItems.add(new SimpleSolutionsMenuItem(menu));
			}
		}

		return viewItems;
	}

}
