package software.simple.solutions.framework.core.repository.impl;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Repository;

import software.simple.solutions.framework.core.entities.Configuration;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.IConfigurationRepository;

@Repository
public class ConfigurationRepository extends GenericRepository implements IConfigurationRepository {

	@Override
	public List<Configuration> getConfigurations(List<String> codes) throws FrameworkException {
		String query = "from Configuration where code in :codes";
		ConcurrentMap<String, Object> paramMap = createParamMap();
		paramMap.put("codes", codes);
		return createListQuery(query, paramMap);
	}

}
