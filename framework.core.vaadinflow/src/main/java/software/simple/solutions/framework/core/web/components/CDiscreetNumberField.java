package software.simple.solutions.framework.core.web.components;

import com.vaadin.flow.component.UI;

import software.simple.solutions.framework.core.util.NumberUtil;
import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.util.SortUtils;

public class CDiscreetNumberField extends LongField implements Comparable<LongField> {

	private static final long serialVersionUID = 4976419156408287054L;

	public CDiscreetNumberField() {
		super();
	}

	public void setLongValue(Long value) {
		if (value != null) {
			setValue(value);
		}
	}

	public Long getLongValue() {
		return NumberUtil.getLong(getValue());
	}

	public String getStringValue() {
		return getValue() == null ? null : getValue().toString();
	}

	public void setCaptionByKey(String caption) {
		super.setLabel(PropertyResolver.getPropertyValueByLocale(caption, UI.getCurrent().getLocale()));
	}

	@Override
	public int compareTo(LongField o) {
		return SortUtils.compareTo(getValue(), o.getValue());
	}

	@Override
	public void clear() {
		if (!isReadOnly()) {
			super.clear();
		}
	}
}
