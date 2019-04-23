package software.simple.solutions.framework.core.service;

import java.util.List;

import software.simple.solutions.framework.core.entities.Role;
import software.simple.solutions.framework.core.exceptions.FrameworkException;

public interface IUserRoleService extends ISuperService {

	List<Role> findRolesByUser(Long userId) throws FrameworkException;

}
