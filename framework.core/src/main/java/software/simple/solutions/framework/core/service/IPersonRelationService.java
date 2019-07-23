package software.simple.solutions.framework.core.service;

import software.simple.solutions.framework.core.entities.PersonRelation;
import software.simple.solutions.framework.core.exceptions.FrameworkException;

public interface IPersonRelationService extends ISuperService {

	PersonRelation getByPerson(Long personId, Long relationId) throws FrameworkException;

	Boolean isPersonOfType(Long personId,  Long relationId) throws FrameworkException;

}
