package software.simple.solutions.framework.core.web.view.configuration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.appreciated.card.Card;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasValue.ValueChangeEvent;
import com.vaadin.flow.component.HasValue.ValueChangeListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.server.Command;
import com.vaadin.flow.server.VaadinSession;

import software.simple.solutions.framework.core.annotations.CxodeConfigurationComponent;
import software.simple.solutions.framework.core.constants.Constants;
import software.simple.solutions.framework.core.entities.ApplicationUserConfiguration;
import software.simple.solutions.framework.core.entities.Configuration;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.ConfigurationProperty;
import software.simple.solutions.framework.core.properties.SystemProperty;
import software.simple.solutions.framework.core.service.facade.ApplicationUserConfigurationServiceFacade;
import software.simple.solutions.framework.core.service.facade.ConfigurationServiceFacade;
import software.simple.solutions.framework.core.service.facade.MailServiceFacade;
import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.util.SessionHolder;
import software.simple.solutions.framework.core.valueobjects.ApplicationUserConfigurationVO;
import software.simple.solutions.framework.core.valueobjects.ConfigurationVO;
import software.simple.solutions.framework.core.web.DetailsWindow;
import software.simple.solutions.framework.core.web.components.CButton;
import software.simple.solutions.framework.core.web.components.CCheckBox;
import software.simple.solutions.framework.core.web.components.CVerticalLayout;
import software.simple.solutions.framework.core.web.components.CDiscreetNumberField;
import software.simple.solutions.framework.core.web.components.CPasswordField;
import software.simple.solutions.framework.core.web.components.CTextArea;
import software.simple.solutions.framework.core.web.components.CTextField;
import software.simple.solutions.framework.core.web.components.NotificationBuilder;
import software.simple.solutions.framework.core.web.components.Panel;

@CxodeConfigurationComponent(order = 2, captionKey = ConfigurationProperty.SMTP_CONFIGURATION)
public class SmtpServerConfiguration extends CVerticalLayout {

	private static final long serialVersionUID = -4290908153354032925L;

	private static final Logger logger = LogManager.getLogger(SmtpServerConfiguration.class);

	private CTextField hostFld;
	private CDiscreetNumberField portFld;
	private CTextField usernameFld;
	private CPasswordField passwordFld;
	private CTextField systemEmailFld;
	private CCheckBox enableSmtp;
	private ProgressBar progressBar;

	private CButton persistBtn;
//	private CButton validateSmtpConfigurationBtn;
	private CButton validateSmtpStateFld;
	private Div errorMessageFld;

	private SessionHolder sessionHolder;
	private UI ui;

	private CVerticalLayout validateSmtpConfigurationLayout;

