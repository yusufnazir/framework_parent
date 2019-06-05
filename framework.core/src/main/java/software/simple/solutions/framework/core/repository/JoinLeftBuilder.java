package software.simple.solutions.framework.core.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;

import software.simple.solutions.framework.core.exceptions.FrameworkException;

public interface JoinLeftBuilder {

	public CriteriaBuilder build(Root<?> root, CriteriaBuilder criteriaBuilder) throws FrameworkException;
}
