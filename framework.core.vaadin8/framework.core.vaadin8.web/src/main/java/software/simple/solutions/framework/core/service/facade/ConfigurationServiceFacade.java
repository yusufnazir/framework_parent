package software.simple.solutions.framework.core.service.facade;

import java.util.List;

import com.vaadin.ui.UI;

import software.simple.solutions.framework.core.entities.Configuration;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.IConfigurationService;
import software.simple.solutions.framework.core.service.ISuperService;

public class ConfigurationServiceFacade extends SuperServiceFacade<IConfigurationService>
		implements IConfigurationService {

	public static final long serialVersionUID = 6973047980313036517L;

	public ConfigurationServiceFacade(UI ui, Class<IConfigurationService> s) {
		super(ui, s);
	}

	public static ConfigurationServiceFacade get(UI ui) {
		return new ConfigurationServiceFacade(ui, IConfigurationService.class);
	}

	@Override
	public List<Configuration> getMailServerConfiguration() throws FrameworkException {
		return service.getMailServerConfiguration();
	}

	@Override
	public Configuration getByCode(String code) throws FrameworkException {
		return service.getByCode(code);
	}

	@Override
	public boolean isSmtpEnabled() throws FrameworkException {
		return service.isSmtpEnabled();
	}

	@Override
	public List<Configuration> getPasswordSecurityConfiguration() throws FrameworkException {
		return service.getPasswordSecurityConfiguration();
	}

	@Override
	public List<Configuration> getApplicationConfiguration() throws FrameworkException {
		return service.getApplicationConfiguration();
	}

	@Override
	public String getSystemEmail() throws FrameworkException {
		return service.getSystemEmail();
	}

	@Override
	public List<Configuration> getMailPlaceholderConfiguration() throws FrameworkException {
		return service.getMailPlaceholderConfiguration();
	}

	@Override
	public Configuration updateCodeValue(String configurationCode, String value) throws FrameworkException {
		return service.updateCodeValue(configurationCode, value);
	}

	@Override
	public List<Configuration> getLdapConfiguration() throws FrameworkException {
		return service.getLdapConfiguration();
	}
}
