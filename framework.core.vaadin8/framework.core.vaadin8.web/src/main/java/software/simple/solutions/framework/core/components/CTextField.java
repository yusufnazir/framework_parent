package software.simple.solutions.framework.core.components;

import org.apache.commons.lang3.ObjectUtils;

import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

import software.simple.solutions.framework.core.constants.Style;
import software.simple.solutions.framework.core.util.PropertyResolver;

public class CTextField extends TextField implements IField, Comparable<CTextField> {

	private static final long serialVersionUID = 1456255778562075546L;

	private boolean isThisRequired = false;

	public CTextField() {
		addStyleName(Style.TINY);
	}

	@Override
	public void setRequired() {
		addStyleName(Style.INCOMPLETE);
		this.isThisRequired = true;
	}

	@Override
	public void setDefault() {
		removeStyleName(Style.INCOMPLETE);
		this.isThisRequired = false;
	}

	@Override
	public void setValue(String value) {
		super.setValue(value == null ? "" : value);
	}

	// @Override
	// public void setValue(String newValue) {
	// boolean wasReadOnly = false;
	// if (isReadOnly()) {
	// wasReadOnly = true;
	// setReadOnly(false);
	// }
	// super.setValue(newValue == null ? "" : newValue);
	// if (wasReadOnly) {
	// setReadOnly(true);
	// }
	// }

	public boolean isThisRequired() {
		return this.isThisRequired;
	}

	public void setThisRequired(boolean isThisRequired) {
		this.isThisRequired = isThisRequired;
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		super.setReadOnly(readOnly);
		if (readOnly) {
			setDefault();
		}
	}

	@Override
	public void setCaptionByKey(String key) {
		super.setCaption(PropertyResolver.getPropertyValueByLocale(key, UI.getCurrent().getLocale()));
	}

	@Override
	public int compareTo(CTextField o) {
		return ObjectUtils.compare(getValue(), o.getValue());
	}

	public void setUpperCase() {
		addStyleName(Style.UPPERCASE);
	}

	@Override
	public void clear() {
		super.clear();
	}

}
