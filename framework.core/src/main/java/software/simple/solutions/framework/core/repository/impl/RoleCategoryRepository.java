package software.simple.solutions.framework.core.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.springframework.stereotype.Repository;

import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.IRoleCategoryRepository;
import software.simple.solutions.framework.core.util.ThreadAttributes;
import software.simple.solutions.framework.core.util.ThreadContext;

@Repository
public class RoleCategoryRepository extends GenericRepository implements IRoleCategoryRepository {

	@Override
	public Boolean isNameUnique(Class<?> cl, String name) throws FrameworkException {
		return isNameUnique(cl, name, null);
	}

	@Override
	public Boolean isNameUnique(Class<?> cl, String name, Long id) throws FrameworkException {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<?> criteria = builder.createQuery(cl);
		Root<?> from = criteria.from(cl);

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

	@Override
	public <T, R> List<R> getForListing(Class<T> cl, Boolean active) throws FrameworkException {
		ConcurrentMap<String, Object> paramMap = createParamMap();
		ThreadAttributes threadAttributes = ThreadContext.get();
		Long roleCategoryId = threadAttributes.getRoleCategoryId();
		String query = "select new software.simple.solutions.framework.core.pojo.ComboItem(id,name) from RoleCategory where 1=1 ";
		if (roleCategoryId.compareTo(1L) != 0) {
			query += " and id!= :roleCategoryId";
			paramMap.put("roleCategoryId", 1L);
		}

		query += " order by id ";

		return createListQuery(query, paramMap);
	}
}
