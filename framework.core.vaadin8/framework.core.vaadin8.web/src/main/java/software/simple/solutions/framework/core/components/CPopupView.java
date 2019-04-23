package software.simple.solutions.framework.core.components;

import com.vaadin.ui.Component;
import com.vaadin.ui.PopupView;

public class CPopupView extends PopupView {

	private static final long serialVersionUID = 5339540483238339130L;

	public CPopupView(String small, Component large) {
		super(small, large);
	}

	public CPopupView(PopupView.Content content) {
		super(content);
	}

}
