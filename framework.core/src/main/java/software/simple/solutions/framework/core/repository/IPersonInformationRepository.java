package software.simple.solutions.framework.core.repository;

import software.simple.solutions.framework.core.entities.PersonInformation;
import software.simple.solutions.framework.core.exceptions.FrameworkException;

public interface IPersonInformationRepository extends IGenericRepository{

	PersonInformation getByPerson(Long personId) throws FrameworkException;

}
