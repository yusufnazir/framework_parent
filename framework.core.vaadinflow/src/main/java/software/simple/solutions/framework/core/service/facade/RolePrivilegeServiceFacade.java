package software.simple.solutions.framework.core.service.facade;

import java.util.List;

import com.vaadin.flow.component.UI;

import software.simple.solutions.framework.core.entities.Privilege;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.IRolePrivilegeService;

public class RolePrivilegeServiceFacade extends SuperServiceFacade<IRolePrivilegeService>
		implements IRolePrivilegeService {

	public static final long serialVersionUID = -7724114835857081127L;

	public RolePrivilegeServiceFacade(UI ui, Class<IRolePrivilegeService> s) {
		super(ui, s);
	}

	public static RolePrivilegeServiceFacade get(UI ui) {
		return new RolePrivilegeServiceFacade(ui, IRolePrivilegeService.class);
	}

	@Override
	public void updateRolePrivileges(Long roleId, List<Long> privilegeIds) throws FrameworkException {
		service.updateRolePrivileges(roleId, privilegeIds);
	}

	@Override
	public List<Privilege> getPrivilegesByRole(Long roleId) throws FrameworkException {
		return service.getPrivilegesByRole(roleId);
	}
}
