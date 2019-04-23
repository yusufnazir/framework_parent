package software.simple.solutions.framework.core.repository;

import software.simple.solutions.framework.core.entities.RoleViewPrivilege;
import software.simple.solutions.framework.core.exceptions.FrameworkException;

public interface IRoleViewRepository extends IGenericRepository {

	RoleViewPrivilege getByViewIdAndRoleId(Long viewId, Long roleId) throws FrameworkException;

}
