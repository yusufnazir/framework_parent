package software.simple.solutions.framework.core.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.entities.Nationality;
import software.simple.solutions.framework.core.exceptions.Arg;
import software.simple.solutions.framework.core.exceptions.ExceptionBuilder;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.NationalityProperty;
import software.simple.solutions.framework.core.properties.SystemMessageProperty;
import software.simple.solutions.framework.core.repository.ICountryRepository;
import software.simple.solutions.framework.core.repository.INationalityRepository;
import software.simple.solutions.framework.core.service.INationalityService;
import software.simple.solutions.framework.core.valueobjects.NationalityVO;
import software.simple.solutions.framework.core.valueobjects.SuperVO;

@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Service
@ServiceRepository(claz = ICountryRepository.class)
public class NationalityService extends SuperService implements INationalityService {

	private static final long serialVersionUID = -1080525256146667917L;

	private INationalityRepository nationalityRepository;

	@Override
	public <T, R extends SuperVO> T updateSingle(R valueObject) throws FrameworkException {
		NationalityVO vo = (NationalityVO) valueObject;

		if (StringUtils.isBlank(vo.getName())) {
			throw ExceptionBuilder.FRAMEWORK_EXCEPTION.build(SystemMessageProperty.FIELD_IS_REQUIRED,
					Arg.build().key(NationalityProperty.NAME));
		}

		Nationality nationality = new Nationality();
		if (vo.isNew()) {
			Boolean isUnique = nationalityRepository.isNameUnique(vo.getName(), null);
			if (!isUnique) {
				throw ExceptionBuilder.FRAMEWORK_EXCEPTION.build(SystemMessageProperty.FIELD_MUST_BE_UNIQUE,
						Arg.build().key(NationalityProperty.NAME));
			}
		} else {
			Boolean isUnique = nationalityRepository.isNameUnique(vo.getName(), nationality.getId());
			if (!isUnique) {
				throw ExceptionBuilder.FRAMEWORK_EXCEPTION.build(SystemMessageProperty.FIELD_MUST_BE_UNIQUE,
						Arg.build().key(NationalityProperty.NAME));
			}
		}
		nationality.setName(vo.getName());
		nationality.setActive(vo.getActive());

		return (T) saveOrUpdate(nationality, vo.isNew());
	}
}
