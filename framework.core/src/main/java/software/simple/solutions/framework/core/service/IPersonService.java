package software.simple.solutions.framework.core.service;

import java.time.LocalDate;
import java.util.List;

import software.simple.solutions.framework.core.entities.Person;
import software.simple.solutions.framework.core.exceptions.FrameworkException;

public interface IPersonService extends ISuperService {

	void updatePersonEmail(Long personId, String email) throws FrameworkException;

	void updatePersonMobileNumber(Long personId, String mobileNumber) throws FrameworkException;

	void createPersonImage(Person person) throws FrameworkException;

	List<Person> listBySoundex(String firstName, String lastName, LocalDate dateOfBirth, Long genderId)
			throws FrameworkException;

}
