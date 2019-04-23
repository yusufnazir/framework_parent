package software.simple.solutions.framework.core.repository;

import software.simple.solutions.framework.core.entities.View;
import software.simple.solutions.framework.core.exceptions.FrameworkException;

public interface IViewRepository extends IGenericRepository {

	View getByClassName(String className) throws FrameworkException;

}
