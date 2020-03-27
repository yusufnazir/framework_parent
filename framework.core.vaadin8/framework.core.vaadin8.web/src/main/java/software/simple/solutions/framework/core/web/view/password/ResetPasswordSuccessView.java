package software.simple.solutions.framework.core.web.view.password;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import software.simple.solutions.framework.core.properties.SecurityProperty;
import software.simple.solutions.framework.core.util.PropertyResolver;

public class ResetPasswordSuccessView extends VerticalLayout implements View {
	private static final long serialVersionUID = 5714845595036195879L;
	private Label messageLbl;

	public ResetPasswordSuccessView() {
		super();
		setSizeFull();
		Page.getCurrent().setTitle("Password reset success");
	}

	@Override
	public void enter(ViewChangeEvent event) {
		messageLbl = new Label();
		messageLbl.setSizeFull();
		messageLbl.addStyleName(ValoTheme.LABEL_BOLD);
		messageLbl.addStyleName(ValoTheme.LABEL_H1);
		messageLbl.setValue(PropertyResolver.getPropertyValueByLocale(SecurityProperty.RESET_PASSWORD_SUCCESS, UI.getCurrent().getLocale()));
		addComponent(messageLbl);
	}

}