	public SmtpServerConfiguration() throws FrameworkException {
		ui = UI.getCurrent();
		sessionHolder = (SessionHolder) VaadinSession.getCurrent().getAttribute(Constants.SESSION_HOLDER);
		
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		Panel smtpSettingsPanel = new Panel();
		smtpSettingsPanel.setHeaderKey(ConfigurationProperty.SMTP_CONFIGURATION);
		horizontalLayout.add(smtpSettingsPanel);
		add(horizontalLayout);
		
		CVerticalLayout smtpSettingsLayout = new CVerticalLayout();
		smtpSettingsPanel.add(smtpSettingsLayout);
		
		enableSmtp = smtpSettingsLayout.add(CCheckBox.class, ConfigurationProperty.SMTP_ENABLE);
		hostFld = smtpSettingsLayout.add(CTextField.class, ConfigurationProperty.SMTP_HOST);
		portFld = smtpSettingsLayout.add(CDiscreetNumberField.class, ConfigurationProperty.SMTP_PORT);
		usernameFld = smtpSettingsLayout.add(CTextField.class, ConfigurationProperty.SMTP_USERNAME);
		passwordFld = smtpSettingsLayout.add(CPasswordField.class, ConfigurationProperty.SMTP_PASSWORD);
		systemEmailFld = smtpSettingsLayout.add(CTextField.class, ConfigurationProperty.SMTP_SYSTEM_EMAIL);

//		validateSmtpConfigurationBtn = smtpSettingsLayout.add(CButton.class, ConfigurationProperty.SMTP_VALIDATE_CONFIGURATION);
//		validateSmtpConfigurationBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

		persistBtn = smtpSettingsLayout.add(CButton.class, SystemProperty.SYSTEM_BUTTON_SUBMIT);
		persistBtn.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_PRIMARY);
		persistBtn.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {

			private static final long serialVersionUID = 6213590339018687407L;

			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				List<ConfigurationVO> configurations = new ArrayList<ConfigurationVO>();
				configurations.add(getValue(ConfigurationProperty.SMTP_ENABLE, enableSmtp.getValue().toString()));
				configurations.add(getValue(ConfigurationProperty.SMTP_HOST, hostFld.getValue()));
				configurations.add(getValue(ConfigurationProperty.SMTP_PORT, portFld.getStringValue()));
				configurations.add(getValue(ConfigurationProperty.SMTP_USERNAME, usernameFld.getValue()));
				configurations.add(getValue(ConfigurationProperty.SMTP_PASSWORD, passwordFld.getValue()));
				configurations.add(getValue(ConfigurationProperty.SMTP_SYSTEM_EMAIL, systemEmailFld.getValue()));
				try {
					ConfigurationServiceFacade.get(UI.getCurrent()).update(configurations);
					NotificationBuilder.buildSuccess(PropertyResolver
							.getPropertyValueByLocale(SystemProperty.UPDATE_SUCCESSFULL, UI.getCurrent().getLocale()));
				} catch (FrameworkException e) {
					logger.error(e.getMessage(), e);
					DetailsWindow.build(e);
				}
			}
		});

		Panel validateSmtpConfigurationPanel = new Panel();
		validateSmtpConfigurationPanel.setHeaderKey(ConfigurationProperty.SMTP_VALIDATE_CONFIGURATION);
		horizontalLayout.add(validateSmtpConfigurationPanel);
		
		validateSmtpConfigurationLayout = new CVerticalLayout();
		validateSmtpConfigurationPanel.add(validateSmtpConfigurationLayout);
		
		validateSmtpStateFld = validateSmtpConfigurationLayout.add(CButton.class, null);
		validateSmtpStateFld.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_ERROR);
		validateSmtpStateFld.setVisible(false);

		errorMessageFld = validateSmtpConfigurationLayout.add(Div.class, null);
		errorMessageFld.setVisible(false);
		errorMessageFld.getStyle().set("color", "var(--lumo-error-text-color)");
		CTextField validateSmtpEmailFld = validateSmtpConfigurationLayout.add(CTextField.class,
				ConfigurationProperty.SMTP_VALIDATE_EMAIL);
		CTextField subjectFld = validateSmtpConfigurationLayout.add(CTextField.class,
				ConfigurationProperty.SMTP_VALIDATE_SUBJECT);
		CTextArea messageFld = validateSmtpConfigurationLayout.add(CTextArea.class,
				ConfigurationProperty.SMTP_VALIDATE_MESSAGE);
		
		try {
			List<ApplicationUserConfiguration> smtpValidateConfigurations = ApplicationUserConfigurationServiceFacade
					.get(UI.getCurrent())
					.getSMTPValidateConfiguration(sessionHolder.getApplicationUser().getId());
			if (smtpValidateConfigurations != null && !smtpValidateConfigurations.isEmpty()) {
				for (ApplicationUserConfiguration applicationUserConfiguration : smtpValidateConfigurations) {
					switch (applicationUserConfiguration.getCode()) {
					case ConfigurationProperty.SMTP_VALIDATE_SUBJECT:
						subjectFld.setValue(applicationUserConfiguration.getValue());
						break;
					case ConfigurationProperty.SMTP_VALIDATE_MESSAGE:
						messageFld.setValue(applicationUserConfiguration.getValue());
						break;
					case ConfigurationProperty.SMTP_VALIDATE_EMAIL:
						validateSmtpEmailFld.setValue(applicationUserConfiguration.getValue());
						break;

					default:
						break;
					}
				}
			}
		} catch (FrameworkException e) {
			logger.error(e.getMessage(), e);
		}
		
		HorizontalLayout validateSmtpSettingsActionLayout = new HorizontalLayout();
		validateSmtpConfigurationLayout.add(validateSmtpSettingsActionLayout);

		CButton validateSmtpSettingsBtn = new CButton();
		validateSmtpSettingsActionLayout.add(validateSmtpSettingsBtn);
		validateSmtpSettingsBtn.setCaptionByKey(SystemProperty.SYSTEM_BUTTON_VALIDATE);

		progressBar = new ProgressBar();
		progressBar.setIndeterminate(true);
		progressBar.setVisible(false);
		validateSmtpSettingsActionLayout.add(progressBar);

		validateSmtpSettingsBtn.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {

			private static final long serialVersionUID = 8039325419724076493L;

			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				new ValidateSmtpSettings(subjectFld.getValue(), messageFld.getValue(),
						validateSmtpEmailFld.getValue()).start();
			}
		});

		setValues();
	}

	class ValidateSmtpSettings extends Thread {

		private String subject;
		private String message;
		private String validateSmtpEmail;

		public ValidateSmtpSettings(String subject, String message, String validateSmtpEmail) {
			this.subject = subject;
			this.message = message;
			this.validateSmtpEmail = validateSmtpEmail;
		}

		@Override
		public void run() {
			ui.access(new Command() {

				private static final long serialVersionUID = 6522653890668126128L;

				@Override
				public void execute() {
					progressBar.setVisible(true);
				}
			});
			try {
				List<ApplicationUserConfigurationVO> applicationUserConfigurations = new ArrayList<ApplicationUserConfigurationVO>();
				applicationUserConfigurations.add(getValue(ConfigurationProperty.SMTP_VALIDATE_SUBJECT, subject));
				applicationUserConfigurations.add(getValue(ConfigurationProperty.SMTP_VALIDATE_MESSAGE, message));
				applicationUserConfigurations
						.add(getValue(ConfigurationProperty.SMTP_VALIDATE_EMAIL, validateSmtpEmail));
				ApplicationUserConfigurationServiceFacade.get(UI.getCurrent()).update(applicationUserConfigurations);

				boolean sentTestMail = MailServiceFacade.get(UI.getCurrent()).sendTestMail(hostFld.getValue(),
						portFld.getStringValue(), usernameFld.getValue(), passwordFld.getValue(),
						systemEmailFld.getValue(), subject, message, validateSmtpEmail);
				if (sentTestMail) {
					ui.access(new Command() {

						private static final long serialVersionUID = -3172433779476538277L;

						@Override
						public void execute() {
							validateSmtpStateFld.setIcon(VaadinIcon.CHECK_CIRCLE_O.create());
						}
					});
				}
			} catch (FrameworkException e) {
				logger.error(e.getMessage(), e);
				ui.access(new Command() {

					private static final long serialVersionUID = 4552331834375760147L;

					@Override
					public void execute() {
						validateSmtpStateFld.setIcon(VaadinIcon.WARNING.create());
						errorMessageFld.setVisible(true);
						errorMessageFld.setText(e.getMessage());
						// MessageWindow messageWindow = new MessageWindow();
						// messageWindow.setErrorMessage(e);
					}
				});
			}
			ui.access(new Command() {

				private static final long serialVersionUID = -3727974044656343508L;

				@Override
				public void execute() {
					progressBar.setVisible(false);
					validateSmtpStateFld.setVisible(true);
				}
			});
		}

		private ApplicationUserConfigurationVO getValue(String code, String value) {
			ApplicationUserConfigurationVO vo = new ApplicationUserConfigurationVO();
			vo.setCode(code);
			vo.setValue(value);
			vo.setApplicationUserId(sessionHolder.getApplicationUser().getId());
			vo.setUpdatedBy(sessionHolder.getApplicationUser().getId());
			vo.setUpdatedDate(LocalDateTime.now());
			return vo;
		}
	}

	private void setValues() throws FrameworkException {
		enableSmtp.addValueChangeListener(new ValueChangeListener<ValueChangeEvent<Boolean>>() {

			private static final long serialVersionUID = 2725360827088064484L;

			@Override
			public void valueChanged(ValueChangeEvent<Boolean> event) {
				enableFields(event.getValue());
			}
		});
		enableFields(false);
		List<Configuration> configurations = ConfigurationServiceFacade.get(UI.getCurrent())
				.getMailServerConfiguration();
		if (configurations != null && !configurations.isEmpty()) {
			for (Configuration configuration : configurations) {
				switch (configuration.getCode()) {
				case ConfigurationProperty.SMTP_ENABLE:
					enableSmtp.setValue(Boolean.parseBoolean(configuration.getValue()));
					enableFields(Boolean.parseBoolean(configuration.getValue()));
					break;
				case ConfigurationProperty.SMTP_HOST:
					hostFld.setValue(configuration.getValue());
					break;
				case ConfigurationProperty.SMTP_PORT:
					portFld.setLongValue(configuration.getLong());
					break;
				case ConfigurationProperty.SMTP_USERNAME:
					usernameFld.setValue(configuration.getValue());
					break;
				case ConfigurationProperty.SMTP_PASSWORD:
					passwordFld.setValue(configuration.getValue());
					break;
				case ConfigurationProperty.SMTP_SYSTEM_EMAIL:
					systemEmailFld.setValue(configuration.getValue());
					break;
				default:
					break;
				}
			}
		}
	}

	private void enableFields(boolean enabled) {
		hostFld.setEnabled(enabled);
		portFld.setEnabled(enabled);
		usernameFld.setEnabled(enabled);
		passwordFld.setEnabled(enabled);
		systemEmailFld.setEnabled(enabled);
		validateSmtpConfigurationLayout.setEnabled(enabled);
	}

	private ConfigurationVO getValue(String code, Object value) {
		ConfigurationVO vo = new ConfigurationVO();
		vo.setCode(code);
		vo.setValue(value == null ? null : value.toString());

		vo.setUpdatedBy(sessionHolder.getApplicationUser().getId());
		vo.setUpdatedDate(LocalDateTime.now());
		return vo;
	}

}
