package software.simple.solutions.framework.core.repository.impl;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Repository;

import software.simple.solutions.framework.core.entities.Privilege;
import software.simple.solutions.framework.core.entities.RoleView;
import software.simple.solutions.framework.core.entities.RoleViewPrivilege;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.IRoleViewPrivilegeRepository;

@Repository
public class RoleViewPrivilegeRepository extends GenericRepository implements IRoleViewPrivilegeRepository {

	private static final long serialVersionUID = -541462290405216742L;

	@Override
	public List<Privilege> getByViewIdAndRoleId(Long viewId, Long roleId) throws FrameworkException {
		if (viewId == null || roleId == null) {
			return Collections.emptyList();
		}
		ConcurrentMap<String, Object> paramMap = createParamMap();
		String query = "select rvp.privilege from RoleViewPrivilege rvp left join RoleView rv on rv.id=rvp.roleView.id "
				+ "where rv.view.id=:viewId " + "and rv.role.id=:roleId";
		paramMap.put("viewId", viewId);
		paramMap.put("roleId", roleId);
		return createListQuery(query, paramMap);
	}

	@Override
	public List<String> getPrivilegesByViewIdAndRoleId(Long viewId, Long roleId) throws FrameworkException {
		if (viewId == null || roleId == null) {
			return Collections.emptyList();
		}
		ConcurrentMap<String, Object> paramMap = createParamMap();
		String query = "select distinct rvp.privilege.code from RoleViewPrivilege rvp left join RoleView rv on rv.id=rvp.roleView.id "
				+ "where rv.view.id=:viewId " + "and rv.role.id=:roleId";
		paramMap.put("viewId", viewId);
		paramMap.put("roleId", roleId);
		return createListQuery(query, paramMap);
	}

	@Override
	public List<String> getPrivilegesByViewIdAndUserId(Long viewId, Long applicationUserId) throws FrameworkException {
		if (viewId == null || applicationUserId == null) {
			return Collections.emptyList();
		}
		ConcurrentMap<String, Object> paramMap = createParamMap();
		String query = "select distinct rvp.privilege.code from RoleViewPrivilege rvp "
				+ "left join RoleView rv on rv.id=rvp.roleView.id " + "left join UserRole ur on ur.role.id=rv.role.id "
				+ "where rv.view.id=:viewId " + "and ur.applicationUser.id=:applicationUserId";
		paramMap.put("viewId", viewId);
		paramMap.put("applicationUserId", applicationUserId);
		return createListQuery(query, paramMap);
	}

	@Override
	public void updateRoleViewPrivileges(Long roleViewId, List<Long> privilegeIds) throws FrameworkException {
		ConcurrentMap<String, Object> paramMap = createParamMap();
		String delete = "delete from RoleViewPrivilege where roleView.id=:roleViewId";
		paramMap.put("roleViewId", roleViewId);
		updateByHql(delete, paramMap);

		RoleView roleView = get(RoleView.class, roleViewId);
		for (Long privilegeId : privilegeIds) {
			RoleViewPrivilege roleViewPrivilege = new RoleViewPrivilege();
			roleViewPrivilege.setRoleView(roleView);
			roleViewPrivilege.setPrivilege(get(Privilege.class, privilegeId));
			updateSingle(roleViewPrivilege, true);
		}
	}

	@Override
	public List<Privilege> getPrivilegesByRoleView(Long roleViewId) throws FrameworkException {
		if (roleViewId == null) {
			return Collections.emptyList();
		}
		ConcurrentMap<String, Object> paramMap = createParamMap();
		String query = "select rp.privilege from RoleViewPrivilege rp " + "where rp.roleView.id=:roleViewId";
		paramMap.put("roleViewId", roleViewId);
		return createListQuery(query, paramMap);
	}

}
