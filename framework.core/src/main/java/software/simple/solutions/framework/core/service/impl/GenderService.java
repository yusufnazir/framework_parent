package software.simple.solutions.framework.core.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.entities.Gender;
import software.simple.solutions.framework.core.exceptions.Arg;
import software.simple.solutions.framework.core.exceptions.ExceptionBuilder;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.GenderProperty;
import software.simple.solutions.framework.core.properties.SystemMessageProperty;
import software.simple.solutions.framework.core.repository.IGenderRepository;
import software.simple.solutions.framework.core.service.IGenderService;
import software.simple.solutions.framework.core.valueobjects.GenderVO;
import software.simple.solutions.framework.core.valueobjects.SuperVO;

@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Service
@ServiceRepository(claz = IGenderRepository.class)
public class GenderService extends SuperService implements IGenderService {

	@Autowired
	private IGenderRepository genderRepository;

	@Override
	public <T, R extends SuperVO> T updateSingle(R valueObject) throws FrameworkException {
		GenderVO vo = (GenderVO) valueObject;

		if (StringUtils.isBlank(vo.getName())) {
			throw ExceptionBuilder.FRAMEWORK_EXCEPTION.build(SystemMessageProperty.FIELD_IS_REQUIRED,
					Arg.build().key(GenderProperty.NAME));
		}

		Gender gender = new Gender();
		if (vo.isNew()) {
			Boolean isNameUnique = genderRepository.isNameUnique(null, vo.getName());
			if (!isNameUnique) {
				throw ExceptionBuilder.FRAMEWORK_EXCEPTION.build(SystemMessageProperty.FIELD_MUST_BE_UNIQUE,
						Arg.build().key(GenderProperty.NAME));
			}
			Boolean isKeyUnique = genderRepository.isKeyUnique(null, vo.getName());
			if (!isKeyUnique) {
				throw ExceptionBuilder.FRAMEWORK_EXCEPTION.build(SystemMessageProperty.FIELD_MUST_BE_UNIQUE,
						Arg.build().key(GenderProperty.PROPERTY_KEY));
			}
		} else {
			Boolean isNameUnique = genderRepository.isNameUnique(vo.getId(), vo.getName());
			if (!isNameUnique) {
				throw ExceptionBuilder.FRAMEWORK_EXCEPTION.build(SystemMessageProperty.FIELD_MUST_BE_UNIQUE,
						Arg.build().key(GenderProperty.NAME));
			}
			Boolean isKeyUnique = genderRepository.isKeyUnique(vo.getId(), vo.getName());
			if (!isKeyUnique) {
				throw ExceptionBuilder.FRAMEWORK_EXCEPTION.build(SystemMessageProperty.FIELD_MUST_BE_UNIQUE,
						Arg.build().key(GenderProperty.PROPERTY_KEY));
			}
			gender = get(Gender.class, vo.getId());
		}
		gender.setName(vo.getName());
		gender.setActive(vo.getActive());
		gender.setKey(vo.getKey());

		return (T) saveOrUpdate(gender, vo.isNew());
	}

}
