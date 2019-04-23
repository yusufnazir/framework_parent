package software.simple.solutions.framework.core.service.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.repository.IMessageRepository;
import software.simple.solutions.framework.core.service.IMessageService;

@Transactional
@Service
@ServiceRepository(claz = IMessageRepository.class)
public class MessageService extends SuperService implements IMessageService {

}
