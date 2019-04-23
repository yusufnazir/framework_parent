package software.simple.solutions.framework.core.service;

import java.util.List;

import software.simple.solutions.framework.core.entities.UserRole;
import software.simple.solutions.framework.core.exceptions.FrameworkException;

public interface IRoleService extends ISuperService {

	List<UserRole> findRolesByUserId(Long id) throws FrameworkException;

}
