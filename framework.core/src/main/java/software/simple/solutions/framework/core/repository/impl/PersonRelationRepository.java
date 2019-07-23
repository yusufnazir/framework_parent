package software.simple.solutions.framework.core.repository.impl;

import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Repository;

import software.simple.solutions.framework.core.entities.PersonRelation;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.IPersonRelationRepository;

@Repository
public class PersonRelationRepository extends GenericRepository implements IPersonRelationRepository {

	private static final long serialVersionUID = -8771189452765019284L;

	@Override
	public PersonRelation getByPersonAndRelation(Long personId, Long relationId) throws FrameworkException {
		String query = "from PersonRelation where person.id=:id and relationType.id=:relationId";
		ConcurrentMap<String, Object> paramMap = createParamMap();
		paramMap.put("id", personId);
		paramMap.put("relationId", relationId);
		return getByQuery(query, paramMap);
	}

	@Override
	public Boolean isPersonOfType(Long personId, Long relationId) throws FrameworkException {
		String query = "select count(pr) from PersonRelation pr where pr.person.id=:id and pr.relationType.id=:relationId and pr.endDate is null";
		ConcurrentMap<String, Object> paramMap = createParamMap();
		paramMap.put("id", personId);
		paramMap.put("relationId", relationId);
		Long count = getByQuery(query, paramMap);
		return (count != null && count.compareTo(0L) > 0);
	}

}
