package software.simple.solutions.framework.core.service.impl;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.constants.ApplicationUserConfigurationCodes;
import software.simple.solutions.framework.core.entities.ApplicationUser;
import software.simple.solutions.framework.core.entities.ApplicationUserConfiguration;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.IApplicationUserConfigurationRepository;
import software.simple.solutions.framework.core.repository.IConfigurationRepository;
import software.simple.solutions.framework.core.service.IApplicationUserConfigurationService;
import software.simple.solutions.framework.core.valueobjects.ApplicationUserConfigurationVO;
import software.simple.solutions.framework.core.valueobjects.SuperVO;

@Transactional
@Service
@ServiceRepository(claz = IConfigurationRepository.class)
public class ApplicationUserConfigurationService extends SuperService implements IApplicationUserConfigurationService {

	@Autowired
	private IApplicationUserConfigurationRepository applicationUserConfigurationRepository;

	@Override
	public <T, R extends SuperVO> T updateSingle(R valueObject) throws FrameworkException {

		ApplicationUserConfigurationVO vo = (ApplicationUserConfigurationVO) valueObject;

		ApplicationUserConfiguration configuration = getByCode(vo.getApplicationUserId(), vo.getCode());
		if (configuration == null) {
			configuration = new ApplicationUserConfiguration();
			configuration.setCode(vo.getCode());
		}
		configuration.setApplicationUser(get(ApplicationUser.class, vo.getApplicationUserId()));
		configuration.setValue(vo.getValue());
		configuration.setBigValue(vo.getBigValue());

		return (T) saveOrUpdate(configuration, vo.isNew());
	}

	@Override
	public List<ApplicationUserConfiguration> getSMTPValidateConfiguration(Long applicationUserId)
			throws FrameworkException {
		return applicationUserConfigurationRepository.getSMTPValidateConfiguration(applicationUserId,
				Arrays.asList(ApplicationUserConfigurationCodes.SMTP_VALIDATE_SUBJECT,
						ApplicationUserConfigurationCodes.SMTP_VALIDATE_MESSAGE,
						ApplicationUserConfigurationCodes.SMTP_VALIDATE_TO_EMAIL));
	}

	@Override
	public ApplicationUserConfiguration getByCode(Long applicationUserId, String code) throws FrameworkException {
		return applicationUserConfigurationRepository.getByCode(ApplicationUserConfiguration.class, applicationUserId,
				code);
	}

}
