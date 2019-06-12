package software.simple.solutions.framework.core.web.view.configuration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

import software.simple.solutions.framework.core.annotations.CxodeConfigurationComponent;
import software.simple.solutions.framework.core.components.CButton;
import software.simple.solutions.framework.core.components.CDiscreetNumberField;
import software.simple.solutions.framework.core.components.CGridLayout;
import software.simple.solutions.framework.core.components.CTextField;
import software.simple.solutions.framework.core.components.MessageWindowHandler;
import software.simple.solutions.framework.core.components.NotificationWindow;
import software.simple.solutions.framework.core.components.SessionHolder;
import software.simple.solutions.framework.core.entities.Configuration;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.ConfigurationProperty;
import software.simple.solutions.framework.core.properties.SystemProperty;
import software.simple.solutions.framework.core.service.facade.ConfigurationServiceFacade;
import software.simple.solutions.framework.core.valueobjects.ConfigurationVO;

@CxodeConfigurationComponent(order = 3, captionKey = ConfigurationProperty.PASSWORD_SECURITY_CONFIGURATION)
public class SecurityConfiguration extends CGridLayout {

	private static final long serialVersionUID = -4290908153354032925L;

	private static final Logger logger = LogManager.getLogger(SecurityConfiguration.class);

	private CTextField requestPasswordResetLinkFld;
	private CDiscreetNumberField requestPasswordResetValidityFld;
	private CDiscreetNumberField minimumPasswordLengthFld;
	private CDiscreetNumberField maximumPasswordLengthFld;
	private CDiscreetNumberField numberOfUpperCaseFld;
	private CDiscreetNumberField numberOfSpecialCharactersFld;
	private CDiscreetNumberField numberOfDigits;
	private CDiscreetNumberField numberOfInvalidAttemptsFld;
	private CDiscreetNumberField daysBeforePasswordExpiresFld;
	private CDiscreetNumberField usedPasswordHistoryFld;

	private CButton persistBtn;
	private SessionHolder sessionHolder;
	private UI ui;

