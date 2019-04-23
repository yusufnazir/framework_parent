package software.simple.solutions.framework.core.components;

import com.vaadin.ui.Button;
import com.vaadin.ui.UI;

import software.simple.solutions.framework.core.constants.Style;
import software.simple.solutions.framework.core.util.PropertyResolver;

public class CButton extends Button {

	private static final long serialVersionUID = 6582368776917351402L;

	private String captionKey;

	public CButton() {
		addStyleName(Style.TINY);
		setCaptionAsHtml(true);
	}

	public CButton(String caption) {
		this();
		super.setCaption(caption);
	}

	public void setCaptionByKey(String key) {
		captionKey = key;
		super.setCaption(PropertyResolver.getPropertyValueByLocale(key, UI.getCurrent().getLocale()));
	}

	public String getCaptionKey() {
		return captionKey;
	}

}
