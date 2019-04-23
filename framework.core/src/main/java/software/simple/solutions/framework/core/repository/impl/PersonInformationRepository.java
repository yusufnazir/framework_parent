package software.simple.solutions.framework.core.repository.impl;

import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Repository;

import software.simple.solutions.framework.core.entities.PersonInformation;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.IPersonInformationRepository;

@Repository
public class PersonInformationRepository extends GenericRepository implements IPersonInformationRepository {

	public static final String GET_BY_PERSON = "getByPerson";

	// @Override
	// public String createSearchQuery(Object o, ConcurrentMap<String, Object>
	// paramMap, PagingSetting pagingSetting)
	// throws FrameworkException {
	// PersonInformationVO vo = (PersonInformationVO) o;
	// String query = "select entity from PersonInformation entity where 1=1 ";
	//
	// query += buildIdFragment(paramMap, vo.getPersonId(), "entity.person.id");
	// query += buildStringFragment(paramMap,
	// vo.getPrimaryContactNumberInterval(), "entity.primaryContactNumber");
	// query += buildStringFragment(paramMap, vo.getPrimaryEmailInterval(),
	// "entity.primaryEmail");
	// query += buildStringFragment(paramMap,
	// vo.getSecondaryContactNumberInterval(), "entity.secondaryContactNumber");
	// query += buildStringFragment(paramMap, vo.getSecondaryEmailInterval(),
	// "entity.secondaryEmail");
	//
	// query += getOrderBy(vo.getSortingHelper(), "entity.person.id, entity.id",
	// OrderMapBuilder.build(PersonProperty.PERSON, "entity.person.id",
	// PersonInformationProperty.PRIMARY_CONTACT_NUMBER,
	// "entity.primaryContactNumber"));
	// return query;
	// }

	@Override
	public PersonInformation getByPerson(Long personId) throws FrameworkException {
		if (personId == null) {
			return null;
		}
		ConcurrentMap<String, Object> paramMap = createParamMap();
		String query = "select pc from PersonInformation pc where pc.person.id=:id";
		paramMap.put("id", personId);
		return getByQuery(query, paramMap);
	}

}
