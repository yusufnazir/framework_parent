package software.simple.solutions.framework.core.repository;

import software.simple.solutions.framework.core.entities.PersonRelation;
import software.simple.solutions.framework.core.exceptions.FrameworkException;

public interface IPersonRelationRepository extends IGenericRepository {

	PersonRelation getByPersonAndRelation(Long personId, Long relationId) throws FrameworkException;

	Boolean isPersonOfType(Long personId, Long relationId) throws FrameworkException;

}
