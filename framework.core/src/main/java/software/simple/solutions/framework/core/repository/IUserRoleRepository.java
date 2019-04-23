package software.simple.solutions.framework.core.repository;

import java.util.List;

import software.simple.solutions.framework.core.entities.Role;
import software.simple.solutions.framework.core.entities.UserRole;
import software.simple.solutions.framework.core.exceptions.FrameworkException;

public interface IUserRoleRepository extends IGenericRepository {

	List<Role> findRolesByUser(Long id) throws FrameworkException;

	UserRole getByUserAndRole(Long userId, Long roleId) throws FrameworkException;

}
