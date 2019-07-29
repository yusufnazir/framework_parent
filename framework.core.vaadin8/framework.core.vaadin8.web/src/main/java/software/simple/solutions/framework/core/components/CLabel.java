package software.simple.solutions.framework.core.components;

import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

import software.simple.solutions.framework.core.util.PropertyResolver;

public class CLabel extends Label implements Captionable {
	private static final long serialVersionUID = 3674052824399336235L;

	private CaptionLabel captionLabel;

	public void setValueByKey(String key) {
		setValue(PropertyResolver.getPropertyValueByLocale(key, UI.getCurrent().getLocale()));
	}

	@Override
	public CaptionLabel getLabel() {
		return captionLabel;
	}

	@Override
	public void setLabel(CaptionLabel label) {
		this.captionLabel = label;
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (captionLabel != null) {
			captionLabel.setVisible(visible);
		}
	}
}
