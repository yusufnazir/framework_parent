package software.simple.solutions.framework.core.service;

import java.util.List;

import software.simple.solutions.framework.core.entities.Menu;
import software.simple.solutions.framework.core.exceptions.FrameworkException;

public interface IMenuService extends ISuperService {

	List<Menu> listParentMenus() throws FrameworkException;

	List<Menu> listChildMenus(Long parentId) throws FrameworkException;

	List<Menu> findAuthorizedMenus(Long roleId) throws FrameworkException;

	List<Menu> findTabMenus(Long parentMenuId, Long roleId) throws FrameworkException;

	Menu getLookUpByViewClass(Long roleId, String name) throws FrameworkException;

	List<Menu> findAuthorizedMenusByType(Long roleId, List<Long> types) throws FrameworkException;

	List<Menu> getPossibleHomeViews() throws FrameworkException;

}
