package software.simple.solutions.framework.core.web.lookup;

import software.simple.solutions.framework.core.entities.Menu;
import software.simple.solutions.framework.core.web.components.LookUpField;

public class LookUpHolder {

	private Menu menu;
	private Object parentEntity;
	private Class<?> viewClass;
	private LookUpField lookUpField;

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public Object getParentEntity() {
		return parentEntity;
	}

	public void setParentEntity(Object parentEntity) {
		this.parentEntity = parentEntity;
	}

	public Class<?> getViewClass() {
		return viewClass;
	}

	public void setViewClass(Class<?> viewClass) {
		this.viewClass = viewClass;
	}

	public LookUpField getLookUpField() {
		return lookUpField;
	}

	public void setLookUpField(LookUpField lookUpField) {
		this.lookUpField = lookUpField;
	}

}
