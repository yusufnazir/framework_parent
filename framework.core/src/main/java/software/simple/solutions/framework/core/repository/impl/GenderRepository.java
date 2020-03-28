package software.simple.solutions.framework.core.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.springframework.stereotype.Repository;

import software.simple.solutions.framework.core.entities.Gender;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.repository.IGenderRepository;

@Repository
public class GenderRepository extends GenericRepository implements IGenderRepository {

	@Override
	public <T, R> List<R> getForListing(Class<T> cl, List<Long> ids, Boolean active) throws FrameworkException {
		ConcurrentMap<String, Object> paramMap = createParamMap();
		String query = "from Gender where 1=1 ";
		if (ids != null && !ids.isEmpty()) {
			query += " or id in (:ids) ";
			paramMap.put("ids", ids);
		}
		if (active != null) {
			query += " and active=:active ";
			paramMap.put("active", active);
		}

		query += " order by id ";

		List<Gender> list = createListQuery(query, paramMap);
		if (list == null) {
			return new ArrayList<R>();
		}
		return (List<R>) list.stream().map(p -> new ComboItem(p.getId(), p.getName())).collect(Collectors.toList());
	}

	@Override
	public Boolean isNameUnique(Long id, String name) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<?> criteria = builder.createQuery(Gender.class);
		Root<?> from = criteria.from(Gender.class);

		Predicate equal = builder.equal(from.get("name"), name);
		Predicate notEqual = builder.notEqual(from.get("id"), id);
		List<Predicate> predicates = new ArrayList<Predicate>();
		predicates.add(equal);
		if (id != null) {
			predicates.add(notEqual);
		}

		criteria.where(predicates.toArray(new Predicate[predicates.size()]));
		criteria.select((Selection) builder.count(from));
		Long count = (Long) entityManager.createQuery(criteria).getSingleResult();
		return (count == null || count.compareTo(0L) == 0);
	}

}
