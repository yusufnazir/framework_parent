package software.simple.solutions.framework.core.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.entities.Configuration;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.ConfigurationProperty;
import software.simple.solutions.framework.core.repository.IConfigurationRepository;
import software.simple.solutions.framework.core.service.IConfigurationService;
import software.simple.solutions.framework.core.service.IFileService;
import software.simple.solutions.framework.core.valueobjects.ConfigurationVO;
import software.simple.solutions.framework.core.valueobjects.EntityFileVO;
import software.simple.solutions.framework.core.valueobjects.SuperVO;

@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Service
@ServiceRepository(claz = IConfigurationRepository.class)
public class ConfigurationService extends SuperService implements IConfigurationService {

	private static final long serialVersionUID = 3486090073648582056L;

	@Autowired
	private IConfigurationRepository configurationRepository;

	@Autowired
	private IFileService fileService;

	@Override
	public <T, R extends SuperVO> T updateSingle(R valueObject) throws FrameworkException {

		ConfigurationVO vo = (ConfigurationVO) valueObject;

		Configuration configuration = getByCode(vo.getCode());
		boolean isNew = configuration == null;
		if (configuration == null) {
			configuration = new Configuration();
			configuration.setCode(vo.getCode());
		}
		configuration.setValue(vo.getValue());
		configuration.setBigValue(vo.getBigValue());

		configuration = saveOrUpdate(configuration, isNew);
		if (vo.getByteValue() != null) {
			EntityFileVO entityFileVO = new EntityFileVO();
			entityFileVO.setEntityName(Configuration.class.getName());
			entityFileVO.setEntityId(configuration.getId().toString());
			entityFileVO.setTypeOfFile(ConfigurationProperty.APPLICATION_LOGO);
			entityFileVO.setFilename(vo.getValue());
			entityFileVO.setFileObject(vo.getByteValue());
			entityFileVO.setDatabase(true);
			fileService.upLoadFile(entityFileVO);
		}

		return (T) configuration;
	}

	@Override
	public List<Configuration> getMailServerConfiguration() throws FrameworkException {
		return configurationRepository.getConfigurations(Arrays.asList(ConfigurationProperty.SMTP_ENABLE,
				ConfigurationProperty.SMTP_HOST, ConfigurationProperty.SMTP_PASSWORD, ConfigurationProperty.SMTP_PORT,
				ConfigurationProperty.SMTP_SYSTEM_EMAIL, ConfigurationProperty.SMTP_USERNAME));
	}

	@Override
	public List<Configuration> getPasswordSecurityConfiguration() throws FrameworkException {
		return configurationRepository
				.getConfigurations(Arrays.asList(ConfigurationProperty.PASSWORD_SECURITY_CONFIGURATION,
						ConfigurationProperty.PASSWORD_SECURITY_DAYS_BEFORE_EXPIRATION,
						ConfigurationProperty.PASSWORD_SECURITY_MAXIMUM_LENGTH,
						ConfigurationProperty.PASSWORD_SECURITY_MINIMUM_LENGTH,
						ConfigurationProperty.PASSWORD_SECURITY_NO_OF_DIGITS,
						ConfigurationProperty.PASSWORD_SECURITY_NO_OF_INVALID_ATTEMPTS,
						ConfigurationProperty.PASSWORD_SECURITY_NO_OF_SPECIAL,
						ConfigurationProperty.PASSWORD_SECURITY_NO_OF_UPPERCASE,
						ConfigurationProperty.PASSWORD_SECURITY_USED_HISTORY,
						ConfigurationProperty.PASSWORD_SECURITY_REQUEST_PASSWORD_RESET_VALIDITY,
						ConfigurationProperty.PASSWORD_SECURITY_REQUEST_PASSWORD_RESET_LINK));
	}

	@Override
	public List<Configuration> getLdapConfiguration() throws FrameworkException {
		return configurationRepository
				.getConfigurations(Arrays.asList(ConfigurationProperty.LDAP_CONFIGURATION_USE_LDAP,
						ConfigurationProperty.LDAP_CONFIGURATION_HOST, ConfigurationProperty.LDAP_CONFIGURATION_PORT));
	}

	@Override
	public List<Configuration> getApplicationConfiguration() throws FrameworkException {
		return configurationRepository.getConfigurations(Arrays.asList(ConfigurationProperty.APPLICATION_NAME,
				ConfigurationProperty.APPLICATION_URL, ConfigurationProperty.APPLICATION_LOGO,
				ConfigurationProperty.APPLICATION_DATE_FORMAT, ConfigurationProperty.APPLICATION_CONSOLIDATE_ROLE,
				ConfigurationProperty.APPLICATION_LOGO_HEIGHT, ConfigurationProperty.APPLICATION_LOGO_WIDTH,
				ConfigurationProperty.APPLICATION_ENABLE_REGISTRATION,
				ConfigurationProperty.APPLICATION_DEFAULT_USER_ROLE, ConfigurationProperty.APPLICATION_HOME_VIEW,
				ConfigurationProperty.APPLICATION_LAYOUT));
	}

	@Override
	public List<Configuration> getMailPlaceholderConfiguration() throws FrameworkException {
		return configurationRepository.getConfigurations(
				Arrays.asList(ConfigurationProperty.APPLICATION_NAME, ConfigurationProperty.APPLICATION_URL,
						ConfigurationProperty.PASSWORD_SECURITY_REQUEST_PASSWORD_RESET_LINK,
						ConfigurationProperty.PASSWORD_SECURITY_REQUEST_PASSWORD_RESET_VALIDITY));
	}

	@Override
	public Configuration getByCode(String code) throws FrameworkException {
		return getByCode(Configuration.class, code);
	}

	@Override
	public boolean isSmtpEnabled() throws FrameworkException {
		Configuration configuration = getByCode(ConfigurationProperty.SMTP_ENABLE);
		if (configuration == null) {
			return false;
		}
		return Boolean.parseBoolean(configuration.getValue());
	}

	@Override
	public String getSystemEmail() throws FrameworkException {
		Configuration configuration = getByCode(ConfigurationProperty.SMTP_SYSTEM_EMAIL);
		return (configuration == null ? null : configuration.getValue());
	}

	@Override
	public Configuration updateCodeValue(String configurationCode, String value) throws FrameworkException {
		Configuration configuration = getByCode(configurationCode);
		boolean isNew = configuration == null;
		if (configuration == null) {
			configuration = new Configuration();
		}
		configuration.setValue(value);
		configuration.setCode(configurationCode);
		return saveOrUpdate(configuration, isNew);
	}

}
