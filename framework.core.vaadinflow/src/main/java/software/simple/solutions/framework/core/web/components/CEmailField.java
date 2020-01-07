package software.simple.solutions.framework.core.web.components;

import org.apache.commons.lang3.ObjectUtils;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.textfield.EmailField;

import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.web.IField;

public class CEmailField extends EmailField implements IField, Comparable<CEmailField> {

	private static final long serialVersionUID = 1456255778562075546L;

	private boolean isThisRequired = false;

	public CEmailField() {
	}

	@Override
	public void setRequired() {
		this.isThisRequired = true;
	}

	@Override
	public void setDefault() {
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
		super.setLabel(PropertyResolver.getPropertyValueByLocale(key, UI.getCurrent().getLocale()));
	}

	@Override
	public int compareTo(CEmailField o) {
		return ObjectUtils.compare(getValue(), o.getValue());
	}

	public void setUpperCase() {
	}

	@Override
	public void clear() {
		super.clear();
	}

}
