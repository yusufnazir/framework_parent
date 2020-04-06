package software.simple.solutions.framework.core.web.components;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.textfield.BigDecimalField;

import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.web.IField;

public class CDecimalField extends BigDecimalField implements IField {

	private static final long serialVersionUID = 6865980231260216834L;

	private boolean isThisRequired = false;
	private boolean wasReadOnly = false;

	public CDecimalField() {
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
		// removeStyleName(Style.INVALID);
		this.isThisRequired = false;
	}

	public boolean isThisRequired() {
		return this.isThisRequired;
	}

	public void setThisRequired(boolean isThisRequired) {
		this.isThisRequired = isThisRequired;
	}

	public boolean isWasReadOnly() {
		return wasReadOnly;
	}

	@Override
	public void setCaptionByKey(String key) {
		super.setLabel(PropertyResolver.getPropertyValueByLocale(key, UI.getCurrent().getLocale()));
	}

	@Override
	public void clear() {
		if (!isReadOnly()) {
			super.clear();
		}
	}

}
