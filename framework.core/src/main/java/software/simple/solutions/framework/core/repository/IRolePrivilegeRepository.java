package software.simple.solutions.framework.core.repository;

import java.util.List;

import software.simple.solutions.framework.core.entities.Privilege;
import software.simple.solutions.framework.core.entities.RolePrivilege;
import software.simple.solutions.framework.core.exceptions.FrameworkException;

public interface IRolePrivilegeRepository extends IGenericRepository {

	List<RolePrivilege> getByUser(Long userId) throws FrameworkException;

	List<Privilege> getPrivilegesByRole(Long roleId) throws FrameworkException;

	void updateRolePrivileges(Long roleId, List<Long> privilegeIds) throws FrameworkException;

}
