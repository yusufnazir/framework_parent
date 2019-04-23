package software.simple.solutions.framework.core.repository.impl;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Repository;

import software.simple.solutions.framework.core.entities.UserRole;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.IRoleRepository;

@Repository
public class RoleRepository extends GenericRepository implements IRoleRepository {

	@Override
	public List<UserRole> findRolesByUserId(Long userId) throws FrameworkException {
		ConcurrentMap<String, Object> paramMap = createParamMap();
		String query = "from UserRole ur where ur.applicationUser.id =:userId ";
		paramMap.put("userId", userId);
		return createListQuery(query, paramMap);
	}

}
