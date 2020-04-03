package software.simple.solutions.framework.core.repository.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.springframework.stereotype.Repository;

import software.simple.solutions.framework.core.entities.Holiday;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.IHolidayRepository;

@Repository
public class HolidayRepository extends GenericRepository implements IHolidayRepository {

	private static final long serialVersionUID = -1749321110050776759L;

	@Override
	public Boolean isDateUnique(LocalDate date, Long id) throws FrameworkException {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<?> criteria = builder.createQuery(Holiday.class);
		Root<?> from = criteria.from(Holiday.class);

		Predicate equal = builder.equal(from.get("date"), date);
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
