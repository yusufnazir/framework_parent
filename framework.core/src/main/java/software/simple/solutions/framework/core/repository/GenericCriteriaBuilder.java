package software.simple.solutions.framework.core.repository;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import software.simple.solutions.framework.core.annotations.FilterFieldProperties;
import software.simple.solutions.framework.core.annotations.FilterFieldProperty;
import software.simple.solutions.framework.core.constants.Operator;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.DateInterval;
import software.simple.solutions.framework.core.pojo.DateTimeInterval;
import software.simple.solutions.framework.core.pojo.DecimalInterval;
import software.simple.solutions.framework.core.pojo.Interval;
import software.simple.solutions.framework.core.pojo.LongInterval;
import software.simple.solutions.framework.core.pojo.StringInterval;
import software.simple.solutions.framework.core.util.ColumnSort;
import software.simple.solutions.framework.core.util.SortingHelper;
import software.simple.solutions.framework.core.valueobjects.SuperVO;

public class GenericCriteriaBuilder {

	public static final Logger logger = LogManager.getLogger(GenericCriteriaBuilder.class);

	private CriteriaBuilder criteriaBuilder;
	private List<Predicate> wherePredicates;
	private Predicate whereClause;
	private Class<?> entity;
	private Map<String, Field> fieldMap;
	private Map<String, Join> joinMap;
	private Map<String, Object> queryValuesMap;
	private List<Order> orders;
	private Long currentUserRoleId;
	private JoinLeftBuilder joinLeftBuilder;

	public GenericCriteriaBuilder() {
		super();
		wherePredicates = new ArrayList<Predicate>();
		joinMap = new HashMap<String, Join>();
		queryValuesMap = new HashMap<String, Object>();
	}

	public <T> CriteriaQuery<Object> build(Class<T> cl, Object vo, CriteriaBuilder criteriaBuilder) throws FrameworkException {
		this.entity = cl;
		this.criteriaBuilder = criteriaBuilder;

		updateEntityFieldMap();
		CriteriaQuery<Object> query = null;
		try {
			query = criteriaBuilder.createQuery();
			Root<?> root = query.from(cl);

			query.select(root);

			CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
			Root<?> countRoot = countQuery.from(entity);
			countQuery.select(criteriaBuilder.count(countRoot));

			/*
			 * Create whereclause parameters
			 */
			Field[] declaredFields = vo.getClass().getDeclaredFields();
			for (Field declaredField : declaredFields) {
				if (declaredField.isAnnotationPresent(FilterFieldProperty.class)) {
					updateQueryValuesMap(declaredField, vo);
				}
			}

			// retrieveCurrentUserRole(vo, root);

			// handleSoftDeleteCriteria(root);

			determineWherePredicates(root);

			criteriaBuilder = createLeftJoin(root, criteriaBuilder);

			/*
			 * Create whereclause
			 */
			whereClause = criteriaBuilder.and(wherePredicates.toArray(new Predicate[wherePredicates.size()]));

			/*
			 * Add where clause
			 */
			query.where(whereClause);

			buildOrderByClause(root, vo);

			query.orderBy(orders);

		} catch (IllegalArgumentException | IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		}
		return query;
	}

	public CriteriaBuilder createLeftJoin(Root<?> root, CriteriaBuilder criteriaBuilder) throws FrameworkException {
		if (joinLeftBuilder != null) {
			criteriaBuilder = joinLeftBuilder.build(root, criteriaBuilder);
		}
		return criteriaBuilder;
	}

