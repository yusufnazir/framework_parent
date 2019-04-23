package software.simple.solutions.framework.core.service.impl;

import java.time.LocalDate;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.entities.Gender;
import software.simple.solutions.framework.core.entities.Person;
import software.simple.solutions.framework.core.exceptions.Arg;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.PersonProperty;
import software.simple.solutions.framework.core.properties.SystemMessageProperty;
import software.simple.solutions.framework.core.repository.IPersonRepository;
import software.simple.solutions.framework.core.service.IPersonService;
import software.simple.solutions.framework.core.valueobjects.PersonVO;
import software.simple.solutions.framework.core.valueobjects.SuperVO;

@Transactional
@Service
@ServiceRepository(claz = IPersonRepository.class)
public class PersonService extends SuperService implements IPersonService {

	@Override
	public <T, R extends SuperVO> T updateSingle(R valueObject) throws FrameworkException {
		PersonVO vo = (PersonVO) valueObject;

		if (StringUtils.isBlank(vo.getFirstName())) {
			throw new FrameworkException(SystemMessageProperty.FIELD_IS_REQUIRED,
					new Arg().key(PersonProperty.FIRST_NAME));
		}
		if (StringUtils.isBlank(vo.getLastName())) {
			throw new FrameworkException(SystemMessageProperty.FIELD_IS_REQUIRED,
					new Arg().key(PersonProperty.LAST_NAME));
		}
		if (vo.getDateOfBirth() == null) {
			throw new FrameworkException(SystemMessageProperty.FIELD_IS_REQUIRED,
					new Arg().key(PersonProperty.DATE_OF_BIRTH));
		}
		if (vo.getDateOfBirth().compareTo(LocalDate.now()) > 0) {
			throw new FrameworkException(SystemMessageProperty.NOTIFICATION_DATE_CANNOT_BE_IN_THE_FUTURE,
					new Arg().key(PersonProperty.DATE_OF_BIRTH));
		}
		if (vo.getGenderId() == null) {
			throw new FrameworkException(SystemMessageProperty.FIELD_IS_REQUIRED,
					new Arg().key(PersonProperty.GENDER));
		}

		Person person = null;
		if (vo.isNew()) {
			person = new Person();
		} else {
			person = get(Person.class, vo.getId());
		}
		person.setFirstName(vo.getFirstName());
		person.setMiddleName(vo.getMiddleName());
		person.setLastName(vo.getLastName());
		person.setGender(get(Gender.class, vo.getGenderId()));
		person.setDateOfBirth(vo.getDateOfBirth());
		person.setActive(vo.getActive());

		return (T) saveOrUpdate(person, vo.isNew());
	}

}
