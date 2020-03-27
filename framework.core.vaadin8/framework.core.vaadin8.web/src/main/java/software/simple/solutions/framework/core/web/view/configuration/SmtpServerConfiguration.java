package software.simple.solutions.framework.core.web.view.configuration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import software.simple.solutions.framework.core.annotations.CxodeConfigurationComponent;
import software.simple.solutions.framework.core.components.CButton;
import software.simple.solutions.framework.core.components.CCheckBox;
import software.simple.solutions.framework.core.components.CDiscreetNumberField;
import software.simple.solutions.framework.core.components.CGridLayout;
import software.simple.solutions.framework.core.components.CPasswordField;
import software.simple.solutions.framework.core.components.CTextArea;
import software.simple.solutions.framework.core.components.CTextField;
import software.simple.solutions.framework.core.components.MessageWindowHandler;
import software.simple.solutions.framework.core.components.NotificationWindow;
import software.simple.solutions.framework.core.components.SessionHolder;
import software.simple.solutions.framework.core.constants.Style;
import software.simple.solutions.framework.core.entities.ApplicationUserConfiguration;
import software.simple.solutions.framework.core.entities.Configuration;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.icons.CxodeIcons;
import software.simple.solutions.framework.core.properties.ConfigurationProperty;
import software.simple.solutions.framework.core.properties.SystemProperty;
import software.simple.solutions.framework.core.service.facade.ApplicationUserConfigurationServiceFacade;
import software.simple.solutions.framework.core.service.facade.ConfigurationServiceFacade;
import software.simple.solutions.framework.core.service.facade.MailServiceFacade;
import software.simple.solutions.framework.core.util.ComponentUtil;
import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.valueobjects.ApplicationUserConfigurationVO;
import software.simple.solutions.framework.core.valueobjects.ConfigurationVO;

@CxodeConfigurationComponent(order = 2, captionKey = ConfigurationProperty.SMTP_CONFIGURATION)
public class SmtpServerConfiguration extends CGridLayout {

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
	private CButton validateSmtpConfigurationBtn;
	private CButton validateSmtpStateFld;

	private SessionHolder sessionHolder;
	private UI ui;

