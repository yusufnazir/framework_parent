package software.simple.solutions.framework.core.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.entities.GeneratedMail;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.ICurrencyRepository;
import software.simple.solutions.framework.core.service.IGeneratedMailService;
import software.simple.solutions.framework.core.valueobjects.GeneratedMailVO;
import software.simple.solutions.framework.core.valueobjects.SuperVO;

@Transactional(propagation=Propagation.REQUIRED, rollbackFor = Exception.class)
@Service
@ServiceRepository(claz = ICurrencyRepository.class)
public class GeneratedMailService extends SuperService implements IGeneratedMailService {

	@Override
	public <T, R extends SuperVO> T updateSingle(R entityVO) throws FrameworkException {
		GeneratedMailVO vo = (GeneratedMailVO) entityVO;

		GeneratedMail generatedMail = null;
		boolean isNew = vo.getId()==null;
		if (vo.getId() == null) {
			generatedMail = new GeneratedMail();
		} else {
			generatedMail = getById(GeneratedMail.class, vo.getId());
		}

		generatedMail.setActive(true);
		generatedMail.setFrom(vo.getFrom());
		generatedMail.setTo(vo.getTo());
		generatedMail.setSubject(vo.getSubject());
		generatedMail.setMessage(vo.getMessage());
		generatedMail.setState(vo.getState());
		generatedMail.setRetryCount(0L);

		generatedMail = saveOrUpdate(generatedMail, isNew);

		return (T) generatedMail;
	}

}
