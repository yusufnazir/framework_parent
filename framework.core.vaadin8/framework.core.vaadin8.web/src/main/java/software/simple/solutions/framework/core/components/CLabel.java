package software.simple.solutions.framework.core.components;

import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

import software.simple.solutions.framework.core.util.PropertyResolver;

public class CLabel extends Label {
	private static final long serialVersionUID = 3674052824399336235L;

	public void setValueByKey(String key) {
		setValue(PropertyResolver.getPropertyValueByLocale(key,UI.getCurrent().getLocale()));
	}
}
