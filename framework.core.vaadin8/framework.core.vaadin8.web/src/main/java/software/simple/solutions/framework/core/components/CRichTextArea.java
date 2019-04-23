package software.simple.solutions.framework.core.components;

import org.apache.commons.lang3.ObjectUtils;

import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.themes.ValoTheme;

import software.simple.solutions.framework.core.constants.Style;
import software.simple.solutions.framework.core.util.PropertyResolver;

public class CRichTextArea extends RichTextArea implements IField, Comparable<CRichTextArea> {

	private static final long serialVersionUID = 1456255778562075546L;

	private boolean isThisRequired = false;

	public CRichTextArea() {
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
		boolean wasReadOnly = isReadOnly();
		if (wasReadOnly) {
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
			addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
		}
	}

	@Override
	public void setCaptionByKey(String caption) {
		super.setCaption(PropertyResolver.getPropertyValueByLocale(caption, getLocale()));
	}

	@Override
	public int compareTo(CRichTextArea o) {
		return ObjectUtils.compare(getValue(), o.getValue());
	}

	public void setUpperCase() {
		addStyleName(Style.UPPERCASE);
	}

	@Override
	public void clear() {
		if (!isReadOnly()) {
			super.clear();
		}
	}
}