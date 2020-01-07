package software.simple.solutions.framework.core.web.view;

import java.time.LocalDate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import software.simple.solutions.framework.core.components.CButton;
import software.simple.solutions.framework.core.components.CCheckBox;
import software.simple.solutions.framework.core.components.CLabel;
import software.simple.solutions.framework.core.components.CPasswordField;
import software.simple.solutions.framework.core.components.CPopupDateField;
import software.simple.solutions.framework.core.components.CTextField;
import software.simple.solutions.framework.core.components.MessageWindowHandler;
import software.simple.solutions.framework.core.components.NotificationWindow;
import software.simple.solutions.framework.core.components.select.GenderSelect;
import software.simple.solutions.framework.core.constants.Constants;
import software.simple.solutions.framework.core.constants.Style;
import software.simple.solutions.framework.core.entities.Configuration;
import software.simple.solutions.framework.core.events.SimpleSolutionsEvent.UserLoginRequestedEvent;
import software.simple.solutions.framework.core.events.SimpleSolutionsEventBus;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.SecurityValidation;
import software.simple.solutions.framework.core.properties.ConfigurationProperty;
import software.simple.solutions.framework.core.properties.RegistrationProperty;
import software.simple.solutions.framework.core.properties.SystemProperty;
import software.simple.solutions.framework.core.service.IApplicationUserService;
import software.simple.solutions.framework.core.service.facade.ConfigurationServiceFacade;
import software.simple.solutions.framework.core.util.ContextProvider;
import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.valueobjects.ApplicationUserVO;
import software.simple.solutions.framework.core.web.view.password.RequestPasswordResetLayout;

public class LoginView extends VerticalLayout implements View {

	private static final long serialVersionUID = 1969692676006992700L;
	private static final Logger logger = LogManager.getLogger(LoginView.class);
	private Component applicationLabelsLayout;
	private Component loginFieldsLayout;
	private Component registrationFieldsLayout;
	private Component registrationSuccessfullLayout;

	@Override
	public void enter(ViewChangeEvent event) {
	}

