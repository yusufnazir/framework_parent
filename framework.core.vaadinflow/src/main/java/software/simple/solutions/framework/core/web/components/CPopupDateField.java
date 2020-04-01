package software.simple.solutions.framework.core.web.components;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.datepicker.DatePicker;

import software.simple.solutions.framework.core.util.PropertyResolver;

public class CPopupDateField extends DatePicker {

	private static final long serialVersionUID = 2495694014542106728L;

	public CPopupDateField() {
		super();
	}

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
