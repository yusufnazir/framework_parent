package software.simple.solutions.framework.core.repository.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Repository;

import software.simple.solutions.framework.core.entities.Person;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.IPersonRepository;

@Repository
public class PersonRepository extends GenericRepository implements IPersonRepository {

	private static final long serialVersionUID = -2544966838144734886L;

	@Override
	public List<Person> listBySoundex(String soundexFirstName, String soundexLastName, LocalDate dateOfBirth,
			Long genderId) throws FrameworkException {
		ConcurrentMap<String, Object> paramMap = createParamMap();
		// @formatter:off
		String query = "from Person " 
				+ "where soundexFirstName=:soundexFirstName "
				+ "and soundexLastName=:soundexLastName " 
				+ "and gender.id=:genderId " 
				+ "and dateOfBirth=:dateOfBirth"
				;
		// @formatter:on

		paramMap.put("soundexFirstName", soundexFirstName);
		paramMap.put("soundexLastName", soundexLastName);
		paramMap.put("dateOfBirth", dateOfBirth);
		paramMap.put("genderId", genderId);
		return createListQuery(query, paramMap);
	}

	// @Override
	// public String createSearchQuery(Object o, ConcurrentMap<String, Object>
	// paramMap, PagingSetting pagingSetting)
	// throws FrameworkException {
	// PersonVO vo = (PersonVO) o;
	// String query = "select entity from Person entity where 1=1 ";
	//
	// query += createAndStringCondition("entity.firstName", vo.getFirstName(),
	// paramMap);
	// query += createAndStringCondition("entity.lastName", vo.getLastName(),
	// paramMap);
	//
	// query += getOrderBy(vo.getSortingHelper(),
	// "entity.firstName,entity.lastName",
	// OrderMapBuilder.build("1", "entity.firstName", "2", "entity.lastName"));
	// return query;
	// }

}
