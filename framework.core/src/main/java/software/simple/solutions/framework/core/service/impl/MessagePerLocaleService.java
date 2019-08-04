package software.simple.solutions.framework.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.IMessagePerLocaleRepository;
import software.simple.solutions.framework.core.service.IMessagePerLocaleService;

@Transactional(propagation=Propagation.REQUIRED, rollbackFor = Exception.class)
@Service
@ServiceRepository(claz = IMessagePerLocaleRepository.class)
public class MessagePerLocaleService extends SuperService implements IMessagePerLocaleService {

	@Autowired
	private IMessagePerLocaleRepository messagePerLocaleRepository;

	@Override
	public <T> T getByMessageAndLocale(String messageKey, String language) throws FrameworkException {
		return messagePerLocaleRepository.getByMessageAndLocale(messageKey, language);
	}
}
