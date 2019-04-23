package software.simple.solutions.framework.core.service;

import java.util.List;

import software.simple.solutions.framework.core.entities.Menu;
import software.simple.solutions.framework.core.exceptions.FrameworkException;

public interface IMenuService extends ISuperService {

	List<Menu> listParentMenus() throws FrameworkException;

	List<Menu> listChildMenus(Long parentId) throws FrameworkException;

	List<Menu> findAuthorizedMenus(Long roleId) throws FrameworkException;

	List<Menu> findTabMenus(Long id) throws FrameworkException;

	Menu getLookUpByViewClass(Long roleId, String name) throws FrameworkException;

}