	public <T> CriteriaQuery<Long> buildCount(Class<T> cl, Object vo, CriteriaBuilder criteriaBuilder) throws FrameworkException {
		this.entity = cl;
		this.criteriaBuilder = criteriaBuilder;

		CriteriaQuery<Long> query = null;
		updateEntityFieldMap();
		try {
			query = criteriaBuilder.createQuery(Long.class);
			Root<?> root = query.from(entity);
			query.select(criteriaBuilder.count(root));

			/*
			 * Create whereclause parameters
			 */
			Field[] declaredFields = vo.getClass().getDeclaredFields();
			for (Field declaredField : declaredFields) {
				if (declaredField.isAnnotationPresent(FilterFieldProperty.class)) {
					updateQueryValuesMap(declaredField, vo);
				}
			}

			determineWherePredicates(root);

			criteriaBuilder = createLeftJoin(root, criteriaBuilder);

			/*
			 * Create whereclause
			 */
			whereClause = criteriaBuilder.and(wherePredicates.toArray(new Predicate[wherePredicates.size()]));

			/*
			 * Add where clause
			 */
			query.where(whereClause);

		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return query;
	}

	private void updateEntityFieldMap() {
		fieldMap = new HashMap<String, Field>();
		Field[] declaredFields = entity.getDeclaredFields();
		for (Field field : declaredFields) {
			if (field.isAnnotationPresent(FilterFieldProperty.class)) {
				FilterFieldProperty fieldProperty = field.getAnnotation(FilterFieldProperty.class);
				fieldMap.put(fieldProperty.fieldProperty(), field);
			} else if (field.isAnnotationPresent(FilterFieldProperties.class)) {
				FilterFieldProperties filterFieldProperties = field.getAnnotation(FilterFieldProperties.class);
				FilterFieldProperty[] fieldProperties = filterFieldProperties.fieldProperties();
				for (FilterFieldProperty fieldProperty : fieldProperties) {
					fieldMap.put(fieldProperty.fieldProperty(), field);
				}
			}
		}
	}

	private void buildOrderByClause(Root<?> root, Object vo) {
		SuperVO superVO = (SuperVO) vo;
		SortingHelper sortingHelper = superVO.getSortingHelper();
		orders = new ArrayList<Order>();
		if (sortingHelper != null && sortingHelper.getColumnsSort() != null
				&& !sortingHelper.getColumnsSort().isEmpty()) {
			List<ColumnSort> columnsSort = sortingHelper.getColumnsSort();
			for (ColumnSort columnSort : columnsSort) {
				String columnId = columnSort.getColumnId();
				Field field = fieldMap.get(columnId);
				if (field != null) {
					String sortDirection = columnSort.getSortDirection();
					if (sortDirection.equalsIgnoreCase(SortingHelper.DESCENDING)) {
						Order order = criteriaBuilder.desc(root.get(field.getName()));
						orders.add(order);
					} else {
						Order order = criteriaBuilder.asc(root.get(field.getName()));
						orders.add(order);
					}
				}
			}
		} else {
			Order order = criteriaBuilder.desc(root.get("id"));
			orders.add(order);
		}
	}

	private Predicate createPredicateForStringInterval(Expression<String> expression, StringInterval stringInterval) {
		Predicate predicate = null;
		String from = stringInterval.getFrom();
		String operator = stringInterval.getOperator();
		if (from != null && !from.trim().isEmpty()) {
			switch (operator) {
			case Operator.EQ:
				predicate = criteriaBuilder.equal(expression, from.toLowerCase());
				break;
			case Operator.NE:
				predicate = criteriaBuilder.notEqual(expression, from.toLowerCase());
				break;
			case Operator.EW:
				predicate = criteriaBuilder.like(expression, from.toLowerCase() + "%");
				break;
			case Operator.SW:
				predicate = criteriaBuilder.like(expression, "%" + from.toLowerCase());
				break;
			case Operator.CT:
				predicate = criteriaBuilder.like(expression, "%" + from.toLowerCase() + "%");
				break;
			case Operator.NCT:
				predicate = criteriaBuilder.notLike(expression, "%" + from.toLowerCase() + "%");
				break;
			default:
				break;
			}
			wherePredicates.add(predicate);
		}
		return predicate;
	}

	private Predicate createPredicateForLongInterval(Expression<Long> expression, LongInterval longInterval) {
		Predicate predicate = null;
		Long from = longInterval.getFrom();
		Long to = longInterval.getTo();
		String operator = longInterval.getOperator();
		if (from != null) {
			switch (operator) {
			case Operator.EQ:
				predicate = criteriaBuilder.equal(expression, from);
				break;
			case Operator.NE:
				predicate = criteriaBuilder.notEqual(expression, from);
				break;
			case Operator.GT:
				predicate = criteriaBuilder.gt(expression, from);
				break;
			case Operator.GE:
				predicate = criteriaBuilder.ge(expression, from);
				break;
			case Operator.LT:
				predicate = criteriaBuilder.lt(expression, from);
				break;
			case Operator.LE:
				predicate = criteriaBuilder.le(expression, from);
				break;
			case Operator.BE:
				if (to != null) {
					predicate = criteriaBuilder.between(expression, from, to);
				}
				break;
			}
			wherePredicates.add(predicate);
		}
		return predicate;
	}

	private Predicate createPredicateForInteger(Expression<Integer> expression, String operator, Integer from,
			Integer to) {
		Predicate predicate = null;
		if (from != null) {
			switch (operator) {
			case Operator.EQ:
				predicate = criteriaBuilder.equal(expression, from);
				break;
			case Operator.NE:
				predicate = criteriaBuilder.notEqual(expression, from);
				break;
			case Operator.GT:
				predicate = criteriaBuilder.gt(expression, from);
				break;
			case Operator.GE:
				predicate = criteriaBuilder.ge(expression, from);
				break;
			case Operator.LT:
				predicate = criteriaBuilder.lt(expression, from);
				break;
			case Operator.LE:
				predicate = criteriaBuilder.le(expression, from);
				break;
			case Operator.BE:
				if (to != null) {
					predicate = criteriaBuilder.between(expression, from, to);
				}
				break;
			}
			wherePredicates.add(predicate);
		}
		return predicate;
	}

	private Predicate createPredicateForBigDecimalInterval(Expression<BigDecimal> expression,
			DecimalInterval decimalInterval) {
		Predicate predicate = null;
		BigDecimal from = decimalInterval.getFrom();
		BigDecimal to = decimalInterval.getTo();
		String operator = decimalInterval.getOperator();
		if (from != null) {
			switch (operator) {
			case Operator.EQ:
				predicate = criteriaBuilder.equal(expression, from);
				break;
			case Operator.NE:
				predicate = criteriaBuilder.notEqual(expression, from);
				break;
			case Operator.GT:
				predicate = criteriaBuilder.gt(expression, from);
				break;
			case Operator.GE:
				predicate = criteriaBuilder.ge(expression, from);
				break;
			case Operator.LT:
				predicate = criteriaBuilder.lt(expression, from);
				break;
			case Operator.LE:
				predicate = criteriaBuilder.le(expression, from);
				break;
			case Operator.BE:
				if (to != null) {
					predicate = criteriaBuilder.between(expression, from, to);
				}
				break;
			}
			wherePredicates.add(predicate);
		}
		return predicate;
	}

	private Predicate createPredicateForDateInterval(Expression<LocalDate> expression, DateInterval dateInterval) {
		Predicate predicate = null;
		LocalDate from = dateInterval.getFrom();
		LocalDate to = dateInterval.getTo();
		String operator = dateInterval.getOperator();
		if (from != null) {
			switch (operator) {
			case Operator.EQ:
				predicate = criteriaBuilder.equal(expression, from);
				break;
			case Operator.NE:
				predicate = criteriaBuilder.notEqual(expression, from);
				break;
			case Operator.GT:
				predicate = criteriaBuilder.greaterThan(expression, from);
				break;
			case Operator.GE:
				predicate = criteriaBuilder.greaterThanOrEqualTo(expression, from);
				break;
			case Operator.LT:
				predicate = criteriaBuilder.lessThan(expression, from);
				break;
			case Operator.LE:
				predicate = criteriaBuilder.lessThanOrEqualTo(expression, from);
				break;
			case Operator.BE:
				if (to != null) {
					predicate = criteriaBuilder.between(expression, from, to);
				}
				break;
			}
			wherePredicates.add(predicate);
		}
		return predicate;

	}

	private Predicate createPredicateForDateTruncTimeInterval(Expression<LocalDateTime> expression,
			DateInterval dateInterval) {
		Predicate predicate = null;
		LocalDate from = dateInterval.getFrom();
		LocalDate to = dateInterval.getTo();
		LocalDateTime dateTimeFrom = LocalDateTime.from(from.atStartOfDay()).withHour(0).withMinute(0).withSecond(0)
				.withNano(0);
		String operator = dateInterval.getOperator();
		if (from != null) {
			switch (operator) {
			case Operator.EQ:
				predicate = criteriaBuilder.between(expression, dateTimeFrom, dateTimeFrom.plusDays(1));
				break;
			case Operator.NE:
				predicate = criteriaBuilder
						.not(criteriaBuilder.between(expression, dateTimeFrom, dateTimeFrom.plusDays(1)));
				break;
			case Operator.GT:
				predicate = criteriaBuilder.greaterThan(expression, dateTimeFrom.plusDays(1));
				break;
			case Operator.GE:
				predicate = criteriaBuilder.greaterThanOrEqualTo(expression, dateTimeFrom);
				break;
			case Operator.LT:
				predicate = criteriaBuilder.lessThan(expression, dateTimeFrom);
				break;
			case Operator.LE:
				predicate = criteriaBuilder.lessThanOrEqualTo(expression, dateTimeFrom.plusDays(1));
				break;
			case Operator.BE:
				if (to != null) {
					LocalDateTime dateTimeTo = LocalDateTime.from(to.atStartOfDay()).withHour(0).withMinute(0)
							.withSecond(0).withNano(0);
					predicate = criteriaBuilder.between(expression, dateTimeFrom, dateTimeTo.plusDays(1));
				}
				break;
			}
			wherePredicates.add(predicate);
		}
		return predicate;
	}

	private Predicate createPredicateForDateTimeInterval(Expression<LocalDateTime> expression,
			DateTimeInterval dateTimeInterval) {
		Predicate predicate = null;
		LocalDateTime from = dateTimeInterval.getFrom();
		LocalDateTime to = dateTimeInterval.getTo();
		String operator = dateTimeInterval.getOperator();
		if (from != null) {
			switch (operator) {
			case Operator.EQ:
				predicate = criteriaBuilder.equal(expression, from);
				break;
			case Operator.NE:
				predicate = criteriaBuilder.notEqual(expression, from);
				break;
			case Operator.GT:
				predicate = criteriaBuilder.greaterThan(expression, from);
				break;
			case Operator.GE:
				predicate = criteriaBuilder.greaterThanOrEqualTo(expression, from);
				break;
			case Operator.LT:
				predicate = criteriaBuilder.lessThan(expression, from);
				break;
			case Operator.LE:
				predicate = criteriaBuilder.lessThanOrEqualTo(expression, from);
				break;
			case Operator.BE:
				if (to != null) {
					predicate = criteriaBuilder.between(expression, from, to);
				}
				break;
			}
			wherePredicates.add(predicate);
		}
		return predicate;
	}

	private void updateQueryValuesMap(Field field, Object vo) throws IllegalArgumentException, IllegalAccessException {
		field.setAccessible(true);
		Object fieldValue = field.get(vo);
		if (fieldValue instanceof Interval) {
			Interval<?> interval = (Interval<?>) fieldValue;
			Object object = interval.getFrom();
			if (object != null) {
				FilterFieldProperty filterFieldProperty = field.getAnnotation(FilterFieldProperty.class);
				queryValuesMap.put(filterFieldProperty.fieldProperty(), interval);
			}
		} else {
			if (fieldValue != null) {
				FilterFieldProperty filterFieldProperty = field.getAnnotation(FilterFieldProperty.class);
				queryValuesMap.put(filterFieldProperty.fieldProperty(), fieldValue);
			}
		}
	}

	private void determineWherePredicate(Path path, Field entityDeclaredField, String fieldProperty) {
		Object object = queryValuesMap.get(fieldProperty);
		if (object != null) {
			if (object instanceof Interval) {
				if (object instanceof StringInterval) {
					StringInterval stringInterval = (StringInterval) object;
					createPredicateForStringInterval(path, stringInterval);
				} else if (object instanceof LongInterval) {
					LongInterval longInterval = (LongInterval) object;
					createPredicateForLongInterval(path, longInterval);
				} else if (object instanceof DecimalInterval) {
					DecimalInterval decimalInterval = (DecimalInterval) object;
					createPredicateForBigDecimalInterval(path, decimalInterval);
				} else if (object instanceof DateInterval) {
					if (entityDeclaredField.getType().isAssignableFrom(LocalDateTime.class)) {
						DateInterval dateInterval = (DateInterval) object;
						createPredicateForDateTruncTimeInterval(path, dateInterval);
					} else {
						DateInterval dateInterval = (DateInterval) object;
						createPredicateForDateInterval(path, dateInterval);
					}

				} else if (object instanceof DateTimeInterval) {
					DateTimeInterval dateTimeInterval = (DateTimeInterval) object;
					createPredicateForDateTimeInterval(path, dateTimeInterval);
				}
			} else {
				Predicate predicate = criteriaBuilder.equal(path, object);
				wherePredicates.add(predicate);
			}
		}
	}

	private void handleForJoin(Root root, Field entityDeclaredField, String fieldProperty,
			String referencedFieldProperty) {
		if (!joinMap.containsKey(entityDeclaredField.getName())) {
			Join<Object, Object> join = root.join(entityDeclaredField.getName(), JoinType.LEFT);
			joinMap.put(entityDeclaredField.getName(), join);

			// handleSoftDeleteCriteria(join);
		}
		Join join = joinMap.get(entityDeclaredField.getName());
		Class<?> type = entityDeclaredField.getType();
		Field[] declaredFields = type.getDeclaredFields();

		for (Field declaredField : declaredFields) {
			if (declaredField.isAnnotationPresent(FilterFieldProperty.class)) {
				FilterFieldProperty filterFieldProperty = declaredField.getAnnotation(FilterFieldProperty.class);

				if (referencedFieldProperty == null || referencedFieldProperty.trim().isEmpty()) {
					if (fieldProperty.equalsIgnoreCase(filterFieldProperty.fieldProperty())) {
						Path path = join.get(declaredField.getName());
						determineWherePredicate(path, declaredField, fieldProperty);
					}
				} else {
					if (referencedFieldProperty.equalsIgnoreCase(filterFieldProperty.fieldProperty())) {
						Path path = join.get(declaredField.getName());
						determineWherePredicate(path, declaredField, fieldProperty);
					}
				}
			}
		}
	}

	private void determineWherePredicates(Root<?> root) throws IllegalArgumentException, IllegalAccessException {

		Field[] entityDeclaredFields = entity.getDeclaredFields();
		for (Field entityDeclaredField : entityDeclaredFields) {
			if (entityDeclaredField.isAnnotationPresent(FilterFieldProperty.class)) {
				FilterFieldProperty filterFieldProperty = entityDeclaredField.getAnnotation(FilterFieldProperty.class);
				if (entityDeclaredField.isAnnotationPresent(Column.class)) {
					Path path = root.get(entityDeclaredField.getName());
					determineWherePredicate(path, entityDeclaredField, filterFieldProperty.fieldProperty());
				} else if (entityDeclaredField.isAnnotationPresent(ManyToOne.class)) {
					handleForJoin(root, entityDeclaredField, filterFieldProperty.fieldProperty(),
							filterFieldProperty.referencedFieldProperty());
				}
			} else if (entityDeclaredField.isAnnotationPresent(FilterFieldProperties.class)) {
				FilterFieldProperties filterFieldProperties = entityDeclaredField
						.getAnnotation(FilterFieldProperties.class);
				FilterFieldProperty[] fieldProperties = filterFieldProperties.fieldProperties();
				for (FilterFieldProperty filterFieldProperty : fieldProperties) {
					if (entityDeclaredField.isAnnotationPresent(Column.class)) {
						Path path = root.get(entityDeclaredField.getName());
						determineWherePredicate(path, entityDeclaredField, filterFieldProperty.fieldProperty());
					} else if (entityDeclaredField.isAnnotationPresent(ManyToOne.class)) {
						handleForJoin(root, entityDeclaredField, filterFieldProperty.fieldProperty(),
								filterFieldProperty.referencedFieldProperty());
					}
					// handleForJoin(root, entityDeclaredField,
					// filterFieldProperty.fieldProperty(),
					// filterFieldProperty.referencedFieldProperty());
				}
			}
		}
	}

	public JoinLeftBuilder getJoinLeftBuilder() {
		return joinLeftBuilder;
	}

	public void setJoinLeftBuilder(JoinLeftBuilder joinLeftBuilder) {
		this.joinLeftBuilder = joinLeftBuilder;
	}

}
