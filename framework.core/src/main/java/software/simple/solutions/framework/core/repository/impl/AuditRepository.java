package software.simple.solutions.framework.core.repository.impl;

import java.util.List;

import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.stereotype.Repository;

import software.simple.solutions.framework.core.pojo.PagingResult;
import software.simple.solutions.framework.core.pojo.PagingSetting;
import software.simple.solutions.framework.core.repository.IAuditRepository;

@Repository
public class AuditRepository extends GenericRepository implements IAuditRepository {

	@Override
	public <T, A> PagingResult<A> createAuditQuery(Class<T> cl, Long id, PagingSetting pagingSetting) {

		PagingResult<A> pagingResult = new PagingResult<A>();
		pagingResult.setPagingSetting(pagingSetting);

		Long count = (Long) auditReader.createQuery().forRevisionsOfEntity(cl, false, true)
				.addProjection(AuditEntity.id().count()).getSingleResult();
		pagingResult.setCount(count);

		AuditQuery auditQuery = auditReader.createQuery().forRevisionsOfEntity(cl, false, true)
				.add(AuditEntity.id().eq(id));
		auditQuery.addOrder(AuditEntity.revisionNumber().desc());
		if (pagingSetting != null) {
			if (pagingSetting.getMaxResult() != 0) {
				auditQuery.setMaxResults(pagingSetting.getMaxResult());
				auditQuery.setFirstResult(pagingSetting.getStartPosition());
			}
		}
		List<A> resultList = auditQuery.getResultList();
		pagingResult.setResult(resultList);
		return pagingResult;
	}

}
