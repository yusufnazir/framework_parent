package software.simple.solutions.framework.core.web.view.configuration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

import software.simple.solutions.framework.core.annotations.CxodeConfigurationComponent;
import software.simple.solutions.framework.core.components.CButton;
import software.simple.solutions.framework.core.components.CCheckBox;
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
import software.simple.solutions.framework.core.service.IConfigurationService;
import software.simple.solutions.framework.core.util.ContextProvider;
import software.simple.solutions.framework.core.valueobjects.ConfigurationVO;

@CxodeConfigurationComponent(order = 4, captionKey = ConfigurationProperty.LDAP_CONFIGURATION)
public class LdapConfiguration extends CGridLayout {

	private static final long serialVersionUID = -4290908153354032925L;

	private static final Logger logger = LogManager.getLogger(LdapConfiguration.class);

	private CCheckBox useLdapFld;
	private CTextField hostFld;
//	private CDiscreetNumberField portFld;

	private CButton persistBtn;
	private SessionHolder sessionHolder;
	private UI ui;

	public LdapConfiguration() throws FrameworkException {
		ui = UI.getCurrent();
		sessionHolder = (SessionHolder) UI.getCurrent().getData();
		setSpacing(true);
		int i = 0;
		useLdapFld = addField(CCheckBox.class, ConfigurationProperty.LDAP_CONFIGURATION_USE_LDAP, 0, ++i);
		hostFld = addField(CTextField.class, ConfigurationProperty.LDAP_CONFIGURATION_HOST, 0, ++i);
//		portFld = addField(CDiscreetNumberField.class, ConfigurationProperty.LDAP_CONFIGURATION_PORT, 0, ++i);

		persistBtn = addField(CButton.class, 0, ++i);
		persistBtn.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		persistBtn.setCaptionByKey(SystemProperty.SYSTEM_BUTTON_SUBMIT);

		persistBtn.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 3418895031034445319L;

			@Override
			public void buttonClick(ClickEvent event) {
				List<ConfigurationVO> configurations = new ArrayList<ConfigurationVO>();
				configurations.add(getValue(ConfigurationProperty.LDAP_CONFIGURATION_USE_LDAP, useLdapFld.getValue()));
				configurations.add(getValue(ConfigurationProperty.LDAP_CONFIGURATION_HOST, hostFld.getValue()));
//				configurations.add(getValue(ConfigurationProperty.LDAP_CONFIGURATION_PORT, portFld.getLongValue()));
				IConfigurationService configurationService = ContextProvider.getBean(IConfigurationService.class);
				try {
					configurationService.update(configurations);
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
		useLdapFld.addValueChangeListener(new ValueChangeListener<Boolean>() {

			private static final long serialVersionUID = 6436331570102432249L;

			@Override
			public void valueChange(ValueChangeEvent<Boolean> event) {
				enableFields(event.getValue());
			}
		});
		enableFields(false);
		IConfigurationService configurationService = ContextProvider.getBean(IConfigurationService.class);
		List<Configuration> configurations = configurationService.getLdapConfiguration();
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
//				case ConfigurationProperty.LDAP_CONFIGURATION_PORT:
//					portFld.setValue(configuration.getValue());
//					break;
				default:
					break;
				}
			}
		}
		
		hostFld.setEnabled(false);
//		portFld.setEnabled(false);
		if(useLdapFld.getValue()){
			hostFld.setEnabled(true);
//			portFld.setEnabled(true);
		}
	}
	
	private void enableFields(boolean enabled) {
		hostFld.setEnabled(enabled);
//		portFld.setEnabled(enabled);
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
