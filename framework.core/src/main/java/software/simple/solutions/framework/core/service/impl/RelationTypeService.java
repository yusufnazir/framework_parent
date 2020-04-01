package software.simple.solutions.framework.core.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.entities.RelationType;
import software.simple.solutions.framework.core.entities.Role;
import software.simple.solutions.framework.core.exceptions.Arg;
import software.simple.solutions.framework.core.exceptions.ExceptionBuilder;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.RoleProperty;
import software.simple.solutions.framework.core.properties.SystemMessageProperty;
import software.simple.solutions.framework.core.repository.IRelationTypeRepository;
import software.simple.solutions.framework.core.service.IRelationTypeService;
import software.simple.solutions.framework.core.valueobjects.RelationTypeVO;
import software.simple.solutions.framework.core.valueobjects.SuperVO;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@ServiceRepository(claz = IRelationTypeRepository.class)
public class RelationTypeService extends SuperService implements IRelationTypeService {

	private static final long serialVersionUID = -3825279379510043625L;

	@Override
	public <T, R extends SuperVO> T updateSingle(R valueObject) throws FrameworkException {
		RelationTypeVO vo = (RelationTypeVO) valueObject;

		if (StringUtils.isBlank(vo.getCode())) {
			throw ExceptionBuilder.FRAMEWORK_EXCEPTION.build(SystemMessageProperty.FIELD_IS_REQUIRED,
					Arg.build().key(RoleProperty.CODE));
		}
		if (StringUtils.isBlank(vo.getName())) {
			throw ExceptionBuilder.FRAMEWORK_EXCEPTION.build(SystemMessageProperty.FIELD_IS_REQUIRED,
					Arg.build().key(RoleProperty.NAME));
		}

		RelationType relationType = new RelationType();
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
			relationType = get(RelationType.class, vo.getId());
		}
		relationType.setCode(vo.getCode().toUpperCase());
		relationType.setName(vo.getName());
		relationType.setActive(vo.getActive());

		return (T) saveOrUpdate(relationType, vo.isNew());
	}

}
