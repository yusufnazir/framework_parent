package software.simple.solutions.framework.core.components;

import java.time.LocalDateTime;

import com.vaadin.ui.DateTimeField;
import com.vaadin.ui.UI;

import software.simple.solutions.framework.core.constants.Style;
import software.simple.solutions.framework.core.util.PropertyResolver;

public class CPopupDateTimeField extends DateTimeField implements IField {

	private static final long serialVersionUID = 2495694014542106728L;
	private boolean isThisRequired = false;

	public CPopupDateTimeField() {
		addStyleName(Style.TINY);
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
	public void setValue(LocalDateTime newFieldValue) {
		boolean wasReadOnly = false;
		if (isReadOnly()) {
			wasReadOnly = true;
			setReadOnly(false);
		}
		super.setValue(newFieldValue);
		if (wasReadOnly) {
			setReadOnly(true);
		}
	}

	@Override
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
	public void clear() {
		if (!isReadOnly()) {
			super.clear();
		}
	}

}
