package software.simple.solutions.framework.core.repository;

import software.simple.solutions.framework.core.exceptions.FrameworkException;

public interface IRoleCategoryRepository extends IGenericRepository {

	Boolean isNameUnique(Class<?> cl, String name, Long id) throws FrameworkException;

	Boolean isNameUnique(Class<?> cl, String name) throws FrameworkException;

}
