package software.simple.solutions.framework.core.service.facade;

import java.util.List;

import com.vaadin.ui.UI;

import software.simple.solutions.framework.core.entities.UserRole;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.IRoleService;
import software.simple.solutions.framework.core.service.ISuperService;

public class RoleServiceFacade extends SuperServiceFacade<IRoleService> implements IRoleService {

	public static final long serialVersionUID = -4397029434092379871L;

	public RoleServiceFacade(UI ui, Class<IRoleService> s) {
		super(ui, s);
	}

	public static RoleServiceFacade get(UI ui) {
		return new RoleServiceFacade(ui, IRoleService.class);
	}

	@Override
	public List<UserRole> findRolesByUserId(Long id) throws FrameworkException {
		return service.findRolesByUserId(id);
	}
}
