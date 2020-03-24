package software.simple.solutions.framework.core.web;

import org.apache.commons.lang3.StringUtils;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;

import software.simple.solutions.framework.core.constants.MenuType;
import software.simple.solutions.framework.core.entities.Menu;
import software.simple.solutions.framework.core.entities.View;
import software.simple.solutions.framework.core.exceptions.ExceptionBuilder;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.SystemMessageProperty;
import software.simple.solutions.framework.core.util.NumberUtil;

public class SimpleSolutionsMenuItem {

	private Menu menu;
	private boolean divider = false;
	private boolean returnToMain = false;
	private Object searchedEntity;
	private Object parentEntity;

	public SimpleSolutionsMenuItem() {
		super();
	}

	public SimpleSolutionsMenuItem(final Menu menu) {
		super();
		this.menu = menu;
	}

	public SimpleSolutionsMenuItem(final Menu menu, boolean returnToMain) {
		this(menu);
		this.returnToMain = returnToMain;
	}

	public boolean isDivider() {
		return divider;
	}

	public void setDivider(boolean divider) {
		this.divider = divider;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	@SuppressWarnings("deprecation")
	public Resource getIcon() {
		if (menu.getIcon() == null) {
			return null;
		}
		Resource icon = FontAwesome.fromCodepoint(NumberUtil.getInteger(menu.getIcon()));
		if (icon == null) {
			icon = fromCodepoint(NumberUtil.getInteger(menu.getIcon()));
		}
		return icon;
	}

	public static VaadinIcons fromCodepoint(final int codepoint) {
		for (VaadinIcons f : VaadinIcons.values()) {
			if (f.getCodepoint() == codepoint) {
				return f;
			}
		}
		throw new IllegalArgumentException("Codepoint " + codepoint + " not found in FontAwesome");
	}

	public String getMenuName() {
		return menu.getName();
	}

	public String getViewName() {
		return menu.getView().getViewClassName();
	}

	public Class<?> getViewClass() throws FrameworkException {
		View view = menu.getView();
		if (view == null) {
			return null;
		}

		String viewClassName = view.getViewClassName();
		if (StringUtils.isBlank(viewClassName)) {
			return null;
		}
		try {
			return Class.forName(viewClassName);
		} catch (ClassNotFoundException e) {
			throw ExceptionBuilder.FRAMEWORK_EXCEPTION.build(SystemMessageProperty.COULD_NOT_CREATE_VIEW, e);
		}
	}

	public boolean isReturnToMain() {
		return returnToMain;
	}

	public void setReturnToMain(boolean returnToMain) {
		this.returnToMain = returnToMain;
	}

	public Long getId() {
		return menu.getId();
	}

	public boolean isParent() {
		return menu.getType().compareTo(MenuType.HEAD_MENU) == 0;
	}

	public Object getSearchedEntity() {
		return searchedEntity;
	}

	public void setSearchedEntity(Object searchedEntity) {
		this.searchedEntity = searchedEntity;
	}

	public Object getParentEntity() {
		return parentEntity;
	}

	public void setParentEntity(Object parentEntity) {
		this.parentEntity = parentEntity;
	}

}
