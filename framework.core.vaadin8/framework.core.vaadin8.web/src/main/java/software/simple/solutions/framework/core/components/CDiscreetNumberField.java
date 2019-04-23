package software.simple.solutions.framework.core.components;

import org.vaadin.ui.NumberField;

import com.vaadin.ui.UI;

import software.simple.solutions.framework.core.constants.Style;
import software.simple.solutions.framework.core.properties.SystemProperty;
import software.simple.solutions.framework.core.util.NumberUtil;
import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.util.SortUtils;

public class CDiscreetNumberField extends NumberField implements IField, Comparable<CDiscreetNumberField> {

	private static final long serialVersionUID = 4976419156408287054L;

	private boolean isThisRequired = false;

	public CDiscreetNumberField() {
		setGroupingUsed(false);
		setDecimalAllowed(false);
		addStyleName(Style.RIGHT_ALIGN);
		addStyleName(Style.TINY);

		setErrorText(PropertyResolver.getPropertyValueByLocale(SystemProperty.INVALID_NUMBER_FORMAT,
				UI.getCurrent().getLocale()));
	}

	public void setRequired() {
		addStyleName(Style.INCOMPLETE);
		this.isThisRequired = true;
	}

	/**
	 * Set the default style, not required, not error.
	 */
	public void setDefault() {
		removeStyleName(Style.INCOMPLETE);
		this.isThisRequired = false;
	}

	@Override
	public void setValue(String value) {
		setValue(value, false);
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

	public void setLongValue(Long value) {
		if (value == null) {
			super.doSetValue(null);
		} else {
			setValue(String.valueOf(value));
		}
	}

	public void setIntValue(Integer value) {
		if (value == null) {
			super.doSetValue(null);
		} else {
			setValue(String.valueOf(value));
		}
	}

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
		super.setCaption(PropertyResolver.getPropertyValueByLocale(caption, UI.getCurrent().getLocale()));
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
