package software.simple.solutions.framework.core.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.entities.Role;
import software.simple.solutions.framework.core.entities.RoleCategory;
import software.simple.solutions.framework.core.entities.UserRole;
import software.simple.solutions.framework.core.exceptions.Arg;
import software.simple.solutions.framework.core.exceptions.ExceptionBuilder;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.RoleProperty;
import software.simple.solutions.framework.core.properties.SystemMessageProperty;
import software.simple.solutions.framework.core.repository.IRoleRepository;
import software.simple.solutions.framework.core.service.IRoleService;
import software.simple.solutions.framework.core.valueobjects.RoleVO;
import software.simple.solutions.framework.core.valueobjects.SuperVO;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@ServiceRepository(claz = IRoleRepository.class)
public class RoleService extends SuperService implements IRoleService {

	@Autowired
	private IRoleRepository roleRepository;

	@Override
	public List<UserRole> findRolesByUserId(Long userId) throws FrameworkException {
		return roleRepository.findRolesByUserId(userId);
	}

	@Override
	public <T, R extends SuperVO> T updateSingle(R valueObject) throws FrameworkException {
		RoleVO vo = (RoleVO) valueObject;

		if (StringUtils.isBlank(vo.getCode())) {
			throw ExceptionBuilder.FRAMEWORK_EXCEPTION.build(SystemMessageProperty.FIELD_IS_REQUIRED,
					Arg.build().key(RoleProperty.CODE));
		}
		if (StringUtils.isBlank(vo.getName())) {
			throw ExceptionBuilder.FRAMEWORK_EXCEPTION.build(SystemMessageProperty.FIELD_IS_REQUIRED,
					Arg.build().key(RoleProperty.NAME));
		}

		Role role = new Role();
		if (vo.isNew()) {
			Boolean codeUnique = isCodeUnique(Role.class, vo.getCode());
			if (!codeUnique) {
				throw ExceptionBuilder.FRAMEWORK_EXCEPTION.build(SystemMessageProperty.FIELD_MUST_BE_UNIQUE,
						Arg.build().key(RoleProperty.CODE));
			}
		} else {
			Boolean codeUnique = isCodeUnique(Role.class, vo.getCode(), vo.getId());
			if (!codeUnique) {
				throw ExceptionBuilder.FRAMEWORK_EXCEPTION.build(SystemMessageProperty.FIELD_MUST_BE_UNIQUE,
						Arg.build().key(RoleProperty.CODE));
			}
			role = get(Role.class, vo.getId());
		}
		role.setCode(vo.getCode().toUpperCase());
		role.setName(vo.getName());
		role.setDescription(vo.getDescription());
		role.setActive(vo.getActive());
		role.setRoleCategory(get(RoleCategory.class, vo.getRoleCategoryId()));

		return (T) saveOrUpdate(role, vo.isNew());
	}

}
