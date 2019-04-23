package software.simple.solutions.framework.core.repository;

import java.util.List;

import software.simple.solutions.framework.core.entities.Configuration;
import software.simple.solutions.framework.core.exceptions.FrameworkException;

public interface IConfigurationRepository extends IGenericRepository {

	List<Configuration> getConfigurations(List<String> codes) throws FrameworkException;

}
