package software.simple.solutions.framework.core.service.facade;

import java.util.List;

import com.vaadin.flow.component.UI;

import software.simple.solutions.framework.core.entities.Role;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.IUserRoleService;

public class UserRoleServiceFacade extends SuperServiceFacade<IUserRoleService> implements IUserRoleService {

	public static final long serialVersionUID = 7649271814886258798L;

	public UserRoleServiceFacade(UI ui, Class<IUserRoleService> s) {
		super(ui, s);
	}

	public static UserRoleServiceFacade get(UI ui) {
		return new UserRoleServiceFacade(ui, IUserRoleService.class);
	}

	@Override
	public List<Role> findRolesByUser(Long userId) throws FrameworkException {
		return service.findRolesByUser(userId);
	}
}
