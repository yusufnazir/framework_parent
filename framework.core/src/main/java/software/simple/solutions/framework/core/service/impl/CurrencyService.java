package software.simple.solutions.framework.core.service.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.repository.ICurrencyRepository;
import software.simple.solutions.framework.core.service.ICurrencyService;

@Transactional
@Service
@ServiceRepository(claz = ICurrencyRepository.class)
public class CurrencyService extends SuperService implements ICurrencyService {

}
