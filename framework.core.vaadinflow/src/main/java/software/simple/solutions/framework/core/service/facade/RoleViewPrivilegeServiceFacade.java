package software.simple.solutions.framework.core.service.facade;

import java.util.List;

import com.vaadin.flow.component.UI;

import software.simple.solutions.framework.core.entities.Privilege;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.IRoleViewPrivilegeService;

public class RoleViewPrivilegeServiceFacade extends SuperServiceFacade<IRoleViewPrivilegeService>
		implements IRoleViewPrivilegeService {

	public static final long serialVersionUID = 1797712657066786757L;

	public RoleViewPrivilegeServiceFacade(UI ui, Class<IRoleViewPrivilegeService> s) {
		super(ui, s);
	}

	public static RoleViewPrivilegeServiceFacade get(UI ui) {
		return new RoleViewPrivilegeServiceFacade(ui, IRoleViewPrivilegeService.class);
	}

	@Override
	public List<Privilege> getRoleViewPrivilege(Long viewId, Long roleId) throws FrameworkException {
		return service.getRoleViewPrivilege(viewId, roleId);
	}

	@Override
	public void updateRoleViewPrivileges(Long roleViewId, List<Long> privilegeIds) throws FrameworkException {
		service.updateRoleViewPrivileges(roleViewId, privilegeIds);
	}

	@Override
	public List<Privilege> getPrivilegesByRoleView(Long roleViewId) throws FrameworkException {
		return service.getPrivilegesByRoleView(roleViewId);
	}

	@Override
	public List<String> getPrivilegesByViewIdAndRoleId(Long viewId, Long roleId) throws FrameworkException {
		return service.getPrivilegesByViewIdAndRoleId(viewId, roleId);
	}

	@Override
	public List<String> getPrivilegesByViewIdAndUserId(Long viewId, Long applicationUserId) throws FrameworkException {
		return service.getPrivilegesByViewIdAndUserId(viewId, applicationUserId);
	}

}
