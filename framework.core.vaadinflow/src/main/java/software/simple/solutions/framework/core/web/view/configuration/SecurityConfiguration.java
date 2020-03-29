package software.simple.solutions.framework.core.web.view.configuration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.server.VaadinSession;

import software.simple.solutions.framework.core.annotations.CxodeConfigurationComponent;
import software.simple.solutions.framework.core.constants.Constants;
import software.simple.solutions.framework.core.entities.Configuration;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.ConfigurationProperty;
import software.simple.solutions.framework.core.properties.SystemProperty;
import software.simple.solutions.framework.core.service.facade.ConfigurationServiceFacade;
import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.util.SessionHolder;
import software.simple.solutions.framework.core.valueobjects.ConfigurationVO;
import software.simple.solutions.framework.core.web.DetailsWindow;
import software.simple.solutions.framework.core.web.components.CButton;
import software.simple.solutions.framework.core.web.components.CConfigurationLayout;
import software.simple.solutions.framework.core.web.components.CDiscreetNumberField;
import software.simple.solutions.framework.core.web.components.CTextField;
import software.simple.solutions.framework.core.web.components.NotificationBuilder;

@CxodeConfigurationComponent(order = 3, captionKey = ConfigurationProperty.PASSWORD_SECURITY_CONFIGURATION)
public class SecurityConfiguration extends CConfigurationLayout {

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
		sessionHolder = (SessionHolder) VaadinSession.getCurrent().getAttribute(Constants.SESSION_HOLDER);
		requestPasswordResetLinkFld = add(CTextField.class,
				ConfigurationProperty.PASSWORD_SECURITY_REQUEST_PASSWORD_RESET_LINK);
		requestPasswordResetValidityFld = add(CDiscreetNumberField.class,
				ConfigurationProperty.PASSWORD_SECURITY_REQUEST_PASSWORD_RESET_VALIDITY);
		minimumPasswordLengthFld = add(CDiscreetNumberField.class,
				ConfigurationProperty.PASSWORD_SECURITY_MINIMUM_LENGTH);
		maximumPasswordLengthFld = add(CDiscreetNumberField.class,
				ConfigurationProperty.PASSWORD_SECURITY_MAXIMUM_LENGTH);
		numberOfUpperCaseFld = add(CDiscreetNumberField.class, ConfigurationProperty.PASSWORD_SECURITY_NO_OF_UPPERCASE);
		numberOfSpecialCharactersFld = add(CDiscreetNumberField.class,
				ConfigurationProperty.PASSWORD_SECURITY_NO_OF_SPECIAL);
		numberOfDigits = add(CDiscreetNumberField.class, ConfigurationProperty.PASSWORD_SECURITY_NO_OF_DIGITS);
		numberOfInvalidAttemptsFld = add(CDiscreetNumberField.class,
				ConfigurationProperty.PASSWORD_SECURITY_NO_OF_INVALID_ATTEMPTS);
		daysBeforePasswordExpiresFld = add(CDiscreetNumberField.class,
				ConfigurationProperty.PASSWORD_SECURITY_DAYS_BEFORE_EXPIRATION);
		usedPasswordHistoryFld = add(CDiscreetNumberField.class, ConfigurationProperty.PASSWORD_SECURITY_USED_HISTORY);

		persistBtn = add(CButton.class, SystemProperty.SYSTEM_BUTTON_SUBMIT);
		persistBtn.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_PRIMARY);
		persistBtn.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {

			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
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
					NotificationBuilder.buildSuccess(PropertyResolver
							.getPropertyValueByLocale(SystemProperty.UPDATE_SUCCESSFULL, UI.getCurrent().getLocale()));
				} catch (FrameworkException e) {
					logger.error(e.getMessage(), e);
					DetailsWindow.build(e);
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
					requestPasswordResetValidityFld.setLongValue(configuration.getLong());
					break;
				case ConfigurationProperty.PASSWORD_SECURITY_MINIMUM_LENGTH:
					minimumPasswordLengthFld.setLongValue(configuration.getLong());
					break;
				case ConfigurationProperty.PASSWORD_SECURITY_MAXIMUM_LENGTH:
					maximumPasswordLengthFld.setLongValue(configuration.getLong());
					break;
				case ConfigurationProperty.PASSWORD_SECURITY_NO_OF_UPPERCASE:
					numberOfUpperCaseFld.setLongValue(configuration.getLong());
					break;
				case ConfigurationProperty.PASSWORD_SECURITY_NO_OF_SPECIAL:
					numberOfSpecialCharactersFld.setLongValue(configuration.getLong());
					break;
				case ConfigurationProperty.PASSWORD_SECURITY_NO_OF_DIGITS:
					numberOfDigits.setLongValue(configuration.getLong());
					break;
				case ConfigurationProperty.PASSWORD_SECURITY_NO_OF_INVALID_ATTEMPTS:
					numberOfInvalidAttemptsFld.setLongValue(configuration.getLong());
					break;
				case ConfigurationProperty.PASSWORD_SECURITY_DAYS_BEFORE_EXPIRATION:
					daysBeforePasswordExpiresFld.setLongValue(configuration.getLong());
					break;
				case ConfigurationProperty.PASSWORD_SECURITY_USED_HISTORY:
					usedPasswordHistoryFld.setLongValue(configuration.getLong());
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
