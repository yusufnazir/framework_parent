package software.simple.solutions.framework.core.service;

import java.util.List;

import software.simple.solutions.framework.core.entities.ApplicationUserConfiguration;
import software.simple.solutions.framework.core.exceptions.FrameworkException;

public interface IApplicationUserConfigurationService extends ISuperService {

	public List<ApplicationUserConfiguration> getSMTPValidateConfiguration(Long applicationUserId)
			throws FrameworkException;

	public ApplicationUserConfiguration getByCode(Long applicationUserId, String code) throws FrameworkException;

}