	public LoginView() throws FrameworkException {
		addStyleName("loginview");
		setSizeFull();
		VerticalLayout layout = new VerticalLayout();
		layout.setMargin(false);
		layout.setSpacing(false);

		Component loginForm = buildLoginLayout();
		addComponent(loginForm);
		setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);
	}

	private Component buildLoginLayout() throws FrameworkException {
		final VerticalLayout loginPanel = new VerticalLayout();
		loginPanel.setWidth("-1px");
		loginPanel.setMargin(false);
		Responsive.makeResponsive(loginPanel);
		loginPanel.addStyleName(Style.LOGIN_PANEL);

		applicationLabelsLayout = buildApplicationLabels();
		loginFieldsLayout = buildLoginFields();
		registrationFieldsLayout = buildRegistrationFields();
		registrationFieldsLayout.setVisible(false);
		registrationSuccessfullLayout = buildRegistrationSuccessfullLayout();
		registrationSuccessfullLayout.setVisible(false);
		loginPanel.addComponent(applicationLabelsLayout);
		loginPanel.addComponent(loginFieldsLayout);
		loginPanel.addComponent(registrationFieldsLayout);
		loginPanel.addComponent(registrationSuccessfullLayout);

		return loginPanel;
	}

	private Component buildLoginFields() throws FrameworkException {
		VerticalLayout fields = new VerticalLayout();
		fields.setWidth("400px");
		fields.setMargin(true);
		fields.setSpacing(true);
		fields.addStyleName("fields");

		Label loginHeaderFld = new Label(PropertyResolver.getPropertyValueByLocale(SystemProperty.LOGIN_HEADER));
		loginHeaderFld.addStyleName(ValoTheme.LABEL_H2);
		loginHeaderFld.addStyleName(ValoTheme.LABEL_BOLD);

		final CTextField username = new CTextField();
		username.setSizeFull();
		username.addStyleName(ValoTheme.TEXTFIELD_LARGE);
		username.setCaptionByKey(SystemProperty.LOGIN_USERNAME);
		username.setPlaceholder(PropertyResolver.getPropertyValueByLocale(SystemProperty.LOGIN_USERNAME));
		username.setIcon(FontAwesome.USER);
		username.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

		final CPasswordField password = new CPasswordField();
		password.setSizeFull();
		password.addStyleName(ValoTheme.TEXTFIELD_LARGE);
		password.setCaptionByKey(SystemProperty.LOGIN_PASSWORD);
		password.setPlaceholder(PropertyResolver.getPropertyValueByLocale(SystemProperty.LOGIN_PASSWORD));
		password.setIcon(FontAwesome.LOCK);
		password.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

		final CButton signin = new CButton();
		signin.setSizeFull();
		signin.addStyleName(ValoTheme.BUTTON_LARGE);
		signin.setCaptionByKey(SystemProperty.LOGIN_BUTTON_LOGIN);
		signin.addStyleName(ValoTheme.BUTTON_PRIMARY);
		signin.setClickShortcut(KeyCode.ENTER);
		signin.focus();

		final CButton registerFld = new CButton();
		registerFld.addStyleName(ValoTheme.BUTTON_LARGE);
		registerFld.setCaptionByKey(SystemProperty.LOGIN_REGISTER_HERE);
		registerFld.addStyleName(ValoTheme.BUTTON_LINK);
		registerFld.addStyleName(Style.REGISTER_LINK_BUTTON);
		registerFld.setVisible(false);

		Configuration enableRegistrationConfig = ConfigurationServiceFacade.get(UI.getCurrent())
				.getByCode(ConfigurationProperty.APPLICATION_ENABLE_REGISTRATION);
		if (enableRegistrationConfig != null && enableRegistrationConfig.getBoolean() != null
				&& enableRegistrationConfig.getBoolean()) {
			registerFld.setVisible(true);
		}

		fields.addComponents(loginHeaderFld, username, password, new Label(), signin, registerFld);
		fields.setComponentAlignment(signin, Alignment.BOTTOM_LEFT);
		fields.setComponentAlignment(registerFld, Alignment.BOTTOM_RIGHT);

		signin.addClickListener(new ClickListener() {

			private static final long serialVersionUID = -232691329181636057L;

			@Override
			public void buttonClick(final ClickEvent event) {
				try {
					IApplicationUserService applicationUserService = ContextProvider
							.getBean(IApplicationUserService.class);
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

		registerFld.addClickListener(new ClickListener() {

			private static final long serialVersionUID = -3880882585537226501L;

			@Override
			public void buttonClick(ClickEvent event) {
				registrationFieldsLayout.setVisible(true);
				loginFieldsLayout.setVisible(false);
				registrationSuccessfullLayout.setVisible(false);
			}
		});
		return fields;
	}

	private Component buildRegistrationFields() {
		VerticalLayout fields = new VerticalLayout();
		fields.setMargin(true);
		fields.setSpacing(true);
		fields.setWidth("600px");

		Label registrationHeaderFld = new Label(
				PropertyResolver.getPropertyValueByLocale(RegistrationProperty.REGISTER_HEADER));
		registrationHeaderFld.addStyleName(ValoTheme.LABEL_H2);
		registrationHeaderFld.addStyleName(ValoTheme.LABEL_BOLD);

		Label errorLbl = new Label();
		errorLbl.setContentMode(ContentMode.HTML);
		errorLbl.addStyleName(ValoTheme.LABEL_FAILURE);
		errorLbl.addStyleName(ValoTheme.LABEL_H3);
		errorLbl.setVisible(false);
		errorLbl.setSizeFull();

		final CTextField firstNameFld = new CTextField();
		firstNameFld.setSizeFull();
		firstNameFld.addStyleName(ValoTheme.TEXTFIELD_LARGE);
		firstNameFld.setCaptionByKey(RegistrationProperty.REGISTER_FIRST_NAME);
		firstNameFld
				.setPlaceholder(PropertyResolver.getPropertyValueByLocale(RegistrationProperty.REGISTER_FIRST_NAME));
		firstNameFld.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		firstNameFld.setRequiredIndicatorVisible(true);

		final CTextField lastNameFld = new CTextField();
		lastNameFld.setSizeFull();
		lastNameFld.addStyleName(ValoTheme.TEXTFIELD_LARGE);
		lastNameFld.setCaptionByKey(RegistrationProperty.REGISTER_LAST_NAME);
		lastNameFld.setPlaceholder(PropertyResolver.getPropertyValueByLocale(RegistrationProperty.REGISTER_LAST_NAME));
		lastNameFld.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		lastNameFld.setRequiredIndicatorVisible(true);

		final CPopupDateField dobFld = new CPopupDateField();
		dobFld.setSizeFull();
		dobFld.addStyleName(ValoTheme.DATEFIELD_LARGE);
		dobFld.setCaptionByKey(RegistrationProperty.REGISTER_DATE_OF_BIRTH);
		dobFld.setPlaceholder(PropertyResolver.getPropertyValueByLocale(RegistrationProperty.REGISTER_DATE_OF_BIRTH));
		dobFld.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		dobFld.setDateFormat(Constants.SIMPLE_DATE_FORMAT.toPattern());
		dobFld.setRangeEnd(LocalDate.now());
		dobFld.setRequiredIndicatorVisible(true);

		final GenderSelect genderFld = new GenderSelect();
		genderFld.setSizeFull();
		genderFld.addStyleName(ValoTheme.COMBOBOX_LARGE);
		genderFld.setCaptionByKey(RegistrationProperty.REGISTER_GENDER);
		genderFld.setPlaceholder(PropertyResolver.getPropertyValueByLocale(RegistrationProperty.REGISTER_GENDER));

		final CTextField contactNumberFld = new CTextField();
		contactNumberFld.setSizeFull();
		contactNumberFld.addStyleName(ValoTheme.DATEFIELD_LARGE);
		contactNumberFld.setCaptionByKey(RegistrationProperty.REGISTER_MOBILE_NUMBER);
		contactNumberFld
				.setPlaceholder(PropertyResolver.getPropertyValueByLocale(RegistrationProperty.REGISTER_MOBILE_NUMBER));
		contactNumberFld.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		contactNumberFld.setRequiredIndicatorVisible(true);

		final CTextField emailFld = new CTextField();
		emailFld.setSizeFull();
		emailFld.addStyleName(ValoTheme.DATEFIELD_LARGE);
		emailFld.setCaptionByKey(RegistrationProperty.REGISTER_EMAIL);
		emailFld.setPlaceholder(PropertyResolver.getPropertyValueByLocale(RegistrationProperty.REGISTER_EMAIL));
		emailFld.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		emailFld.setRequiredIndicatorVisible(true);

		final CPasswordField passwordFld = new CPasswordField();
		passwordFld.setSizeFull();
		passwordFld.addStyleName(ValoTheme.DATEFIELD_LARGE);
		passwordFld.setCaptionByKey(RegistrationProperty.REGISTER_PASSWORD);
		passwordFld.setPlaceholder(PropertyResolver.getPropertyValueByLocale(RegistrationProperty.REGISTER_PASSWORD));
		passwordFld.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		passwordFld.setRequiredIndicatorVisible(true);

		final CPasswordField confirmPasswordFld = new CPasswordField();
		confirmPasswordFld.setSizeFull();
		confirmPasswordFld.addStyleName(ValoTheme.DATEFIELD_LARGE);
		confirmPasswordFld.setCaptionByKey(RegistrationProperty.REGISTER_CONFIRM_PASSWORD);
		confirmPasswordFld.setPlaceholder(
				PropertyResolver.getPropertyValueByLocale(RegistrationProperty.REGISTER_CONFIRM_PASSWORD));
		confirmPasswordFld.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		confirmPasswordFld.setRequiredIndicatorVisible(true);

		HorizontalLayout horizontalLayout = new HorizontalLayout();
//		CCheckBox termsAndConditionCheckFld = new CCheckBox();
//		horizontalLayout.addComponent(termsAndConditionCheckFld);
//		Link link = new Link();
//		link.setCaption(
//				PropertyResolver.getPropertyValueByLocale(RegistrationProperty.REGISTER_AGREE_TERMS_AND_CONDITIONS));
//		link.addStyleName(Style.REGISTER_LINK);
//		horizontalLayout.addComponent(link);

		final CButton registerFld = new CButton();
		registerFld.setSizeFull();
		registerFld.addStyleName(ValoTheme.BUTTON_LARGE);
		registerFld.setCaptionByKey(RegistrationProperty.REGISTER_BUTTON_CONFIRM);
		registerFld.setIcon(FontAwesome.USER);
		registerFld.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

		final CButton backToLoginFld = new CButton();
		backToLoginFld.addStyleName(ValoTheme.BUTTON_LARGE);
		backToLoginFld.setCaptionByKey(RegistrationProperty.REGISTER_BACK_TO_LOGIN);
		backToLoginFld.addStyleName(ValoTheme.BUTTON_LINK);
		backToLoginFld.setIcon(VaadinIcons.ARROW_BACKWARD);
		backToLoginFld.addStyleName(Style.REGISTER_LINK_BUTTON);

		fields.addComponents(registrationHeaderFld, errorLbl, firstNameFld, lastNameFld, dobFld, contactNumberFld,
				emailFld, passwordFld, confirmPasswordFld, horizontalLayout, registerFld, backToLoginFld);
		fields.setComponentAlignment(backToLoginFld, Alignment.BOTTOM_RIGHT);

		registerFld.addClickListener(new ClickListener() {

			private static final long serialVersionUID = -7736352842423878879L;

			@Override
			public void buttonClick(ClickEvent event) {
				registerUser();
			}

			private void clearFields() {
				firstNameFld.clear();
				lastNameFld.clear();
				dobFld.clear();
				contactNumberFld.clear();
				genderFld.clear();
				emailFld.clear();
				passwordFld.clear();
				confirmPasswordFld.clear();
//				termsAndConditionCheckFld.clear();
			}

			private void registerUser() {
				errorLbl.setVisible(false);

				IApplicationUserService applicationUserService = ContextProvider.getBean(IApplicationUserService.class);
				ApplicationUserVO vo = new ApplicationUserVO();
				vo.setFirstName(firstNameFld.getValue());
				vo.setLastName(lastNameFld.getValue());
				vo.setDateOfBirth(dobFld.getValue());
				vo.setMobileNumber(contactNumberFld.getValue());
				vo.setGenderId(genderFld.getLongValue());
				vo.setUsername(emailFld.getValue());
				vo.setEmail(emailFld.getValue());
				vo.setPassword(passwordFld.getValue());
				vo.setPasswordConfirm(confirmPasswordFld.getValue());
//				vo.setTermsAccepted(termsAndConditionCheckFld.getValue());
				try {
					SecurityValidation securityValidation = applicationUserService.registerUser(vo);
					if (!securityValidation.isSuccess()) {
						errorLbl.setVisible(true);
						errorLbl.setValue(PropertyResolver.getPropertyValueByLocale(securityValidation.getMessageKey(),
								securityValidation.getArgs(t -> {
									if (t.isKey()) {
										return PropertyResolver.getPropertyValueByLocale(t.getValue());
									} else {
										return t.getValue();
									}
								})));
					} else {
						clearFields();
						loginFieldsLayout.setVisible(false);
						registrationFieldsLayout.setVisible(false);
						registrationSuccessfullLayout.setVisible(true);
					}
				} catch (FrameworkException e) {
					logger.error(e.getMessage(), e);
				}
			}
		});

		backToLoginFld.addClickListener(new ClickListener() {

			private static final long serialVersionUID = -7736352842423878879L;

			@Override
			public void buttonClick(ClickEvent event) {
				loginFieldsLayout.setVisible(true);
				registrationFieldsLayout.setVisible(false);
				registrationSuccessfullLayout.setVisible(false);
			}
		});

		return fields;
	}

	private Component buildRegistrationSuccessfullLayout() {
		VerticalLayout fields = new VerticalLayout();
		fields.setMargin(true);
		fields.setSpacing(true);
		fields.setWidth("600px");

		Label registrationHeaderFld = new Label(
				PropertyResolver.getPropertyValueByLocale(RegistrationProperty.REGISTER_HEADER_SUCCESSFULL));
		registrationHeaderFld.addStyleName(ValoTheme.LABEL_H2);
		registrationHeaderFld.addStyleName(ValoTheme.LABEL_BOLD);

		Label successLbl = new Label();
		successLbl.setContentMode(ContentMode.HTML);
		successLbl.addStyleName(ValoTheme.LABEL_SUCCESS);
		successLbl.addStyleName(ValoTheme.LABEL_H2);
		successLbl.setSizeFull();
		successLbl.setValue(
				PropertyResolver.getPropertyValueByLocale(RegistrationProperty.REGISTER_USER_CREATED_SUCCESSFULLY));

		final CButton backToLoginFld = new CButton();
		backToLoginFld.addStyleName(ValoTheme.BUTTON_LARGE);
		backToLoginFld.setCaptionByKey(RegistrationProperty.REGISTER_BACK_TO_LOGIN);
		backToLoginFld.addStyleName(ValoTheme.BUTTON_LINK);
		backToLoginFld.setIcon(VaadinIcons.ARROW_BACKWARD);
		backToLoginFld.addStyleName(Style.REGISTER_LINK_BUTTON);

		fields.addComponents(registrationHeaderFld, successLbl, backToLoginFld);
		fields.setComponentAlignment(backToLoginFld, Alignment.BOTTOM_RIGHT);

		backToLoginFld.addClickListener(new ClickListener() {

			private static final long serialVersionUID = -7736352842423878879L;

			@Override
			public void buttonClick(ClickEvent event) {
				loginFieldsLayout.setVisible(true);
				registrationFieldsLayout.setVisible(false);
				registrationSuccessfullLayout.setVisible(false);
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

	private Component buildApplicationLabels() throws FrameworkException {
		VerticalLayout labels = new VerticalLayout();
		labels.setMargin(true);
		// labels.setWidth("100%");
		labels.setSizeFull();
		labels.addStyleName(Style.APPLICATION_NAME_HEADER);
		// labels.addStyleName(ValoTheme.LAYOUT_WELL);
		// labels.addStyleName(Style.LABELS);

		CLabel welcomeApplicationNameLbl = new CLabel();
		welcomeApplicationNameLbl.setValueByKey(SystemProperty.LOGIN_WELCOME);
		welcomeApplicationNameLbl.setSizeUndefined();
		welcomeApplicationNameLbl.addStyleName(ValoTheme.LABEL_H2);
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
