package software.simple.solutions.framework.core.repository;

import java.util.List;

import software.simple.solutions.framework.core.entities.Privilege;
import software.simple.solutions.framework.core.exceptions.FrameworkException;

public interface IRoleViewPrivilegeRepository extends IGenericRepository {

	// RoleViewPrivilege> getByViewAndUser(Long viewId, Long userId) throws
	// FrameworkException;

	List<Privilege> getByViewIdAndRoleId(Long viewId, Long roleId) throws FrameworkException;

	void updateRoleViewPrivileges(Long roleViewId, List<Long> privilegeIds) throws FrameworkException;

	List<Privilege> getPrivilegesByRoleView(Long roleViewId) throws FrameworkException;

	List<String> getPrivilegesByViewIdAndRoleId(Long viewId, Long roleId) throws FrameworkException;

	List<String> getPrivilegesByViewIdAndUserId(Long viewId, Long applicationUserId) throws FrameworkException;

}
