package software.simple.solutions.framework.core.service;

import java.util.List;

import software.simple.solutions.framework.core.entities.Configuration;
import software.simple.solutions.framework.core.exceptions.FrameworkException;

public interface IConfigurationService extends ISuperService {

	public List<Configuration> getMailServerConfiguration() throws FrameworkException;

	public Configuration getByCode(String code) throws FrameworkException;

	public boolean isSmtpEnabled() throws FrameworkException;

	List<Configuration> getPasswordSecurityConfiguration() throws FrameworkException;

	List<Configuration> getApplicationConfiguration() throws FrameworkException;

	public String getSystemEmail() throws FrameworkException;

	List<Configuration> getMailPlaceholderConfiguration() throws FrameworkException;

	public Configuration updateCodeValue(String configurationCode, String value) throws FrameworkException;

	List<Configuration> getLdapConfiguration() throws FrameworkException;

}
