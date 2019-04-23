package software.simple.solutions.framework.core.pojo;

import java.util.ArrayList;
import java.util.List;

import software.simple.solutions.framework.core.entities.Menu;

public class SimpleMenuItem {

	private Long id;
	private Menu menu;
	private Long index;
	private List<SimpleMenuItem> menuItems;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<SimpleMenuItem> getMenus() {
		if (menuItems == null) {
			menuItems = new ArrayList<SimpleMenuItem>();
		}
		return menuItems;
	}

	public void setMenuItems(List<SimpleMenuItem> menuItems) {
		this.menuItems = menuItems;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public List<SimpleMenuItem> getMenuItems() {
		return menuItems;
	}

	public Long getIndex() {
		return index;
	}

	public void setIndex(Long index) {
		this.index = index;
	}

}
