package software.simple.solutions.framework.core.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.repository.ICountryRepository;
import software.simple.solutions.framework.core.service.ICountryService;

@Transactional(propagation=Propagation.REQUIRED, rollbackFor = Exception.class)
@Service
@ServiceRepository(claz = ICountryRepository.class)
public class CountryService extends SuperService implements ICountryService {

	private static final long serialVersionUID = -1080525256146667917L;

}
