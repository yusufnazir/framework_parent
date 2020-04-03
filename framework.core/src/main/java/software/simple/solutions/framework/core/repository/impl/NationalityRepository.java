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

import software.simple.solutions.framework.core.entities.Nationality;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.repository.INationalityRepository;

@Repository
public class NationalityRepository extends GenericRepository implements INationalityRepository {

	private static final long serialVersionUID = 987656873738702380L;

	@Override
	public <T, R> List<R> getForListing(Class<T> cl, List<Long> ids, Boolean active) throws FrameworkException {
		ConcurrentMap<String, Object> paramMap = createParamMap();
		String query = "from Nationality where 1=1 ";
		if (ids != null && !ids.isEmpty()) {
			query += " or id in (:ids) ";
			paramMap.put("ids", ids);
		}
		if (active != null) {
			query += " and active=:active ";
			paramMap.put("active", active);
		}

		query += " order by name ";

		List<Nationality> nationalities = createListQuery(query, paramMap);
		if (nationalities != null) {
			List<ComboItem> list = nationalities.stream().map(p -> new ComboItem(p.getId(), p.getName()))
					.collect(Collectors.toList());
			return (List<R>) list;
		}
		return new ArrayList<R>();
	}

	@Override
	public Boolean isNameUnique(String name, Long id) throws FrameworkException {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<?> criteria = builder.createQuery(Nationality.class);
		Root<?> from = criteria.from(Nationality.class);

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
