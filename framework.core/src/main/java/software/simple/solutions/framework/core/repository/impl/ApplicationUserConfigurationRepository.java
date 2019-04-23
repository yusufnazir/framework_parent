package software.simple.solutions.framework.core.repository.impl;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Repository;

import software.simple.solutions.framework.core.entities.ApplicationUserConfiguration;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.IApplicationUserConfigurationRepository;

@Repository
public class ApplicationUserConfigurationRepository extends GenericRepository
		implements IApplicationUserConfigurationRepository {

	@Override
	public List<ApplicationUserConfiguration> getSMTPValidateConfiguration(Long applicationUserId, List<String> codes)
			throws FrameworkException {
		String query = "from ApplicationUserConfiguration where code in :codes and applicationUser.id=:applicationUserId";
		ConcurrentMap<String, Object> paramMap = createParamMap();
		paramMap.put("codes", codes);
		paramMap.put("applicationUserId", applicationUserId);
		return createListQuery(query, paramMap);
	}

	@Override
	public ApplicationUserConfiguration getByCode(Class<ApplicationUserConfiguration> entityClass,
			Long applicationUserId, String code) throws FrameworkException {
		String query = "from ApplicationUserConfiguration where applicationUser.id=:applicationUserId and code =:code";
		ConcurrentMap<String, Object> paramMap = createParamMap();
		paramMap.put("code", code);
		paramMap.put("applicationUserId", applicationUserId);
		return getByQuery(query, paramMap);
	}

}
