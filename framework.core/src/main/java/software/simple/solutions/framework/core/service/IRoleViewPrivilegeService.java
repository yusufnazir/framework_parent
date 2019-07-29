package software.simple.solutions.framework.core.service;

import java.util.List;

import software.simple.solutions.framework.core.entities.Privilege;
import software.simple.solutions.framework.core.exceptions.FrameworkException;

public interface IRoleViewPrivilegeService extends ISuperService {

	// List<String> getUserViewPrivilege(Long viewId, Long userId) throws
	// FrameworkException;

	// List<String> getUserViewPrivilege(String className, Long userId) throws
	// FrameworkException;

	List<Privilege> getRoleViewPrivilege(Long viewId, Long roleId) throws FrameworkException;

	void updateRoleViewPrivileges(Long roleViewId, List<Long> privilegeIds) throws FrameworkException;

	List<Privilege> getPrivilegesByRoleView(Long roleViewId) throws FrameworkException;

	List<String> getPrivilegesByViewIdAndRoleId(Long viewId, Long roleId) throws FrameworkException;

	List<String> getPrivilegesByViewIdAndUserId(Long viewId, Long applicationUserId) throws FrameworkException;

	// List<String> getRoleViewPrivilege(String className, Long roleId) throws
	// FrameworkException;

}
