package software.simple.solutions.framework.core.web.components;

import java.time.LocalDateTime;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.timepicker.TimePicker;

import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.web.IField;

public class CPopupDateTimeField extends TimePicker implements IField {

	private static final long serialVersionUID = 2495694014542106728L;
	private boolean isThisRequired = false;

	public CPopupDateTimeField() {
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
		super.setLabel(PropertyResolver.getPropertyValueByLocale(caption, UI.getCurrent().getLocale()));
	}

	@Override
	public void clear() {
		if (!isReadOnly()) {
			super.clear();
		}
	}

}
