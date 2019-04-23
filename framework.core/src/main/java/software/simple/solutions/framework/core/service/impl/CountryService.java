package software.simple.solutions.framework.core.service.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.repository.ICountryRepository;
import software.simple.solutions.framework.core.service.ICountryService;

@Transactional
@Service
@ServiceRepository(claz = ICountryRepository.class)
public class CountryService extends SuperService implements ICountryService {

}
