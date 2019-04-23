package software.simple.solutions.framework.core.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import software.simple.solutions.framework.core.entities.Privilege;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.IPrivilegeRepository;

@Repository
public class PrivilegeRepository extends GenericRepository implements IPrivilegeRepository {

	@Override
	public List<Privilege> getPrivileges() throws FrameworkException {
		String query = "from Privilege";
		return createListQuery(query);
	}

}
