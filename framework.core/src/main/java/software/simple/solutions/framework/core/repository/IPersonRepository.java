package software.simple.solutions.framework.core.repository;

import java.time.LocalDate;
import java.util.List;

import software.simple.solutions.framework.core.entities.Person;
import software.simple.solutions.framework.core.exceptions.FrameworkException;

public interface IPersonRepository extends IGenericRepository{

	List<Person> listBySoundex(String soundexFirstName, String soundexLastName, LocalDate dateOfBirth, Long genderId) throws FrameworkException;

}
