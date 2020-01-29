package software.simple.solutions.framework.core.service.facade;

import java.util.List;

import com.vaadin.flow.component.UI;

import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.ISubMenuService;

public class SubMenuServiceFacade extends SuperServiceFacade<ISubMenuService> implements ISubMenuService {

	public static final long serialVersionUID = -1821036617989646815L;

	public SubMenuServiceFacade(UI ui, Class<ISubMenuService> s) {
		super(ui, s);
	}

	public static SubMenuServiceFacade get(UI ui) {
		return new SubMenuServiceFacade(ui, ISubMenuService.class);
	}

	@Override
	public <T> List<T> findTabMenus(Long parentMenuId) throws FrameworkException {
		return service.findTabMenus(parentMenuId);
	}
}
