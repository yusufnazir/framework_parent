package software.simple.solutions.framework.core.repository.impl;

import org.springframework.stereotype.Repository;

import software.simple.solutions.framework.core.repository.IPersonRepository;

@Repository
public class PersonRepository extends GenericRepository implements IPersonRepository {

//	@Override
//	public String createSearchQuery(Object o, ConcurrentMap<String, Object> paramMap, PagingSetting pagingSetting)
//			throws FrameworkException {
//		PersonVO vo = (PersonVO) o;
//		String query = "select entity from Person entity where 1=1 ";
//
//		query += createAndStringCondition("entity.firstName", vo.getFirstName(), paramMap);
//		query += createAndStringCondition("entity.lastName", vo.getLastName(), paramMap);
//
//		query += getOrderBy(vo.getSortingHelper(), "entity.firstName,entity.lastName",
//				OrderMapBuilder.build("1", "entity.firstName", "2", "entity.lastName"));
//		return query;
//	}

}
