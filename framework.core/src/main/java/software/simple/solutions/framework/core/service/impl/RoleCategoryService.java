package software.simple.solutions.framework.core.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.entities.RoleCategory;
import software.simple.solutions.framework.core.exceptions.Arg;
import software.simple.solutions.framework.core.exceptions.ExceptionBuilder;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.RoleCategoryProperty;
import software.simple.solutions.framework.core.properties.SystemMessageProperty;
import software.simple.solutions.framework.core.repository.IRoleCategoryRepository;
import software.simple.solutions.framework.core.service.IRoleCategoryService;
import software.simple.solutions.framework.core.valueobjects.RoleCategoryVO;
import software.simple.solutions.framework.core.valueobjects.SuperVO;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@ServiceRepository(claz = IRoleCategoryRepository.class)
public class RoleCategoryService extends SuperService implements IRoleCategoryService {

	@Autowired
	private IRoleCategoryRepository roleCategoryRepository;

	@Override
	public <T, R extends SuperVO> T updateSingle(R valueObject) throws FrameworkException {
		RoleCategoryVO vo = (RoleCategoryVO) valueObject;

		if (StringUtils.isBlank(vo.getName())) {
			throw ExceptionBuilder.FRAMEWORK_EXCEPTION.build(SystemMessageProperty.FIELD_IS_REQUIRED,
					Arg.build().key(RoleCategoryProperty.NAME));
		}

		RoleCategory roleCategory = new RoleCategory();
		if (vo.isNew()) {
			Boolean nameUnique = roleCategoryRepository.isNameUnique(RoleCategory.class, vo.getName());
			if (!nameUnique) {
				throw ExceptionBuilder.FRAMEWORK_EXCEPTION.build(SystemMessageProperty.FIELD_MUST_BE_UNIQUE,
						Arg.build().key(RoleCategoryProperty.NAME));
			}
		} else {
			Boolean nameUnique = roleCategoryRepository.isNameUnique(RoleCategory.class, vo.getName(), vo.getId());
			if (!nameUnique) {
				throw ExceptionBuilder.FRAMEWORK_EXCEPTION.build(SystemMessageProperty.FIELD_MUST_BE_UNIQUE,
						Arg.build().key(RoleCategoryProperty.NAME));
			}
			roleCategory = get(RoleCategory.class, vo.getId());
		}
		roleCategory.setName(vo.getName());

		return (T) saveOrUpdate(roleCategory, vo.isNew());
	}

}
