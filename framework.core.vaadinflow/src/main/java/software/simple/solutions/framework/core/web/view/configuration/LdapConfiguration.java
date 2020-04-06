package software.simple.solutions.framework.core.web.view.configuration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasValue.ValueChangeEvent;
import com.vaadin.flow.component.HasValue.ValueChangeListener;
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
import software.simple.solutions.framework.core.web.components.CCheckBox;
import software.simple.solutions.framework.core.web.components.CTextField;
import software.simple.solutions.framework.core.web.components.CVerticalLayout;
import software.simple.solutions.framework.core.web.components.NotificationBuilder;

@CxodeConfigurationComponent(order = 4, captionKey = ConfigurationProperty.LDAP_CONFIGURATION)
public class LdapConfiguration extends CVerticalLayout {

	private static final long serialVersionUID = -4290908153354032925L;

	private static final Logger logger = LogManager.getLogger(LdapConfiguration.class);

	private CCheckBox useLdapFld;
	private CTextField hostFld;
	// private CDiscreetNumberField portFld;

	private CButton persistBtn;
	private SessionHolder sessionHolder;
	private UI ui;

	public LdapConfiguration() throws FrameworkException {
		ui = UI.getCurrent();
		sessionHolder = (SessionHolder) VaadinSession.getCurrent().getAttribute(Constants.SESSION_HOLDER);
		useLdapFld = add(CCheckBox.class, ConfigurationProperty.LDAP_CONFIGURATION_USE_LDAP);
		hostFld = add(CTextField.class, ConfigurationProperty.LDAP_CONFIGURATION_HOST);
		// portFld = add(CDiscreetNumberField.class,
		// ConfigurationProperty.LDAP_CONFIGURATION_PORT, 0, ++i);

		persistBtn = add(CButton.class, SystemProperty.SYSTEM_BUTTON_SUBMIT);
		persistBtn.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_PRIMARY);
		persistBtn.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {

			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				List<ConfigurationVO> configurations = new ArrayList<ConfigurationVO>();
				configurations.add(getValue(ConfigurationProperty.LDAP_CONFIGURATION_USE_LDAP, useLdapFld.getValue()));
				configurations.add(getValue(ConfigurationProperty.LDAP_CONFIGURATION_HOST, hostFld.getValue()));
				// configurations.add(getValue(ConfigurationProperty.LDAP_CONFIGURATION_PORT,
				// portFld.getLongValue()));
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
		useLdapFld.addValueChangeListener(new ValueChangeListener<ValueChangeEvent<Boolean>>() {

			private static final long serialVersionUID = 1130870737013338569L;

			@Override
			public void valueChanged(ValueChangeEvent<Boolean> event) {
				enableFields(event.getValue());
			}
		});
		enableFields(false);
		List<Configuration> configurations = ConfigurationServiceFacade.get(UI.getCurrent()).getLdapConfiguration();
		if (configurations != null && !configurations.isEmpty()) {
			for (Configuration configuration : configurations) {
				switch (configuration.getCode()) {
				case ConfigurationProperty.LDAP_CONFIGURATION_USE_LDAP:
					useLdapFld.setValue(Boolean.parseBoolean(configuration.getValue()));
					enableFields(Boolean.parseBoolean(configuration.getValue()));
					break;
				case ConfigurationProperty.LDAP_CONFIGURATION_HOST:
					hostFld.setValue(configuration.getValue());
					break;
				// case ConfigurationProperty.LDAP_CONFIGURATION_PORT:
				// portFld.setValue(configuration.getValue());
				// break;
				default:
					break;
				}
			}
		}

		hostFld.setEnabled(false);
		// portFld.setEnabled(false);
		if (useLdapFld.getValue()) {
			hostFld.setEnabled(true);
			// portFld.setEnabled(true);
		}
	}

	private void enableFields(boolean enabled) {
		hostFld.setEnabled(enabled);
		// portFld.setEnabled(enabled);
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
