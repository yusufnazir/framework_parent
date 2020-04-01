package software.simple.solutions.framework.core.service.impl;

import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.config.PropertyHolder;
import software.simple.solutions.framework.core.constants.MailTemplateReference;
import software.simple.solutions.framework.core.constants.ReferenceKey;
import software.simple.solutions.framework.core.entities.MailTemplate;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.IMailTemplateRepository;
import software.simple.solutions.framework.core.service.IMailTemplateService;
import software.simple.solutions.framework.core.service.IPropertyPerLocaleService;
import software.simple.solutions.framework.core.valueobjects.MailTemplateVO;
import software.simple.solutions.framework.core.valueobjects.PropertyPerLocaleVO;
import software.simple.solutions.framework.core.valueobjects.SuperVO;

@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Service
@ServiceRepository(claz = IMailTemplateRepository.class)
public class MailTemplateService extends SuperService implements IMailTemplateService {

	@Autowired
	private IPropertyPerLocaleService propertyPerLocaleService;

	@Override
	public <T, R extends SuperVO> T updateSingle(R entityVO) throws FrameworkException {
		MailTemplateVO vo = (MailTemplateVO) entityVO;

		MailTemplate mailTemplate = getById(MailTemplate.class, vo.getId());
		boolean isNew = mailTemplate == null;
		if (mailTemplate == null) {
			mailTemplate = new MailTemplate();
		}
		if (vo.getLanguageCode().equalsIgnoreCase(Locale.ENGLISH.getISO3Language())) {
			mailTemplate.setCode(vo.getCode());
			mailTemplate.setName(vo.getName());
			mailTemplate.setDescription(vo.getDescription());
			mailTemplate.setActive(vo.getActive());

			mailTemplate.setSubject(vo.getSubject());
			mailTemplate.setMessage(vo.getMessage());
		}

		PropertyPerLocaleVO propertyPerLocaleVO = new PropertyPerLocaleVO();
		propertyPerLocaleVO.setLanguageId(vo.getLanguageId());
		propertyPerLocaleVO.setReferenceKey(ReferenceKey.MAIL_TEMPLATE);
		propertyPerLocaleVO.setActive(true);

		if (StringUtils.isNotBlank(vo.getName())) {
			propertyPerLocaleVO.setReferenceId(MailTemplateReference.NAME + mailTemplate.getId());
			propertyPerLocaleVO.setValue(vo.getName());
			propertyPerLocaleService.updateSingle(propertyPerLocaleVO);
			PropertyHolder.updateKeyValue(ReferenceKey.MAIL_TEMPLATE, vo.getLanguageCode(),
					MailTemplateReference.NAME + mailTemplate.getId(), vo.getName());
		}

		if (StringUtils.isNotBlank(vo.getDescription())) {
			propertyPerLocaleVO.setReferenceId(MailTemplateReference.DESCRIPTION + mailTemplate.getId());
			propertyPerLocaleVO.setValue(vo.getDescription());
			propertyPerLocaleService.updateSingle(propertyPerLocaleVO);
			PropertyHolder.updateKeyValue(ReferenceKey.MAIL_TEMPLATE, vo.getLanguageCode(),
					MailTemplateReference.DESCRIPTION + mailTemplate.getId(), vo.getDescription());
		}

		if (StringUtils.isNotBlank(vo.getSubject())) {
			propertyPerLocaleVO.setReferenceId(MailTemplateReference.SUBJECT + mailTemplate.getId());
			propertyPerLocaleVO.setValue(vo.getSubject());
			propertyPerLocaleService.updateSingle(propertyPerLocaleVO);
			PropertyHolder.updateKeyValue(ReferenceKey.MAIL_TEMPLATE, vo.getLanguageCode(),
					MailTemplateReference.SUBJECT + mailTemplate.getId(), vo.getSubject());
		}

		if (StringUtils.isNotBlank(vo.getMessage())) {
			propertyPerLocaleVO.setReferenceId(MailTemplateReference.MESSAGE + mailTemplate.getId());
			propertyPerLocaleVO.setValue(vo.getMessage());
			propertyPerLocaleService.updateSingle(propertyPerLocaleVO);
			PropertyHolder.updateKeyValue(ReferenceKey.MAIL_TEMPLATE, vo.getLanguageCode(),
					MailTemplateReference.MESSAGE + mailTemplate.getId(), vo.getMessage());
		}

		mailTemplate = saveOrUpdate(mailTemplate, isNew);

		return (T) mailTemplate;
	}

}
