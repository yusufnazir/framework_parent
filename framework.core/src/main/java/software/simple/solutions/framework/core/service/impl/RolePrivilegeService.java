package software.simple.solutions.framework.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.entities.Privilege;
import software.simple.solutions.framework.core.entities.Role;
import software.simple.solutions.framework.core.entities.RolePrivilege;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.IRolePrivilegeRepository;
import software.simple.solutions.framework.core.repository.IViewRepository;
import software.simple.solutions.framework.core.service.IRolePrivilegeService;
import software.simple.solutions.framework.core.valueobjects.RolePrivilegeVO;
import software.simple.solutions.framework.core.valueobjects.SuperVO;

@Transactional(propagation=Propagation.REQUIRED, rollbackFor = Exception.class)
@Service
@ServiceRepository(claz = IRolePrivilegeRepository.class)
public class RolePrivilegeService extends SuperService implements IRolePrivilegeService {

	@Autowired
	private IRolePrivilegeRepository rolePrivilegeRepository;

	@Autowired
	private IViewRepository viewRepository;

	@Override
	public <T, R extends SuperVO> T updateSingle(R valueObject) throws FrameworkException {
		RolePrivilegeVO vo = (RolePrivilegeVO) valueObject;

		RolePrivilege rolePrivilege = new RolePrivilege();
		if (!vo.isNew()) {
			rolePrivilege = get(RolePrivilege.class, vo.getId());
		}
		rolePrivilege.setRole(get(Role.class, vo.getRoleId()));
		rolePrivilege.setPrivilege(get(Privilege.class, vo.getPrivilegeId()));
		rolePrivilege.setActive(vo.getActive());

		return (T) saveOrUpdate(rolePrivilege, vo.isNew());
	}

	@Override
	public void updateRolePrivileges(Long roleId, List<Long> privilegeIds) throws FrameworkException {
		rolePrivilegeRepository.updateRolePrivileges(roleId, privilegeIds);

	}

	@Override
	public List<Privilege> getPrivilegesByRole(Long roleId) throws FrameworkException {
		return rolePrivilegeRepository.getPrivilegesByRole(roleId);
	}

	// @Override
	// public Privilege getUserPrivilege(Long userId) throws FrameworkException
	// {
	// List<RolePrivilege> rolePrivileges =
	// rolePrivilegeRepository.getByUser(userId);
	// if (rolePrivileges == null || rolePrivileges.isEmpty()) {
	// return null;
	// } else if (rolePrivileges.size() == 1) {
	// RolePrivilege rolePrivilege = rolePrivileges.get(0);
	// return PrivilegeUtil.getPrivilege(rolePrivilege.getPrivilege());
	// } else {
	// Optional<RolePrivilege> optional = rolePrivileges.stream()
	// .max(Comparator.comparing(RolePrivilege::getPrivilege));
	// if (optional.isPresent()) {
	// RolePrivilege rolePrivilege = optional.get();
	// return PrivilegeUtil.getPrivilege(rolePrivilege.getPrivilege());
	// } else {
	// return null;
	// }
	// }
	// }

	// @Override
	// public Privilege getRolePrivilege(Long roleId) throws FrameworkException
	// {
	// List<RolePrivilege> rolePrivileges =
	// rolePrivilegeRepository.getByRoleId(roleId);
	// if (rolePrivileges == null || rolePrivileges.isEmpty()) {
	// return null;
	// } else if (rolePrivileges.size() == 1) {
	// RolePrivilege rolePrivilege = rolePrivileges.get(0);
	// return PrivilegeUtil.getPrivilege(rolePrivilege.getPrivilege());
	// } else {
	// Optional<RolePrivilege> optional = rolePrivileges.stream()
	// .max(Comparator.comparing(RolePrivilege::getPrivilege));
	// if (optional.isPresent()) {
	// RolePrivilege rolePrivilege = optional.get();
	// return PrivilegeUtil.getPrivilege(rolePrivilege.getPrivilege());
	// } else {
	// return null;
	// }
	// }
	// }

}
