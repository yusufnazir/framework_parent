package software.simple.solutions.framework.core.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.repository.IStreetRepository;
import software.simple.solutions.framework.core.service.IStreetService;

@Transactional(propagation=Propagation.REQUIRED, rollbackFor = Exception.class)
@Service
@ServiceRepository(claz = IStreetRepository.class)
public class StreetService extends SuperService implements IStreetService {

}
