package software.simple.solutions.framework.core.repository.impl;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Repository;

import software.simple.solutions.framework.core.entities.Privilege;
import software.simple.solutions.framework.core.entities.Role;
import software.simple.solutions.framework.core.entities.RolePrivilege;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.IRolePrivilegeRepository;

@Repository
public class RolePrivilegeRepository extends GenericRepository implements IRolePrivilegeRepository {

	@Override
	public List<RolePrivilege> getByUser(Long userId) throws FrameworkException {
		if (userId == null) {
			return null;
		}
		ConcurrentMap<String, Object> paramMap = createParamMap();
		String query = "select rvp from RolePrivilege rvp " + "left join UserRole ur on ur.applicationUser.id=:userId "
				+ "where rvp.role.id=ur.role.id";
		paramMap.put("userId", userId);
		return createListQuery(query, paramMap);
	}

	@Override
	public List<Privilege> getPrivilegesByRole(Long roleId) throws FrameworkException {
		if (roleId == null) {
			return null;
		}
		ConcurrentMap<String, Object> paramMap = createParamMap();
		String query = "select rp from RolePrivilege rp " + "where rp.role.id=:roleId";
		paramMap.put("roleId", roleId);
		return createListQuery(query, paramMap);
	}

	@Override
	public void updateRolePrivileges(Long userId, List<Long> privilegeIds) throws FrameworkException {
		ConcurrentMap<String, Object> paramMap = createParamMap();
		String delete = "delete from RolePrivilege where role.id=:roleId";
		paramMap.put("roleId", userId);
		updateByHql(delete, paramMap);

		Role role = get(Role.class, userId);
		for (Long privilegeId : privilegeIds) {
			RolePrivilege rolePrivilege = new RolePrivilege();
			rolePrivilege.setRole(role);
			rolePrivilege.setPrivilege(get(Privilege.class, privilegeId));
			updateSingle(rolePrivilege, true);
		}
	}

}
