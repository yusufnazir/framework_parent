package software.simple.solutions.framework.core.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.repository.IMessageRepository;
import software.simple.solutions.framework.core.service.IMessageService;

@Transactional(propagation=Propagation.REQUIRED, rollbackFor = Exception.class)
@Service
@ServiceRepository(claz = IMessageRepository.class)
public class MessageService extends SuperService implements IMessageService {

}
