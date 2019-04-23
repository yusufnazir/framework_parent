package software.simple.solutions.framework.core.web.view.password;

import org.apache.commons.lang3.StringUtils;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinServletRequest;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import software.simple.solutions.framework.core.components.CButton;
import software.simple.solutions.framework.core.constants.Navigation;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.SecurityValidation;
import software.simple.solutions.framework.core.properties.SecurityProperty;
import software.simple.solutions.framework.core.properties.SystemProperty;
import software.simple.solutions.framework.core.service.IApplicationUserRequestResetPasswordService;
import software.simple.solutions.framework.core.service.IApplicationUserService;
import software.simple.solutions.framework.core.ui.ResetPasswordUI;
import software.simple.solutions.framework.core.util.ContextProvider;
import software.simple.solutions.framework.core.util.PropertyResolver;

public class ResetPasswordView extends VerticalLayout implements View {
	private static final long serialVersionUID = 5714845595036195879L;
	public static final String NAME = "message";
	private PasswordField newPasswordFld;
	private PasswordField repeatNewPasswordFld;
	private CButton submitBtn;
	private Label messageLbl;

	private String username;
	private String resetPasswordKey;

	public ResetPasswordView() {
		super();
		setSizeFull();
		Page.getCurrent().setTitle("Password reset");
	}

	@Override
	public void enter(ViewChangeEvent event) {
		resetPasswordKey = VaadinServletRequest.getCurrent().getParameter("key");
		if (StringUtils.isNotBlank(resetPasswordKey)) {

			IApplicationUserRequestResetPasswordService applicationUserRequestResetPasswordService = ContextProvider
					.getBean(IApplicationUserRequestResetPasswordService.class);
			try {
				SecurityValidation securityValidation = applicationUserRequestResetPasswordService
						.isResetPasswordValid(resetPasswordKey);
				if (!securityValidation.isSuccess()) {
					UI.getCurrent().getNavigator().navigateTo(ResetPasswordUI.ERROR);
				}

				Panel loginPanel = createLayout();
				addComponent(loginPanel);
				setComponentAlignment(loginPanel, Alignment.MIDDLE_CENTER);

			} catch (FrameworkException e) {
				e.printStackTrace();
				UI.getCurrent().getNavigator().navigateTo(ResetPasswordUI.ERROR);
			}
		} else {
			UI.getCurrent().getNavigator().navigateTo(ResetPasswordUI.ERROR);
		}
	}

	private Panel createLayout() {
		messageLbl = new Label();
		messageLbl.setWidth("100%");
		messageLbl.setVisible(false);
		messageLbl.addStyleName(ValoTheme.LABEL_FAILURE);
		addComponent(messageLbl);

		Panel loginPanel = new Panel();
		loginPanel.setCaption(PropertyResolver.getPropertyValueByLocale(SecurityProperty.LOGIN_RESET_PANEL_CAPTION));
		loginPanel.setSizeUndefined();
		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.setSizeFull();

		newPasswordFld = new PasswordField();
		newPasswordFld.setWidth("300px");
		newPasswordFld.setCaption(PropertyResolver.getPropertyValueByLocale(SecurityProperty.LOGIN_RESET_NEW_PASSWORD));
		verticalLayout.addComponent(newPasswordFld);

		repeatNewPasswordFld = new PasswordField();
		repeatNewPasswordFld.setWidth("300px");
		repeatNewPasswordFld.setCaption(
				PropertyResolver.getPropertyValueByLocale(SecurityProperty.LOGIN_RESET_REPEAT_NEW_PASSWORD));
		verticalLayout.addComponent(repeatNewPasswordFld);

		submitBtn = new CButton();
		submitBtn.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		submitBtn.addStyleName(ValoTheme.BUTTON_HUGE);
		submitBtn.setCaption(SystemProperty.SYSTEM_BUTTON_SUBMIT);
		submitBtn.setWidth("300px");
		verticalLayout.addComponent(submitBtn);

		loginPanel.setContent(verticalLayout);

		submitBtn.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1528620389297216423L;

			@Override
			public void buttonClick(ClickEvent event) {
				handleSubmit();
			}
		});

		return loginPanel;
	}

	protected void handleSubmit() {
		if (validate()) {
			try {
				submitResetPassword();
			} catch (FrameworkException e) {
				e.printStackTrace();
			}
		}
	}

	private void submitResetPassword() throws FrameworkException {
		String newPassword = newPasswordFld.getValue();

		IApplicationUserService applicationUserService = ContextProvider.getBean(IApplicationUserService.class);
		SecurityValidation securityValidation = applicationUserService.resetUserPasswordByKey(resetPasswordKey,
				newPassword);
		if (securityValidation.isSuccess()) {
			UI.getCurrent().getNavigator().navigateTo(Navigation.RESET_PASSWORD_SUCCESS);
		} else {
			messageLbl.setValue(PropertyResolver.getPropertyValueByLocale(securityValidation.getMessageKey()));
			messageLbl.setVisible(true);
		}
	}

	private Boolean validate() {
		String newPassword = newPasswordFld.getValue();
		String repeatNewPassword = repeatNewPasswordFld.getValue();

		if (StringUtils.isBlank(newPassword) || StringUtils.isBlank(repeatNewPassword)) {
			messageLbl.setValue(
					PropertyResolver.getPropertyValueByLocale(SecurityProperty.RESET_PASSWORD_ALL_FIELDS_REQUIRED));
			messageLbl.setVisible(true);
			return false;
		}

		if (!newPassword.equalsIgnoreCase(repeatNewPassword)) {
			messageLbl.setValue(PropertyResolver
					.getPropertyValueByLocale(SecurityProperty.RESET_PASSWORD_NEW_PASSWORDS_DO_NOT_MATCH));
			messageLbl.setVisible(true);
			return false;
		}

		messageLbl.setVisible(false);
		return true;
	}
}