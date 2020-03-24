package software.simple.solutions.framework.core.web;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import software.simple.solutions.framework.core.constants.MenuType;
import software.simple.solutions.framework.core.entities.Menu;
import software.simple.solutions.framework.core.entities.View;
import software.simple.solutions.framework.core.exceptions.ExceptionBuilder;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.SystemMessageProperty;

public class SimpleSolutionsMenuItem {

	private Menu menu;
	private boolean divider = false;
	private boolean returnToMain = false;
	private Object searchedEntity;
	private Map<String, Object> referenceKeys;
	private Object parentEntity;
	private boolean isSubTab = false;

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

	// @SuppressWarnings("deprecation")
	// public Icon getIcon() {
	// if (menu.getIcon() == null) {
	// return null;
	// }
	// Resource icon =
	// FontAwesome.fromCodepoint(NumberUtil.getInteger(menu.getIcon()));
	// if (icon == null) {
	// icon = fromCodepoint(NumberUtil.getInteger(menu.getIcon()));
	// }
	// return icon;
	// }
	//
	// public static VaadinIcons fromCodepoint(final int codepoint) {
	// for (VaadinIcons f : VaadinIcons.values()) {
	// if (f.getCodepoint() == codepoint) {
	// return f;
	// }
	// }
	// throw new IllegalArgumentException("Codepoint " + codepoint + " not found
	// in FontAwesome");
	// }

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

	public Map<String, Object> getReferenceKeys() {
		return referenceKeys;
	}

	public void setReferenceKeys(Map<String, Object> referenceKeys) {
		this.referenceKeys = referenceKeys;
	}

	public boolean isSubTab() {
		return isSubTab;
	}

	public void setSubTab(boolean isSubTab) {
		this.isSubTab = isSubTab;
	}

	@SuppressWarnings("unchecked")
	public <T> T getParentEntity() {
		return (T) parentEntity;
	}

	public void setParentEntity(Object parentEntity) {
		this.parentEntity = parentEntity;
	}

}
