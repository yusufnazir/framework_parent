package software.simple.solutions.framework.core.service;

import software.simple.solutions.framework.core.entities.PersonInformation;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.valueobjects.PersonInformationVO;

public interface IPersonInformationService extends ISuperService {

	PersonInformation getByPerson(Long personId) throws FrameworkException;

	public PersonInformation updatePersonEmail(PersonInformationVO vo) throws FrameworkException;

	PersonInformation updatePersonMobileNumber(PersonInformationVO vo) throws FrameworkException;

	String getEmail(Long personId) throws FrameworkException;

}
