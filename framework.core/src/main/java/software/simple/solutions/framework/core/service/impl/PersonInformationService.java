package software.simple.solutions.framework.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.entities.Person;
import software.simple.solutions.framework.core.entities.PersonInformation;
import software.simple.solutions.framework.core.exceptions.Arg;
import software.simple.solutions.framework.core.exceptions.ExceptionBuilder;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.PersonEmergencyContactProperty;
import software.simple.solutions.framework.core.properties.PersonInformationProperty;
import software.simple.solutions.framework.core.properties.SystemMessageProperty;
import software.simple.solutions.framework.core.repository.IPersonInformationRepository;
import software.simple.solutions.framework.core.service.IPersonInformationService;
import software.simple.solutions.framework.core.valueobjects.PersonInformationVO;
import software.simple.solutions.framework.core.valueobjects.SuperVO;

@Transactional(propagation=Propagation.REQUIRED, rollbackFor = Exception.class)
@Service
@ServiceRepository(claz = IPersonInformationRepository.class)
public class PersonInformationService extends SuperService implements IPersonInformationService {

	private static final long serialVersionUID = 3245860402021694519L;
	@Autowired
	private IPersonInformationRepository personInformationRepository;

	@Override
	public PersonInformation getByPerson(Long personId) throws FrameworkException {
		return personInformationRepository.getByPerson(personId);
	}

	@Override
	public <T, R extends SuperVO> T updateSingle(R valueObject) throws FrameworkException {
		PersonInformationVO vo = (PersonInformationVO) valueObject;
		
		if (vo.getPersonId() == null) {
			throw ExceptionBuilder.VALIDATION_EXCEPTION.build(SystemMessageProperty.FIELD_IS_REQUIRED, vo.getLocale(),
					Arg.build().key(PersonInformationProperty.PERSON));
		}
		
		PersonInformation personInformation = null;
		if (vo.isNew()) {
			personInformation = new PersonInformation();
		} else {
			personInformation = get(PersonInformation.class, vo.getId());
		}
		
		personInformation.setPerson(get(Person.class, vo.getPersonId()));
		personInformation.setPrimaryContactNumber(vo.getPrimaryContactNumber());
		personInformation.setPrimaryEmail(vo.getPrimaryEmail());
		personInformation.setSecondaryContactNumber(vo.getSecondaryContactNumber());
		personInformation.setSecondaryEmail(vo.getSecondaryEmail());
		personInformation.setActive(vo.getActive());

		return (T) saveOrUpdate(personInformation, vo.isNew());
	}

	@Override
	public PersonInformation updatePersonEmail(PersonInformationVO vo) throws FrameworkException {
		PersonInformation personInformation = getByPerson(vo.getPersonId());
		boolean isNew = personInformation==null;
		if (personInformation == null) {
			personInformation = new PersonInformation();
			personInformation.setPerson(get(Person.class, vo.getPersonId()));
			personInformation.setPrimaryEmail(vo.getPrimaryEmail());
		} else {
			personInformation.setPrimaryEmail(vo.getPrimaryEmail());
		}
		personInformation.setActive(true);
		return saveOrUpdate(personInformation, isNew);
	}

	@Override
	public PersonInformation updatePersonMobileNumber(PersonInformationVO vo) throws FrameworkException {
		PersonInformation personInformation = getByPerson(vo.getPersonId());
		boolean isNew = personInformation==null;
		if (personInformation == null) {
			personInformation = new PersonInformation();
			personInformation.setPerson(get(Person.class, vo.getPersonId()));
		}
		personInformation.setPrimaryContactNumber(vo.getPrimaryContactNumber());
		personInformation.setActive(true);
		return saveOrUpdate(personInformation, isNew);

	}

	@Override
	public String getEmail(Long personId) throws FrameworkException {
		PersonInformation personInformation = getByPerson(personId);
		if (personInformation != null) {
			return personInformation.getPrimaryEmail();
		}
		return null;
	}

}
