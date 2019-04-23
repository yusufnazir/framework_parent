package software.simple.solutions.framework.core.repository;

import java.util.List;

import software.simple.solutions.framework.core.entities.ApplicationUserConfiguration;
import software.simple.solutions.framework.core.exceptions.FrameworkException;

public interface IApplicationUserConfigurationRepository extends IGenericRepository {

	List<ApplicationUserConfiguration> getSMTPValidateConfiguration(Long applicationUserId, List<String> codes)
			throws FrameworkException;

	ApplicationUserConfiguration getByCode(Class<ApplicationUserConfiguration> entityClass, Long applicationUserId,
			String code) throws FrameworkException;

}
