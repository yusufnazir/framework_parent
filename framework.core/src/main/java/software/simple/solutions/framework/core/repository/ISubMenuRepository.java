package software.simple.solutions.framework.core.repository;

import java.util.List;

import software.simple.solutions.framework.core.exceptions.FrameworkException;

public interface ISubMenuRepository extends IGenericRepository {

	<T> List<T> findTabMenus(Long parentMenuId) throws FrameworkException;

}
