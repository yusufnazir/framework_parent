package software.simple.solutions.framework.core.web.components;

import org.apache.commons.lang3.ObjectUtils;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.textfield.PasswordField;

import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.web.IField;

public class CPasswordField extends PasswordField implements IField, Comparable<CPasswordField> {

	private static final long serialVersionUID = 1456255778562075546L;

	private boolean isThisRequired = false;

	public CPasswordField() {
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
	public void setValue(String newValue) {
		boolean wasReadOnly = false;
		if (isReadOnly()) {
			wasReadOnly = true;
			setReadOnly(false);
		}
		super.setValue(newValue == null ? "" : newValue);
		if (wasReadOnly) {
			setReadOnly(true);
		}
	}

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
	public void setCaptionByKey(String caption) {
		super.setLabel(PropertyResolver.getPropertyValueByLocale(caption, UI.getCurrent().getLocale()));
	}

	@Override
	public int compareTo(CPasswordField o) {
		return ObjectUtils.compare(getValue(), o.getValue());
	}

	public void setUpperCase() {
	}

	@Override
	public void clear() {
		if (!isReadOnly()) {
			super.clear();
		}
	}

}
