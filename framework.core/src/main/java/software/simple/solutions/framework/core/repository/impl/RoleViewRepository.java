package software.simple.solutions.framework.core.repository.impl;

import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Repository;

import software.simple.solutions.framework.core.entities.RoleViewPrivilege;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.IRoleViewRepository;

@Repository
public class RoleViewRepository extends GenericRepository implements IRoleViewRepository {

	@Override
	public RoleViewPrivilege getByViewIdAndRoleId(Long viewId, Long roleId) throws FrameworkException {
		if (viewId == null || roleId == null) {
			return null;
		}
		ConcurrentMap<String, Object> paramMap = createParamMap();
		String query = "select rvp from RoleViewPrivilege rvp " + "where rvp.view.id=:viewId "
				+ "and rvp.role.id=:roleId";
		paramMap.put("viewId", viewId);
		paramMap.put("roleId", roleId);
		return getByQuery(query, paramMap);
	}

}
