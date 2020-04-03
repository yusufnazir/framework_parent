package software.simple.solutions.framework.core.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.entities.Country;
import software.simple.solutions.framework.core.exceptions.Arg;
import software.simple.solutions.framework.core.exceptions.ExceptionBuilder;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.CountryProperty;
import software.simple.solutions.framework.core.properties.SystemMessageProperty;
import software.simple.solutions.framework.core.repository.ICountryRepository;
import software.simple.solutions.framework.core.service.ICountryService;
import software.simple.solutions.framework.core.valueobjects.CountryVO;
import software.simple.solutions.framework.core.valueobjects.SuperVO;

@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Service
@ServiceRepository(claz = ICountryRepository.class)
public class CountryService extends SuperService implements ICountryService {

	private static final long serialVersionUID = -1080525256146667917L;

	private ICountryRepository countryRepository;

	@Override
	public <T, R extends SuperVO> T updateSingle(R valueObject) throws FrameworkException {
		CountryVO vo = (CountryVO) valueObject;

		if (StringUtils.isBlank(vo.getName())) {
			throw ExceptionBuilder.FRAMEWORK_EXCEPTION.build(SystemMessageProperty.FIELD_IS_REQUIRED,
					Arg.build().key(CountryProperty.NAME));
		}

		if (StringUtils.isBlank(vo.getAlpha2())) {
			throw ExceptionBuilder.FRAMEWORK_EXCEPTION.build(SystemMessageProperty.FIELD_IS_REQUIRED,
					Arg.build().key(CountryProperty.ALPHA2));
		}

		if (StringUtils.isBlank(vo.getAlpha3())) {
			throw ExceptionBuilder.FRAMEWORK_EXCEPTION.build(SystemMessageProperty.FIELD_IS_REQUIRED,
					Arg.build().key(CountryProperty.ALPHA3));
		}

		Country country = new Country();
		if (vo.isNew()) {
			Boolean isUnique = countryRepository.isAlpha2Unique(vo.getAlpha2(), null);
			if (!isUnique) {
				throw ExceptionBuilder.FRAMEWORK_EXCEPTION.build(SystemMessageProperty.FIELD_MUST_BE_UNIQUE,
						Arg.build().key(CountryProperty.ALPHA2));
			}

			isUnique = countryRepository.isAlpha3Unique(vo.getAlpha3(), null);
			if (!isUnique) {
				throw ExceptionBuilder.FRAMEWORK_EXCEPTION.build(SystemMessageProperty.FIELD_MUST_BE_UNIQUE,
						Arg.build().key(CountryProperty.ALPHA3));
			}

			isUnique = countryRepository.isNameUnique(vo.getName(), null);
			if (!isUnique) {
				throw ExceptionBuilder.FRAMEWORK_EXCEPTION.build(SystemMessageProperty.FIELD_MUST_BE_UNIQUE,
						Arg.build().key(CountryProperty.NAME));
			}
		} else {
			Boolean isUnique = countryRepository.isAlpha2Unique(vo.getAlpha2(), country.getId());
			if (!isUnique) {
				throw ExceptionBuilder.FRAMEWORK_EXCEPTION.build(SystemMessageProperty.FIELD_MUST_BE_UNIQUE,
						Arg.build().key(CountryProperty.ALPHA2));
			}

			isUnique = countryRepository.isAlpha3Unique(vo.getAlpha3(), country.getId());
			if (!isUnique) {
				throw ExceptionBuilder.FRAMEWORK_EXCEPTION.build(SystemMessageProperty.FIELD_MUST_BE_UNIQUE,
						Arg.build().key(CountryProperty.ALPHA3));
			}

			isUnique = countryRepository.isNameUnique(vo.getName(), country.getId());
			if (!isUnique) {
				throw ExceptionBuilder.FRAMEWORK_EXCEPTION.build(SystemMessageProperty.FIELD_MUST_BE_UNIQUE,
						Arg.build().key(CountryProperty.NAME));
			}
			
			country = get(Country.class, vo.getId());
		}
		country.setAlpha2(vo.getAlpha2());
		country.setAlpha3(vo.getAlpha3());
		country.setName(vo.getName());
		country.setActive(vo.getActive());

		return (T) saveOrUpdate(country, vo.isNew());
	}
}
