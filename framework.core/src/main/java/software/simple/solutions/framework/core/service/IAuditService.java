package software.simple.solutions.framework.core.service;

import software.simple.solutions.framework.core.pojo.PagingResult;
import software.simple.solutions.framework.core.pojo.PagingSetting;

public interface IAuditService extends ISuperService {

	public <T, A> PagingResult<A> createAuditQuery(Class<T> cl, Long id, PagingSetting pagingSetting);

}
