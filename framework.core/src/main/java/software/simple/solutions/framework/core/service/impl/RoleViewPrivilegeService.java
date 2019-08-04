package software.simple.solutions.framework.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.entities.Privilege;
import software.simple.solutions.framework.core.entities.RoleView;
import software.simple.solutions.framework.core.entities.RoleViewPrivilege;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.IRoleViewPrivilegeRepository;
import software.simple.solutions.framework.core.repository.IViewRepository;
import software.simple.solutions.framework.core.service.IRoleViewPrivilegeService;
import software.simple.solutions.framework.core.valueobjects.RoleViewPrivilegeVO;
import software.simple.solutions.framework.core.valueobjects.SuperVO;

@Transactional(propagation=Propagation.REQUIRED, rollbackFor = Exception.class)
@Service
@ServiceRepository(claz = IRoleViewPrivilegeRepository.class)
public class RoleViewPrivilegeService extends SuperService implements IRoleViewPrivilegeService {

	@Autowired
	private IRoleViewPrivilegeRepository roleViewPrivilegeRepository;

	@Autowired
	private IViewRepository viewRepository;

	@Override
	public <T, R extends SuperVO> T updateSingle(R valueObject) throws FrameworkException {
		RoleViewPrivilegeVO vo = (RoleViewPrivilegeVO) valueObject;

		RoleViewPrivilege roleViewPrivilege = new RoleViewPrivilege();
		if (!vo.isNew()) {
			roleViewPrivilege = get(RoleViewPrivilege.class, vo.getId());
		}
		roleViewPrivilege.setRoleView(get(RoleView.class, vo.getRoleViewId()));
		roleViewPrivilege.setPrivilege(get(Privilege.class, vo.getPrivilegeId()));
		roleViewPrivilege.setActive(vo.getActive());

		return (T) saveOrUpdate(roleViewPrivilege, vo.isNew());
	}

	// @Override
	// public List<String> getUserViewPrivilege(Long viewId, Long userId) throws
	// FrameworkException {
	// List<RoleViewPrivilege> roleViewPrivileges =
	// roleViewPrivilegeRepository.getByViewAndUser(viewId, userId);
	// if (roleViewPrivileges == null || roleViewPrivileges.isEmpty()) {
	// return null;
	// } else if (roleViewPrivileges.size() == 1) {
	// RoleViewPrivilege roleViewPrivilege = roleViewPrivileges.get(0);
	// return PrivilegeUtil.getPrivilege(roleViewPrivilege.getPrivilege());
	// } else {
	// Optional<RoleViewPrivilege> optional = roleViewPrivileges.stream()
	// .max(Comparator.comparing(RoleViewPrivilege::getPrivilege));
	// if (optional.isPresent()) {
	// RoleViewPrivilege roleViewPrivilege = optional.get();
	// return PrivilegeUtil.getPrivilege(roleViewPrivilege.getPrivilege());
	// } else {
	// return null;
	// }
	// }
	// }

	@Override
	public List<Privilege> getRoleViewPrivilege(Long viewId, Long roleId) throws FrameworkException {
		return roleViewPrivilegeRepository.getByViewIdAndRoleId(viewId, roleId);
	}

	@Override
	public List<String> getPrivilegesByViewIdAndRoleId(Long viewId, Long roleId) throws FrameworkException {
		return roleViewPrivilegeRepository.getPrivilegesByViewIdAndRoleId(viewId, roleId);
	}
	
	@Override
	public List<String> getPrivilegesByViewIdAndUserId(Long viewId, Long applicationUserId) throws FrameworkException {
		return roleViewPrivilegeRepository.getPrivilegesByViewIdAndUserId(viewId, applicationUserId);
	}

	@Override
	public void updateRoleViewPrivileges(Long roleViewId, List<Long> privilegeIds) throws FrameworkException {
		roleViewPrivilegeRepository.updateRoleViewPrivileges(roleViewId, privilegeIds);
	}

	@Override
	public List<Privilege> getPrivilegesByRoleView(Long roleViewId) throws FrameworkException {
		return roleViewPrivilegeRepository.getPrivilegesByRoleView(roleViewId);
	}

	// @Override
	// public List<String> getUserViewPrivilege(String className, Long userId)
	// throws FrameworkException {
	// View view = viewRepository.getByClassName(className);
	// if (view == null) {
	// return null;
	// }
	// return getUserViewPrivilege(view.getId(), userId);
	// }

	// @Override
	// public List<String> getRoleViewPrivilege(String className, Long roleId)
	// throws FrameworkException {
	// View view = viewRepository.getByClassName(className);
	// if (view == null) {
	// return null;
	// }
	// return getRoleViewPrivilege(view.getId(), roleId);
	// }

}
