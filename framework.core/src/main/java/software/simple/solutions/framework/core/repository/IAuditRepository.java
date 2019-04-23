package software.simple.solutions.framework.core.repository;

import software.simple.solutions.framework.core.pojo.PagingResult;
import software.simple.solutions.framework.core.pojo.PagingSetting;

public interface IAuditRepository extends IGenericRepository {

	<T, A> PagingResult<A> createAuditQuery(Class<T> cl, Long id, PagingSetting pagingSetting);

}
