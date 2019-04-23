package software.simple.solutions.framework.core.service.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.repository.IStreetRepository;
import software.simple.solutions.framework.core.service.IStreetService;

@Transactional
@Service
@ServiceRepository(claz = IStreetRepository.class)
public class StreetService extends SuperService implements IStreetService {

}
