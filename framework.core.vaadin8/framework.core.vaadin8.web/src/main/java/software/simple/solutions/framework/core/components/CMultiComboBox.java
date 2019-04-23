package software.simple.solutions.framework.core.components;

import org.vaadin.addons.ComboBoxMultiselect;

import software.simple.solutions.framework.core.constants.Style;
import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.util.SortUtils;

public class CMultiComboBox extends ComboBoxMultiselect implements IField, Comparable<CMultiComboBox> {
	private static final long serialVersionUID = -6297946915884259838L;
	private boolean isThisRequired = false;

	public CMultiComboBox() {
		setScrollToSelectedItem(true);
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
		super.setCaption(PropertyResolver.getPropertyValueByLocale(caption, getLocale()));
	}

	@Override
	public int compareTo(CMultiComboBox o) {
		return SortUtils.compareTo(getValue(), o.getValue());
	}

	@Override
	public void clear() {
		if (!isReadOnly()) {
			super.clear();
		}
	}
}
