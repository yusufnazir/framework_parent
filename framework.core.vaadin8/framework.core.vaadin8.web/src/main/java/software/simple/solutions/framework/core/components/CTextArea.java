package software.simple.solutions.framework.core.components;

import org.apache.commons.lang3.ObjectUtils;

import com.vaadin.ui.TextArea;
import com.vaadin.ui.UI;

import software.simple.solutions.framework.core.constants.Style;
import software.simple.solutions.framework.core.util.PropertyResolver;

public class CTextArea extends TextArea implements IField, Captionable, Comparable<CTextArea> {

	private static final long serialVersionUID = 1400322141151924857L;

	private boolean isThisRequired = false;
	private CaptionLabel captionLabel;

	public CTextArea() {
		super();
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
		super.setCaption(PropertyResolver.getPropertyValueByLocale(caption, UI.getCurrent().getLocale()));
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
		if (captionLabel != null) {
			captionLabel.setVisible(visible);
		}
	}

	@Override
	public CaptionLabel getLabel() {
		// TODO Auto-generated method stub
		return this.captionLabel;
	}

	@Override
	public void setLabel(CaptionLabel label) {
		this.captionLabel = label;
	}
}
