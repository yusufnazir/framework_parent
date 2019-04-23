package software.simple.solutions.framework.core.service;

import java.util.List;

import software.simple.solutions.framework.core.exceptions.FrameworkException;

public interface ISubMenuService extends ISuperService {

	public <T> List<T> findTabMenus(Long parentMenuId) throws FrameworkException;

}
