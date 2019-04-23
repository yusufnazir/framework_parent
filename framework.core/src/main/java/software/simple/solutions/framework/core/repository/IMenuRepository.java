package software.simple.solutions.framework.core.repository;

import java.util.List;

import software.simple.solutions.framework.core.entities.Menu;
import software.simple.solutions.framework.core.exceptions.FrameworkException;

public interface IMenuRepository extends IGenericRepository {

	List<Menu> listParentMenus() throws FrameworkException;

	List<Menu> listChildMenus(Long id) throws FrameworkException;

	List<Menu> findAuthorizedMenus(Long roleId) throws FrameworkException;

	List<Menu> findTabMenus(Long parentMenuId) throws FrameworkException;

	Menu getLookUpByViewClass(Long roleId, String name) throws FrameworkException;

}
