package software.simple.solutions.framework.core.repository;

import java.util.List;

import software.simple.solutions.framework.core.entities.Privilege;
import software.simple.solutions.framework.core.exceptions.FrameworkException;

public interface IPrivilegeRepository extends IGenericRepository {

	List<Privilege> getPrivileges() throws FrameworkException;

}
