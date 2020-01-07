package software.simple.solutions.framework.core.web.components;

import org.apache.commons.lang3.ObjectUtils;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.textfield.TextArea;

import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.web.IField;

public class CTextArea extends TextArea implements IField, Comparable<CTextArea> {

	private static final long serialVersionUID = 1400322141151924857L;

	private boolean isThisRequired = false;

	public CTextArea() {
		super();
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
		return isThisRequired;
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		super.setReadOnly(readOnly);
		// addStyleName(ValoTheme.TEXTAREA_BORDERLESS);
	}

	@Override
	public void setCaptionByKey(String caption) {
		super.setLabel(PropertyResolver.getPropertyValueByLocale(caption, UI.getCurrent().getLocale()));
	}

	@Override
	public int compareTo(CTextArea o) {
		return ObjectUtils.compare(getValue(), o.getValue());
	}

	@Override
	public void clear() {
		if (!isReadOnly()) {
			super.clear();
		}
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
	}

}
