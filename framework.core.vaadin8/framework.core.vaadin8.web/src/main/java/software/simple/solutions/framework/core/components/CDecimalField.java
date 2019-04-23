package software.simple.solutions.framework.core.components;

import java.math.BigDecimal;

import org.vaadin.ui.NumberField;

import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

import software.simple.solutions.framework.core.constants.Style;
import software.simple.solutions.framework.core.properties.SystemProperty;
import software.simple.solutions.framework.core.util.NumberUtil;
import software.simple.solutions.framework.core.util.PropertyResolver;

public class CDecimalField extends NumberField implements IField {

	private static final long serialVersionUID = 6865980231260216834L;

	private boolean isThisRequired = false;
	private boolean wasReadOnly = false;

	public CDecimalField() {
		setDecimalAllowed(true);
		addStyleName(Style.TINY);
		addStyleName(Style.RIGHT_ALIGN);
		setGroupingUsed(true);
		setMinimumFractionDigits(10);
		setDecimalPrecision(10);

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
		// removeStyleName(Style.INVALID);
		removeStyleName(Style.INCOMPLETE);
		this.isThisRequired = false;
	}

	// @Override
	// public void setValue(String value) {
	// wasReadOnly = false;
	// if (isReadOnly()) {
	// wasReadOnly = true;
	// setReadOnly(false);
	// }
	// super.setValue(StringUtils.isBlank(value) ? "" : value.toString());
	// if (wasReadOnly) {
	// setReadOnly(true);
	// }
	// }

	public void setBigDecimalValue(BigDecimal value) {
		setValue(value == null ? "" : value.toString());
	}

	public BigDecimal getBigDecimalValue() {
		return NumberUtil.getBigDecimal(getValueNonLocalized());
	}

	@Override
	public String getValueNonLocalized() {
		String value = getValue();
		if (value == null || "".equals(value)) {
			return null;
		}
		String groupingSeparator = String.valueOf(getGroupingSeparator());
		return value.replace(groupingSeparator, "").replace(getDecimalSeparator(), '.');

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
			addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
		} else {
			removeStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
		}
	}

	public boolean isWasReadOnly() {
		return wasReadOnly;
	}

	@Override
	public void setCaptionByKey(String key) {
		super.setCaption(PropertyResolver.getPropertyValueByLocale(key, UI.getCurrent().getLocale()));
	}

	@Override
	public void clear() {
		if (!isReadOnly()) {
			super.clear();
		}
	}
}
