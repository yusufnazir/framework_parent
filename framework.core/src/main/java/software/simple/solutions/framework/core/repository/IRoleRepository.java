package software.simple.solutions.framework.core.repository;

import java.util.List;

import software.simple.solutions.framework.core.entities.UserRole;
import software.simple.solutions.framework.core.exceptions.FrameworkException;

public interface IRoleRepository extends IGenericRepository {

	List<UserRole> findRolesByUserId(Long userId) throws FrameworkException;

}
