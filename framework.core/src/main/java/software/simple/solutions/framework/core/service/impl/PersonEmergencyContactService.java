package software.simple.solutions.framework.core.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.repository.IPersonEmergencyContactRepository;
import software.simple.solutions.framework.core.service.IPersonEmergencyContactService;

@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Service
@ServiceRepository(claz = IPersonEmergencyContactRepository.class)
public class PersonEmergencyContactService extends SuperService implements IPersonEmergencyContactService {

	private static final long serialVersionUID = -6850621216490458638L;

}
