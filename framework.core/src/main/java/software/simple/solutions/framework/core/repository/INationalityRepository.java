package software.simple.solutions.framework.core.repository;

import software.simple.solutions.framework.core.exceptions.FrameworkException;

public interface INationalityRepository extends IGenericRepository {

	Boolean isNameUnique(String name, Long id) throws FrameworkException;

}
