package software.simple.solutions.framework.core.components;

import com.vaadin.ui.CheckBox;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.util.SortUtils;

public class CCheckBox extends CheckBox implements IField, Comparable<CCheckBox> {

	private static final long serialVersionUID = -3572553066835019767L;

	public CCheckBox() {
		setProperties();
	}

	@Override
	public void setValue(Boolean value) {
		super.setValue(value == null ? false : value);
	}

	/**
	 * Set the {@link CCheckBox} caption by key. See {@link CheckBox}
	 * 
	 * @param key
	 */
	public void setCaptionByKey(String key) {
		super.setCaption(PropertyResolver.getPropertyValueByLocale(key, UI.getCurrent().getLocale()));
	}

	public void setProperties() {
		addStyleName(ValoTheme.CHECKBOX_LARGE);
	}

	@Override
	public boolean isThisRequired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setRequired() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDefault() {
		// TODO Auto-generated method stub
	}

	@Override
	public int compareTo(CCheckBox o) {
		return SortUtils.compareTo(getValue(), o.getValue());
	}

	@Override
	public void clear() {
		if (!isReadOnly()) {
			super.clear();
		}
	}

}
