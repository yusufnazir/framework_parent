package software.simple.solutions.framework.core.service.impl;

import java.time.LocalDate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.constants.FileReference;
import software.simple.solutions.framework.core.constants.ReferenceKey;
import software.simple.solutions.framework.core.entities.Gender;
import software.simple.solutions.framework.core.entities.Person;
import software.simple.solutions.framework.core.exceptions.Arg;
import software.simple.solutions.framework.core.exceptions.ExceptionBuilder;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.PersonProperty;
import software.simple.solutions.framework.core.properties.SystemMessageProperty;
import software.simple.solutions.framework.core.repository.IPersonRepository;
import software.simple.solutions.framework.core.service.IPersonInformationService;
import software.simple.solutions.framework.core.service.IPersonService;
import software.simple.solutions.framework.core.util.TextToImage;
import software.simple.solutions.framework.core.valueobjects.EntityFileVO;
import software.simple.solutions.framework.core.valueobjects.PersonInformationVO;
import software.simple.solutions.framework.core.valueobjects.PersonVO;
import software.simple.solutions.framework.core.valueobjects.SuperVO;

@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Service
@ServiceRepository(claz = IPersonRepository.class)
public class PersonService extends SuperService implements IPersonService {

	private static final long serialVersionUID = 6213241143645131281L;

	@Autowired
	private IPersonInformationService personInformationService;

	@Autowired
	private FileService fileService;

	@Override
	public <T, R extends SuperVO> T updateSingle(R valueObject) throws FrameworkException {
		PersonVO vo = (PersonVO) valueObject;

		if (StringUtils.isBlank(vo.getFirstName())) {
			throw ExceptionBuilder.FRAMEWORK_EXCEPTION.build(SystemMessageProperty.FIELD_IS_REQUIRED,
					Arg.build().key(PersonProperty.FIRST_NAME));
		}
		if (StringUtils.isBlank(vo.getLastName())) {
			throw ExceptionBuilder.FRAMEWORK_EXCEPTION.build(SystemMessageProperty.FIELD_IS_REQUIRED,
					Arg.build().key(PersonProperty.LAST_NAME));
		}
		// if (vo.getDateOfBirth() == null) {
		// throw new FrameworkException(SystemMessageProperty.FIELD_IS_REQUIRED,
		// new Arg().key(PersonProperty.DATE_OF_BIRTH));
		// }
		if (vo.getDateOfBirth() != null && vo.getDateOfBirth().compareTo(LocalDate.now()) > 0) {
			throw ExceptionBuilder.FRAMEWORK_EXCEPTION.build(
					SystemMessageProperty.NOTIFICATION_DATE_CANNOT_BE_IN_THE_FUTURE,
					Arg.build().key(PersonProperty.DATE_OF_BIRTH));
		}
		// if (vo.getGenderId() == null) {
		// throw new FrameworkException(SystemMessageProperty.FIELD_IS_REQUIRED,
		// new Arg().key(PersonProperty.GENDER));
		// }

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
		person = saveOrUpdate(person, vo.isNew());
		if (vo.isNew()) {
			createPersonImage(person);
		}
		return (T) person;
	}

	@Override
	public void createPersonImage(Person person) throws FrameworkException {
		TextToImage textToImage = new TextToImage();
		byte[] pngImage = textToImage.createPngImage(person.getFirstName().substring(0, 1).toUpperCase()
				+ person.getLastName().substring(0, 1).toUpperCase());
		EntityFileVO entityFileVO = new EntityFileVO();
		entityFileVO.setActive(true);
		entityFileVO.setDatabase(true);
		entityFileVO.setFilename(person.getCode() + ".png");
		entityFileVO.setEntityName(ReferenceKey.PERSON);
		entityFileVO.setEntityId(person.getId().toString());
		entityFileVO.setTypeOfFile(FileReference.USER_PROFILE_IMAGE);
		entityFileVO.setFileObject(pngImage);
		fileService.upLoadFile(entityFileVO);
	}

	@Override
	public void updatePersonEmail(Long personId, String email) throws FrameworkException {
		PersonInformationVO personInformationVO = new PersonInformationVO();
		personInformationVO.setPersonId(personId);
		personInformationVO.setPrimaryEmail(email);
		personInformationService.updatePersonEmail(personInformationVO);
	}

	@Override
	public void updatePersonMobileNumber(Long personId, String mobileNumber) throws FrameworkException {
		PersonInformationVO personInformationVO = new PersonInformationVO();
		personInformationVO.setPersonId(personId);
		personInformationVO.setPrimaryContactNumber(mobileNumber);
		personInformationService.updatePersonMobileNumber(personInformationVO);
	}

}
