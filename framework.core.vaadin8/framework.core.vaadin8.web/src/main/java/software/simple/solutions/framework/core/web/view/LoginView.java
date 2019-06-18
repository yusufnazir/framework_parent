package software.simple.solutions.framework.core.web.view;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import software.simple.solutions.framework.core.components.CButton;
import software.simple.solutions.framework.core.components.CLabel;
import software.simple.solutions.framework.core.components.CPasswordField;
import software.simple.solutions.framework.core.components.CTextField;
import software.simple.solutions.framework.core.components.MessageWindowHandler;
import software.simple.solutions.framework.core.components.NotificationWindow;
import software.simple.solutions.framework.core.constants.Style;
import software.simple.solutions.framework.core.entities.Configuration;
import software.simple.solutions.framework.core.events.SimpleSolutionsEvent.UserLoginRequestedEvent;
import software.simple.solutions.framework.core.events.SimpleSolutionsEventBus;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.SecurityValidation;
import software.simple.solutions.framework.core.properties.ConfigurationProperty;
import software.simple.solutions.framework.core.properties.SystemProperty;
import software.simple.solutions.framework.core.service.IApplicationUserService;
import software.simple.solutions.framework.core.service.facade.ConfigurationServiceFacade;
import software.simple.solutions.framework.core.util.ContextProvider;
import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.web.view.password.RequestPasswordResetLayout;

public class LoginView extends VerticalLayout {

	private static final long serialVersionUID = 1969692676006992700L;
	private static final Logger logger = LogManager.getLogger(LoginView.class);

	public LoginView() throws FrameworkException {
		setSizeFull();
		setMargin(false);
		setSpacing(false);

		Component loginForm = buildLoginForm();
		addComponent(loginForm);
		setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);
	}

	private Component buildLoginForm() throws FrameworkException {
		final VerticalLayout loginPanel = new VerticalLayout();
		loginPanel.setSizeUndefined();
		loginPanel.setMargin(false);
		Responsive.makeResponsive(loginPanel);
		loginPanel.addStyleName(Style.LOGIN_PANEL);

		loginPanel.addComponent(buildLabels());
		loginPanel.addComponent(buildFields());

		// loginPanel.addComponent(
		// new CheckBox(PropertyResolver.getPropertyValueByLocale("remember.me",
		// getLocale()), true));
		return loginPanel;
	}

	private Component buildFields() {
		HorizontalLayout fields = new HorizontalLayout();
		fields.addStyleName("fields");

		final CTextField username = new CTextField();
		username.setCaptionByKey(SystemProperty.LOGIN_USERNAME);
		username.setIcon(FontAwesome.USER);
		username.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

		final CPasswordField password = new CPasswordField();
		password.setCaptionByKey(SystemProperty.LOGIN_PASSWORD);
		password.setIcon(FontAwesome.LOCK);
		password.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

		final CButton signin = new CButton();
		signin.setCaptionByKey(SystemProperty.LOGIN_BUTTON_LOGIN);
		signin.addStyleName(ValoTheme.BUTTON_PRIMARY);
		signin.setClickShortcut(KeyCode.ENTER);
		signin.focus();

		fields.addComponents(username, password, signin);
		fields.setComponentAlignment(signin, Alignment.BOTTOM_LEFT);

		signin.addClickListener(new ClickListener() {

			private static final long serialVersionUID = -232691329181636057L;

			@Override
			public void buttonClick(final ClickEvent event) {
				try {
					IApplicationUserService applicationUserService = ContextProvider
							.getBean(IApplicationUserService.class);
					// SecurityValidation securityValidation =
					// ApplicationUserServiceFacade.get(UI.getCurrent())
					// .validateLogin(username.getValue(), password.getValue());
					SecurityValidation securityValidation = applicationUserService.validateLogin(username.getValue(),
							password.getValue());
					if (securityValidation.isSuccess()) {
						String applicationUsername = postProcessLdapUsername(username.getValue());
						SimpleSolutionsEventBus
								.post(new UserLoginRequestedEvent(applicationUsername, password.getValue()));
					} else {
						NotificationWindow.notificationErrorWindow(
								PropertyResolver.getPropertyValueByLocale(securityValidation.getMessageKey()),
								UI.getCurrent().getLocale());
					}
				} catch (FrameworkException e) {
					new MessageWindowHandler(e);
					logger.error(e.getMessage(), e);
				}
			}
		});
		return fields;
	}

	protected String postProcessLdapUsername(String username) {
		String applicationUserName = username;
		if (username.contains("@")) {
			applicationUserName = username.substring(0, username.indexOf('@'));
		} else if (username.contains("\\")) {
			applicationUserName = username.substring(username.indexOf('\\') + 1, username.length());
		}
		return applicationUserName;
	}

	private Component buildLabels() throws FrameworkException {
		HorizontalLayout labels = new HorizontalLayout();
		labels.setWidth("100%");
		// labels.addStyleName(Style.LABELS);

		CLabel welcomeApplicationNameLbl = new CLabel();
		welcomeApplicationNameLbl.setValueByKey(SystemProperty.LOGIN_WELCOME);
		welcomeApplicationNameLbl.setSizeUndefined();
		welcomeApplicationNameLbl.addStyleName(ValoTheme.LABEL_H4);
		welcomeApplicationNameLbl.addStyleName(ValoTheme.LABEL_COLORED);
		labels.addComponent(welcomeApplicationNameLbl);
		labels.setExpandRatio(welcomeApplicationNameLbl, 1f);

		Configuration smtpEnableConfig = ConfigurationServiceFacade.get(UI.getCurrent())
				.getByCode(ConfigurationProperty.SMTP_ENABLE);
		if (smtpEnableConfig != null && smtpEnableConfig.getBoolean()) {

			CButton resetBtn = new CButton();
			resetBtn.setSizeUndefined();
			resetBtn.setCaptionByKey(SystemProperty.SYSTEM_RESET_PASSWORD);
			resetBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
			resetBtn.addStyleName(ValoTheme.BUTTON_LINK);
			labels.addComponent(resetBtn);
			labels.setComponentAlignment(resetBtn, Alignment.MIDDLE_RIGHT);

			resetBtn.addClickListener(new ClickListener() {

				private static final long serialVersionUID = -3880882585537226501L;

				@Override
				public void buttonClick(ClickEvent event) {
					new RequestPasswordResetLayout();
				}
			});
		}

		Configuration applicationNameConfig = ConfigurationServiceFacade.get(UI.getCurrent())
				.getByCode(ConfigurationProperty.APPLICATION_NAME);
		if (applicationNameConfig != null && applicationNameConfig.getValue() != null) {
			welcomeApplicationNameLbl.setValue(applicationNameConfig.getValue());
		}
		return labels;
	}

}
