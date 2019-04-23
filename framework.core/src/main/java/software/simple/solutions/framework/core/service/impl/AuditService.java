package software.simple.solutions.framework.core.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.pojo.PagingResult;
import software.simple.solutions.framework.core.pojo.PagingSetting;
import software.simple.solutions.framework.core.repository.IAuditRepository;
import software.simple.solutions.framework.core.service.IAuditService;

@Transactional
@Service
@ServiceRepository(claz = IAuditRepository.class)
public class AuditService extends SuperService implements IAuditService {

	@Autowired
	private IAuditRepository auditRepository;

	@Override
	public <T, A> PagingResult<A> createAuditQuery(Class<T> cl, Long id, PagingSetting pagingSetting) {
		return auditRepository.createAuditQuery(cl, id, pagingSetting);
	}

}
