package software.simple.solutions.framework.core.service;

import java.util.List;

import software.simple.solutions.framework.core.entities.Privilege;
import software.simple.solutions.framework.core.exceptions.FrameworkException;

public interface IPrivilegeService extends ISuperService {

	List<Privilege> getPrivileges() throws FrameworkException;

}
