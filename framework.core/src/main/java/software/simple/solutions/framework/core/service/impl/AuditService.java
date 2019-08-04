package software.simple.solutions.framework.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.pojo.PagingResult;
import software.simple.solutions.framework.core.pojo.PagingSetting;
import software.simple.solutions.framework.core.repository.IAuditRepository;
import software.simple.solutions.framework.core.service.IAuditService;

@Transactional(propagation=Propagation.REQUIRED, rollbackFor = Exception.class)
@Service
@ServiceRepository(claz = IAuditRepository.class)
public class AuditService extends SuperService implements IAuditService {

	private static final long serialVersionUID = 7358543794266926766L;
	@Autowired
	private IAuditRepository auditRepository;

	@Override
	public <T, A> PagingResult<A> createAuditQuery(Class<T> cl, Long id, PagingSetting pagingSetting) {
		return auditRepository.createAuditQuery(cl, id, pagingSetting);
	}

}
