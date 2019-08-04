package software.simple.solutions.framework.core.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.entities.Language;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.ILanguageRepository;
import software.simple.solutions.framework.core.service.ILanguageService;
import software.simple.solutions.framework.core.valueobjects.LanguageVO;
import software.simple.solutions.framework.core.valueobjects.SuperVO;

@Transactional(propagation=Propagation.REQUIRED, rollbackFor = Exception.class)
@Service
@ServiceRepository(claz = ILanguageRepository.class)
public class LanguageService extends SuperService implements ILanguageService {

	@Override
	public <T, R extends SuperVO> T updateSingle(R valueObject) throws FrameworkException {

		LanguageVO vo = (LanguageVO) valueObject;

		Language language = null;
		if (vo.isNew()) {
			language = new Language();
		} else {
			language = get(Language.class, vo.getId());
		}
		language.setCode(vo.getCode().toUpperCase());
		language.setName(vo.getName());
		language.setDescription(vo.getDescription());
		language.setActive(vo.getActive());

		return (T) saveOrUpdate(language, vo.isNew());
	}
}
