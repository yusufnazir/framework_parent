package software.simple.solutions.framework.core.web.components;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.data.provider.Query;

import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.util.NumberUtil;
import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.util.SortUtils;
import software.simple.solutions.framework.core.web.IField;

public class CComboBox extends ComboBox<ComboItem> implements IField, Comparable<CComboBox> {
	private static final long serialVersionUID = -6297946915884259838L;
	private boolean isThisRequired = false;

	public CComboBox() {
		setItemLabelGenerator(ComboItem::getCaption);
		setPlaceholder(PropertyResolver.getPropertyValueByLocale("system.select.an.item", UI.getCurrent().getLocale()));
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

	@SuppressWarnings("unchecked")
	public <T> T getItemId() {
		if (getValue() == null) {
			return null;
		}
		return (T) getValue().getId();
	}

	public String getItemCaption() {
		if (getValue() == null) {
			return null;
		}
		return getValue().getCaption();
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		super.setReadOnly(readOnly);
		if (readOnly) {
			setDefault();
		}
	}

	@Override
	public void setCaptionByKey(String key) {
		super.setLabel(PropertyResolver.getPropertyValueByLocale(key, UI.getCurrent().getLocale()));
	}

	@Override
	public int compareTo(CComboBox o) {
		return SortUtils.compareTo(getValue(), o.getValue());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setItems(Collection<ComboItem> items, Long id) {
		setItems(items);
		if (id != null) {
			setValue(items.stream().filter(p -> ((Comparable) p.getId()).compareTo((Comparable) id) == 0).findFirst()
					.get());
		}
	}

	public void setLongValue(Long type) {
		Optional<ComboItem> optional = getDataProvider().fetch(new Query<>()).collect(Collectors.toList()).stream()
				.filter(p -> ((Comparable) p.getId()).compareTo((Comparable) type) == 0).findFirst();
		if (optional.isPresent()) {
			setValue(optional.get());
		}
	}

	public void setValue(Comparable type) {
		if (type != null) {
			Optional<ComboItem> optional = getDataProvider().fetch(new Query<>()).collect(Collectors.toList()).stream()
					.filter(p -> ((Comparable) p.getId()).compareTo(type) == 0).findFirst();
			if (optional.isPresent()) {
				setValue(optional.get());
			}
		}
	}

	public Long getLongValue() {
		ComboItem comboItem = getValue();
		if (comboItem == null) {
			return null;
		}
		return NumberUtil.getLong(comboItem.getId());
	}

	public String getStringValue() {
		ComboItem comboItem = getValue();
		if (comboItem == null) {
			return null;
		}
		return comboItem.getId().toString();
	}

	@Override
	public void clear() {
		if (!isReadOnly()) {
			super.clear();
		}
	}

}
