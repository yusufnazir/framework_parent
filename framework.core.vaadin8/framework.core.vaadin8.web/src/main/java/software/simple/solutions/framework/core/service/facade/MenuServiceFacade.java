package software.simple.solutions.framework.core.service.facade;

import java.util.List;

import com.vaadin.ui.UI;

import software.simple.solutions.framework.core.entities.Menu;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.IMenuService;
import software.simple.solutions.framework.core.service.ISuperService;

public class MenuServiceFacade extends SuperServiceFacade<IMenuService> implements IMenuService {

	public static final long serialVersionUID = 172284490707892538L;

	public MenuServiceFacade(UI ui, Class<IMenuService> s) {
		super(ui, s);
	}

	public static MenuServiceFacade get(UI ui) {
		return new MenuServiceFacade(ui, IMenuService.class);
	}

	@Override
	public List<Menu> listParentMenus() throws FrameworkException {
		return service.listParentMenus();
	}

	@Override
	public List<Menu> listChildMenus(Long parentId) throws FrameworkException {
		return service.listChildMenus(parentId);
	}

	@Override
	public List<Menu> findAuthorizedMenus(Long roleId) throws FrameworkException {
		return service.findAuthorizedMenus(roleId);
	}

	@Override
	public List<Menu> findTabMenus(Long parentMenuId, Long roleId) throws FrameworkException {
		return service.findTabMenus(parentMenuId, roleId);
	}

	@Override
	public Menu getLookUpByViewClass(Long roleId, String name) throws FrameworkException {
		return service.getLookUpByViewClass(roleId, name);
	}

}
