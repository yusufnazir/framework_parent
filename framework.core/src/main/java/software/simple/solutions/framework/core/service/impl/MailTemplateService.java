package software.simple.solutions.framework.core.service.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.entities.MailTemplate;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.IMailTemplateRepository;
import software.simple.solutions.framework.core.service.IMailTemplateService;
import software.simple.solutions.framework.core.valueobjects.MailTemplateVO;
import software.simple.solutions.framework.core.valueobjects.SuperVO;

@Transactional
@Service
@ServiceRepository(claz = IMailTemplateRepository.class)
public class MailTemplateService extends SuperService implements IMailTemplateService {

	@Override
	public <T, R extends SuperVO> T updateSingle(R entityVO) throws FrameworkException {
		MailTemplateVO vo = (MailTemplateVO) entityVO;

		MailTemplate mailTemplate = getById(MailTemplate.class, vo.getId());
		boolean isNew = mailTemplate ==null;
		if (mailTemplate == null) {
			mailTemplate = new MailTemplate();
		}
		mailTemplate.setCode(vo.getCode());
		mailTemplate.setCode(vo.getName());
		mailTemplate.setCode(vo.getDescription());
		mailTemplate.setActive(vo.getActive());

		mailTemplate.setSubject(vo.getSubject());
		mailTemplate.setMessage(vo.getMessage());

		mailTemplate = saveOrUpdate(mailTemplate, isNew);

		return (T) mailTemplate;
	}

}
