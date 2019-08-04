package software.simple.solutions.framework.core.service;

import software.simple.solutions.framework.core.entities.Person;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.valueobjects.ApplicationUserVO;

public interface IPersonService extends ISuperService {

	void updatePersonEmail(Long personId, String email) throws FrameworkException;

	void updatePersonMobileNumber(Long personId, String mobileNumber) throws FrameworkException;

	void createPersonImage(Person person) throws FrameworkException;

}
