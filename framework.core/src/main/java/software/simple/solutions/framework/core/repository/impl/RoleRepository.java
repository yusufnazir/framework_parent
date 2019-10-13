package software.simple.solutions.framework.core.repository.impl;

import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import software.simple.solutions.framework.core.entities.Role;
import software.simple.solutions.framework.core.entities.UserRole;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.repository.IRoleRepository;

@Repository
public class RoleRepository extends GenericRepository implements IRoleRepository {

	private static final long serialVersionUID = 4549390073703953269L;

	@Override
	public List<UserRole> findRolesByUserId(Long userId) throws FrameworkException {
		ConcurrentMap<String, Object> paramMap = createParamMap();
		String query = "from UserRole ur where ur.applicationUser.id =:userId ";
		paramMap.put("userId", userId);
		return createListQuery(query, paramMap);
	}

	@Override
	public <T, R> List<R> getForListing(Class<T> cl, Boolean active) throws FrameworkException {
		ConcurrentMap<String, Object> paramMap = createParamMap();

		String query = "from Role where 1=1 order by id ";

		List<Role> list = createListQuery(query, paramMap);
		return (List<R>) list.stream().map(p -> new ComboItem(p.getId(), p.getCaption())).collect(Collectors.toList());
	}

}
