package software.simple.solutions.framework.core.web.components;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.textfield.IntegerField;

import software.simple.solutions.framework.core.util.NumberUtil;
import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.util.SortUtils;
import software.simple.solutions.framework.core.web.IField;

public class CDiscreetNumberField extends IntegerField implements IField, Comparable<CDiscreetNumberField> {

	private static final long serialVersionUID = 4976419156408287054L;

	private boolean isThisRequired = false;

	public CDiscreetNumberField() {
//		setErrorText(PropertyResolver.getPropertyValueByLocale(SystemProperty.INVALID_NUMBER_FORMAT,
//				UI.getCurrent().getLocale()));
	}

	public void setRequired() {
		this.isThisRequired = true;
	}

	/**
	 * Set the default style, not required, not error.
	 */
	public void setDefault() {
		this.isThisRequired = false;
	}

	// @Override
	// public void setValue(String value) {
	// boolean wasReadOnly = isReadOnly();
	// if (isReadOnly()) {
	// setReadOnly(false);
	// }
	// super.setValue(StringUtils.isBlank(value) ? null : value.toString());
	// if (wasReadOnly) {
	// setReadOnly(true);
	// }
	// }


	public Long getLongValue() {
		return NumberUtil.getLong(getValue());
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
	public int compareTo(CDiscreetNumberField o) {
		return SortUtils.compareTo(getValue(), o.getValue());
	}

	@Override
	public void clear() {
		if (!isReadOnly()) {
			super.clear();
		}
	}
}
