package software.simple.solutions.framework.core.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.entities.Person;
import software.simple.solutions.framework.core.entities.PersonEmergencyContact;
import software.simple.solutions.framework.core.entities.RelationType;
import software.simple.solutions.framework.core.exceptions.Arg;
import software.simple.solutions.framework.core.exceptions.ExceptionBuilder;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.PersonEmergencyContactProperty;
import software.simple.solutions.framework.core.properties.SystemMessageProperty;
import software.simple.solutions.framework.core.repository.IPersonEmergencyContactRepository;
import software.simple.solutions.framework.core.service.IPersonEmergencyContactService;
import software.simple.solutions.framework.core.valueobjects.PersonEmergencyContactVO;
import software.simple.solutions.framework.core.valueobjects.SuperVO;

@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Service
@ServiceRepository(claz = IPersonEmergencyContactRepository.class)
public class PersonEmergencyContactService extends SuperService implements IPersonEmergencyContactService {

	private static final long serialVersionUID = -6850621216490458638L;

	@Override
	public <T, R extends SuperVO> T updateSingle(R valueObject) throws FrameworkException {
		PersonEmergencyContactVO vo = (PersonEmergencyContactVO) valueObject;
		PersonEmergencyContact personEmergencyContact = null;

		if (vo.getPersonId() == null) {
			throw ExceptionBuilder.VALIDATION_EXCEPTION.build(SystemMessageProperty.FIELD_IS_REQUIRED, vo.getLocale(),
					Arg.build().key(PersonEmergencyContactProperty.PERSON));
		}

		if (StringUtils.isBlank(vo.getName())) {
			throw ExceptionBuilder.VALIDATION_EXCEPTION.build(SystemMessageProperty.FIELD_IS_REQUIRED, vo.getLocale(),
					Arg.build().key(PersonEmergencyContactProperty.NAME));
		}

		if (StringUtils.isBlank(vo.getContactNumber())) {
			throw ExceptionBuilder.VALIDATION_EXCEPTION.build(SystemMessageProperty.FIELD_IS_REQUIRED, vo.getLocale(),
					Arg.build().key(PersonEmergencyContactProperty.CONTACT_NUMBER));
		}

		if (vo.getRelationTypeId() == null) {
			throw ExceptionBuilder.VALIDATION_EXCEPTION.build(SystemMessageProperty.FIELD_IS_REQUIRED, vo.getLocale(),
					Arg.build().key(PersonEmergencyContactProperty.RELATION_TYPE));
		}

		if (vo.isNew()) {
			personEmergencyContact = new PersonEmergencyContact();
		} else {
			personEmergencyContact = get(PersonEmergencyContact.class, vo.getId());
		}

		personEmergencyContact.setPerson(get(Person.class, vo.getPersonId()));
		personEmergencyContact.setName(vo.getName());
		personEmergencyContact.setContactNumber(vo.getContactNumber());
		personEmergencyContact.setRelationType(get(RelationType.class, vo.getRelationTypeId()));
		personEmergencyContact.setActive(vo.getActive());

		return (T) saveOrUpdate(personEmergencyContact, vo.isNew());
	}

}
