package software.simple.solutions.framework.core.service;

import java.util.List;

import software.simple.solutions.framework.core.entities.Privilege;
import software.simple.solutions.framework.core.exceptions.FrameworkException;

public interface IRolePrivilegeService extends ISuperService {

	void updateRolePrivileges(Long roleId, List<Long> privilegeIds) throws FrameworkException;

	List<Privilege> getPrivilegesByRole(Long roleId) throws FrameworkException;

	// Privilege getUserPrivilege(Long userId) throws FrameworkException;

	// Privilege getRolePrivilege(Long roleId) throws FrameworkException;

}
