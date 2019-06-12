package software.simple.solutions.framework.core.web.view.password;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import software.simple.solutions.framework.core.components.CButton;
import software.simple.solutions.framework.core.components.CGridLayout;
import software.simple.solutions.framework.core.components.CTextField;
import software.simple.solutions.framework.core.components.MessageWindowHandler;
import software.simple.solutions.framework.core.components.NotificationWindow;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.ApplicationUserProperty;
import software.simple.solutions.framework.core.properties.SystemMessageProperty;
import software.simple.solutions.framework.core.properties.SystemProperty;
import software.simple.solutions.framework.core.service.facade.ApplicationUserRequestResetPasswordServiceFacade;
import software.simple.solutions.framework.core.util.ComponentUtil;
import software.simple.solutions.framework.core.util.PropertyResolver;

public class RequestPasswordResetLayout {

	private CTextField usernameFld;

	public RequestPasswordResetLayout() {
		Window window = new Window();
		window.setCaption(PropertyResolver.getPropertyValueByLocale(SystemProperty.SYSTEM_RESET_PASSWORD));
		UI.getCurrent().addWindow(window);
		window.center();
		window.setModal(true);

		CGridLayout resetPasswordLayout = ComponentUtil.createGrid();
		resetPasswordLayout.setMargin(true);
		resetPasswordLayout.setSpacing(true);
		window.setContent(resetPasswordLayout);

		usernameFld = resetPasswordLayout.addField(CTextField.class, ApplicationUserProperty.USERNAME, 0, 0);
		usernameFld.setWidth("250px");

		CButton savePasswordBtn = resetPasswordLayout.addField(CButton.class, 0, 1);
		savePasswordBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
		savePasswordBtn.setCaptionByKey(SystemProperty.SYSTEM_BUTTON_SUBMIT);
		resetPasswordLayout.setComponentAlignment(savePasswordBtn, Alignment.BOTTOM_LEFT);

		savePasswordBtn.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 5046218167403934004L;

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					ApplicationUserRequestResetPasswordServiceFacade.get(UI.getCurrent())
							.requestPasswordReset(usernameFld.getValue());
					window.close();
					NotificationWindow.notificationNormalWindow(SystemProperty.RESET_PASSWORD_REQUEST_SENT);
				} catch (FrameworkException e) {
					window.close();
					new MessageWindowHandler(SystemMessageProperty.FAILED_TO_REQUEST_PASSWORD_RESET, null);

				}
			}
		});

		window.setSizeUndefined();
	}

}