	public SecurityConfiguration() throws FrameworkException {
		ui = UI.getCurrent();
		sessionHolder = (SessionHolder) UI.getCurrent().getData();
		setSpacing(true);
		int i = 0;
		requestPasswordResetLinkFld = addField(CTextField.class,
				ConfigurationProperty.PASSWORD_SECURITY_REQUEST_PASSWORD_RESET_LINK, 0, ++i);
		requestPasswordResetValidityFld = addField(CDiscreetNumberField.class,
				ConfigurationProperty.PASSWORD_SECURITY_REQUEST_PASSWORD_RESET_VALIDITY, 0, ++i);
		minimumPasswordLengthFld = addField(CDiscreetNumberField.class,
				ConfigurationProperty.PASSWORD_SECURITY_MINIMUM_LENGTH, 0, ++i);
		maximumPasswordLengthFld = addField(CDiscreetNumberField.class,
				ConfigurationProperty.PASSWORD_SECURITY_MAXIMUM_LENGTH, 0, ++i);
		numberOfUpperCaseFld = addField(CDiscreetNumberField.class,
				ConfigurationProperty.PASSWORD_SECURITY_NO_OF_UPPERCASE, 0, ++i);
		numberOfSpecialCharactersFld = addField(CDiscreetNumberField.class,
				ConfigurationProperty.PASSWORD_SECURITY_NO_OF_SPECIAL, 0, ++i);
		numberOfDigits = addField(CDiscreetNumberField.class, ConfigurationProperty.PASSWORD_SECURITY_NO_OF_DIGITS, 0,
				++i);
		numberOfInvalidAttemptsFld = addField(CDiscreetNumberField.class,
				ConfigurationProperty.PASSWORD_SECURITY_NO_OF_INVALID_ATTEMPTS, 0, ++i);
		daysBeforePasswordExpiresFld = addField(CDiscreetNumberField.class,
				ConfigurationProperty.PASSWORD_SECURITY_DAYS_BEFORE_EXPIRATION, 0, ++i);
		usedPasswordHistoryFld = addField(CDiscreetNumberField.class,
				ConfigurationProperty.PASSWORD_SECURITY_USED_HISTORY, 0, ++i);

		persistBtn = addField(CButton.class, 0, ++i);
		persistBtn.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		persistBtn.setCaptionByKey(SystemProperty.SYSTEM_BUTTON_SUBMIT);

		persistBtn.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 3418895031034445319L;

			@Override
			public void buttonClick(ClickEvent event) {
				List<ConfigurationVO> configurations = new ArrayList<ConfigurationVO>();
				configurations.add(getValue(ConfigurationProperty.PASSWORD_SECURITY_REQUEST_PASSWORD_RESET_LINK,
						requestPasswordResetLinkFld.getValue()));
				configurations.add(getValue(ConfigurationProperty.PASSWORD_SECURITY_REQUEST_PASSWORD_RESET_VALIDITY,
						requestPasswordResetValidityFld.getLongValue()));
				configurations.add(getValue(ConfigurationProperty.PASSWORD_SECURITY_MINIMUM_LENGTH,
						minimumPasswordLengthFld.getLongValue()));
				configurations.add(getValue(ConfigurationProperty.PASSWORD_SECURITY_MAXIMUM_LENGTH,
						maximumPasswordLengthFld.getLongValue()));
				configurations.add(getValue(ConfigurationProperty.PASSWORD_SECURITY_NO_OF_UPPERCASE,
						numberOfUpperCaseFld.getLongValue()));
				configurations.add(getValue(ConfigurationProperty.PASSWORD_SECURITY_NO_OF_SPECIAL,
						numberOfSpecialCharactersFld.getLongValue()));
				configurations.add(
						getValue(ConfigurationProperty.PASSWORD_SECURITY_NO_OF_DIGITS, numberOfDigits.getLongValue()));
				configurations.add(getValue(ConfigurationProperty.PASSWORD_SECURITY_NO_OF_INVALID_ATTEMPTS,
						numberOfInvalidAttemptsFld.getLongValue()));
				configurations.add(getValue(ConfigurationProperty.PASSWORD_SECURITY_DAYS_BEFORE_EXPIRATION,
						daysBeforePasswordExpiresFld.getLongValue()));
				configurations.add(getValue(ConfigurationProperty.PASSWORD_SECURITY_USED_HISTORY,
						usedPasswordHistoryFld.getLongValue()));
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

	}

	private void setValues() throws FrameworkException {
		List<Configuration> configurations = ConfigurationServiceFacade.get(UI.getCurrent())
				.getPasswordSecurityConfiguration();
		if (configurations != null && !configurations.isEmpty()) {
			for (Configuration configuration : configurations) {
				switch (configuration.getCode()) {
				case ConfigurationProperty.PASSWORD_SECURITY_REQUEST_PASSWORD_RESET_LINK:
					requestPasswordResetLinkFld.setValue(configuration.getValue());
					break;
				case ConfigurationProperty.PASSWORD_SECURITY_REQUEST_PASSWORD_RESET_VALIDITY:
					requestPasswordResetValidityFld.setValue(configuration.getValue());
					break;
				case ConfigurationProperty.PASSWORD_SECURITY_MINIMUM_LENGTH:
					minimumPasswordLengthFld.setValue(configuration.getValue());
					break;
				case ConfigurationProperty.PASSWORD_SECURITY_MAXIMUM_LENGTH:
					maximumPasswordLengthFld.setValue(configuration.getValue());
					break;
				case ConfigurationProperty.PASSWORD_SECURITY_NO_OF_UPPERCASE:
					numberOfUpperCaseFld.setValue(configuration.getValue());
					break;
				case ConfigurationProperty.PASSWORD_SECURITY_NO_OF_SPECIAL:
					numberOfSpecialCharactersFld.setValue(configuration.getValue());
					break;
				case ConfigurationProperty.PASSWORD_SECURITY_NO_OF_DIGITS:
					numberOfDigits.setValue(configuration.getValue());
					break;
				case ConfigurationProperty.PASSWORD_SECURITY_NO_OF_INVALID_ATTEMPTS:
					numberOfInvalidAttemptsFld.setValue(configuration.getValue());
					break;
				case ConfigurationProperty.PASSWORD_SECURITY_DAYS_BEFORE_EXPIRATION:
					daysBeforePasswordExpiresFld.setValue(configuration.getValue());
					break;
				case ConfigurationProperty.PASSWORD_SECURITY_USED_HISTORY:
					usedPasswordHistoryFld.setValue(configuration.getValue());
					break;
				default:
					break;
				}
			}
		}
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
