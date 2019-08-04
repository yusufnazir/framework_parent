package software.simple.solutions.framework.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.entities.ApplicationUser;
import software.simple.solutions.framework.core.entities.Role;
import software.simple.solutions.framework.core.entities.UserRole;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.IUserRoleRepository;
import software.simple.solutions.framework.core.service.IUserRoleService;
import software.simple.solutions.framework.core.valueobjects.SuperVO;
import software.simple.solutions.framework.core.valueobjects.UserRoleVO;

@Transactional(propagation=Propagation.REQUIRED, rollbackFor = Exception.class)
@Service
@ServiceRepository(claz = IUserRoleRepository.class)
public class UserRoleService extends SuperService implements IUserRoleService {

	@Autowired
	private IUserRoleRepository userRoleRepository;

	@Override
	public List<Role> findRolesByUser(Long userId) throws FrameworkException {
		return userRoleRepository.findRolesByUser(userId);
	}

	@Override
	public <T, R extends SuperVO> T updateSingle(R valueObject) throws FrameworkException {
		UserRoleVO vo = (UserRoleVO) valueObject;

		UserRole userRole = new UserRole();
		if (vo.isNew()) {
			userRole = userRoleRepository.getByUserAndRole(vo.getUserId(), vo.getRoleId());
			if (userRole == null) {
				userRole = new UserRole();
			}
		} else {
			userRole = get(UserRole.class, vo.getId());
		}
		userRole.setApplicationUser(get(ApplicationUser.class, vo.getUserId()));
		userRole.setRole(get(Role.class, vo.getRoleId()));
		userRole.setActive(vo.getActive());

		return (T) saveOrUpdate(userRole, vo.isNew());
	}

}
