package software.simple.solutions.framework.core.web.components;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.timepicker.TimePicker;

import software.simple.solutions.framework.core.util.PropertyResolver;

public class CPopupDateTimeField extends TimePicker {

	private static final long serialVersionUID = 2495694014542106728L;

	public CPopupDateTimeField() {
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