	public SmtpServerConfiguration() throws FrameworkException {
		ui = UI.getCurrent();
		sessionHolder = (SessionHolder) UI.getCurrent().getData();
		setSpacing(true);
		int i = 0;
		enableSmtp = addField(CCheckBox.class, ConfigurationProperty.SMTP_ENABLE, 0, ++i);
		hostFld = addField(CTextField.class, ConfigurationProperty.SMTP_HOST, 0, ++i);
		portFld = addField(CDiscreetNumberField.class, ConfigurationProperty.SMTP_PORT, 0, ++i);
		usernameFld = addField(CTextField.class, ConfigurationProperty.SMTP_USERNAME, 0, ++i);
		passwordFld = addField(CPasswordField.class, ConfigurationProperty.SMTP_PASSWORD, 0, ++i);
		systemEmailFld = addField(CTextField.class, ConfigurationProperty.SMTP_SYSTEM_EMAIL, 0, ++i);

		validateSmtpConfigurationBtn = addField(CButton.class, 0, ++i);
		validateSmtpConfigurationBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
		validateSmtpConfigurationBtn.setCaptionByKey(ConfigurationProperty.SMTP_VALIDATE_CONFIGURATION);

		persistBtn = addField(CButton.class, 0, ++i);
		persistBtn.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		persistBtn.setCaptionByKey(SystemProperty.SYSTEM_BUTTON_SUBMIT);

		persistBtn.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 3418895031034445319L;

			@Override
			public void buttonClick(ClickEvent event) {
				List<ConfigurationVO> configurations = new ArrayList<ConfigurationVO>();
				configurations.add(getValue(ConfigurationProperty.SMTP_ENABLE, enableSmtp.getValue().toString()));
				configurations.add(getValue(ConfigurationProperty.SMTP_HOST, hostFld.getValue()));
				configurations.add(getValue(ConfigurationProperty.SMTP_PORT, portFld.getValue()));
				configurations.add(getValue(ConfigurationProperty.SMTP_USERNAME, usernameFld.getValue()));
				configurations.add(getValue(ConfigurationProperty.SMTP_PASSWORD, passwordFld.getValue()));
				configurations.add(getValue(ConfigurationProperty.SMTP_SYSTEM_EMAIL, systemEmailFld.getValue()));
				try {
					ConfigurationServiceFacade.get(UI.getCurrent()).update(configurations);
					NotificationWindow.notificationNormalWindow(SystemProperty.UPDATE_SUCCESSFULL);
				} catch (FrameworkException e) {
					logger.error(e.getMessage(), e);
					new MessageWindowHandler(e);
				}
			}
		});

		setValues();

		validateSmtpConfigurationBtn.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 6163763552461375138L;

			@Override
			public void buttonClick(ClickEvent event) {
				Window window = new Window();
				UI.getCurrent().addWindow(window);
				window.setCaption(
						PropertyResolver.getPropertyValueByLocale(ConfigurationProperty.SMTP_VALIDATE_CONFIGURATION, UI.getCurrent().getLocale()));
				CGridLayout validateSmtpLayout = ComponentUtil.createGrid();
				validateSmtpLayout.setMargin(true);

				CTextField subjectFld = validateSmtpLayout.addField(CTextField.class,
						ConfigurationProperty.SMTP_VALIDATE_SUBJECT, 0, 0);
				CTextArea messageFld = validateSmtpLayout.addField(CTextArea.class,
						ConfigurationProperty.SMTP_VALIDATE_MESSAGE, 0, 1);
				CTextField validateSmtpEmailFld = validateSmtpLayout.addField(CTextField.class,
						ConfigurationProperty.SMTP_VALIDATE_EMAIL, 0, 2);

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

				HorizontalLayout validateSmtpSettingsActionLayout = validateSmtpLayout.addField(HorizontalLayout.class,
						0, 3);

				CButton validateSmtpSettingsBtn = new CButton();
				validateSmtpSettingsActionLayout.addComponent(validateSmtpSettingsBtn);
				validateSmtpSettingsBtn.setCaptionByKey(SystemProperty.SYSTEM_BUTTON_VALIDATE);

				progressBar = new ProgressBar();
				progressBar.setIndeterminate(true);
				progressBar.setVisible(false);
				validateSmtpSettingsActionLayout.addComponent(progressBar);

				validateSmtpStateFld = new CButton();
				validateSmtpSettingsActionLayout.addComponent(validateSmtpStateFld);
				validateSmtpStateFld.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
				validateSmtpStateFld.addStyleName(ValoTheme.BUTTON_BORDERLESS);
				validateSmtpStateFld.addStyleName(Style.RESIZED_ICON);
				validateSmtpStateFld.setVisible(false);

				window.setContent(validateSmtpLayout);
				window.center();
				window.setModal(true);
				validateSmtpSettingsBtn.addClickListener(new ClickListener() {

					private static final long serialVersionUID = 8578083696892557356L;

					@Override
					public void buttonClick(ClickEvent event) {
						new ValidateSmtpSettings(subjectFld.getValue(), messageFld.getValue(),
								validateSmtpEmailFld.getValue()).start();
					}

				});
			}
		});
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
			ui.access(new Runnable() {

				@Override
				public void run() {
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
						portFld.getValue(), usernameFld.getValue(), passwordFld.getValue(), systemEmailFld.getValue(),
						subject, message, validateSmtpEmail);
				if (sentTestMail) {
					ui.access(new Runnable() {

						@Override
						public void run() {
							validateSmtpStateFld.setIcon(CxodeIcons.SUCCESS);
						}
					});
				}
			} catch (FrameworkException e) {
				logger.error(e.getMessage(), e);
				ui.access(new Runnable() {

					@Override
					public void run() {
						validateSmtpStateFld.setIcon(CxodeIcons.FAIL);
						// MessageWindow messageWindow = new MessageWindow();
						// messageWindow.setErrorMessage(e);
					}
				});
			}
			ui.access(new Runnable() {

				@Override
				public void run() {
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
		enableSmtp.addValueChangeListener(new ValueChangeListener<Boolean>() {

			private static final long serialVersionUID = 6436331570102432249L;

			@Override
			public void valueChange(ValueChangeEvent<Boolean> event) {
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
					portFld.setValue(configuration.getValue());
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
		validateSmtpConfigurationBtn.setEnabled(enabled);
	}

	private ConfigurationVO getValue(String code, String value) {
		ConfigurationVO vo = new ConfigurationVO();
		vo.setCode(code);
		vo.setValue(value);

		vo.setUpdatedBy(sessionHolder.getApplicationUser().getId());
		vo.setUpdatedDate(LocalDateTime.now());
		return vo;
	}

}
