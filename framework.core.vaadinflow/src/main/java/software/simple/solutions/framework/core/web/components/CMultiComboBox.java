package software.simple.solutions.framework.core.web.components;

import org.vaadin.gatanaso.MultiselectComboBox;

import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.util.SortUtils;
import software.simple.solutions.framework.core.web.IField;

public class CMultiComboBox<T> extends MultiselectComboBox<T> implements IField, Comparable<CMultiComboBox<T>> {
	private static final long serialVersionUID = -6297946915884259838L;
	private boolean isThisRequired = false;

	public CMultiComboBox() {
		super();
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
		super.setLabel(PropertyResolver.getPropertyValueByLocale(caption, getLocale()));
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
