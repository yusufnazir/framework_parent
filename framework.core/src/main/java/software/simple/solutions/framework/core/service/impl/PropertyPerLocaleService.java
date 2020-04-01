package software.simple.solutions.framework.core.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.entities.Language;
import software.simple.solutions.framework.core.entities.PropertyPerLocale;
import software.simple.solutions.framework.core.exceptions.Arg;
import software.simple.solutions.framework.core.exceptions.ExceptionBuilder;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.PropertyPerLocaleProperty;
import software.simple.solutions.framework.core.properties.SystemMessageProperty;
import software.simple.solutions.framework.core.repository.IPropertyPerLocaleRepository;
import software.simple.solutions.framework.core.service.IPropertyPerLocaleService;
import software.simple.solutions.framework.core.valueobjects.PropertyPerLocaleVO;
import software.simple.solutions.framework.core.valueobjects.SuperVO;

@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Service
@ServiceRepository(claz = IPropertyPerLocaleRepository.class)
public class PropertyPerLocaleService extends SuperService implements IPropertyPerLocaleService {

	private static final long serialVersionUID = 1828017010040460816L;

	@Autowired
	private IPropertyPerLocaleRepository propertyPerLocaleRepository;

	@Override
	public List<PropertyPerLocale> findAll() throws FrameworkException {
		return propertyPerLocaleRepository.findAll();
	}

	@Override
	public <T, R extends SuperVO> T updateSingle(R valueObject) throws FrameworkException {

		PropertyPerLocaleVO vo = (PropertyPerLocaleVO) valueObject;

		if (StringUtils.isBlank(vo.getReferenceKey())) {
			throw ExceptionBuilder.FRAMEWORK_EXCEPTION.build(SystemMessageProperty.FIELD_IS_REQUIRED,
					Arg.build().key(PropertyPerLocaleProperty.REFERENCE_KEY));
		}

		if (StringUtils.isBlank(vo.getReferenceId())) {
			throw ExceptionBuilder.FRAMEWORK_EXCEPTION.build(SystemMessageProperty.FIELD_IS_REQUIRED,
					Arg.build().key(PropertyPerLocaleProperty.REFERENCE_ID));
		}

		if (vo.getLanguageId() == null) {
			throw ExceptionBuilder.FRAMEWORK_EXCEPTION.build(SystemMessageProperty.FIELD_IS_REQUIRED,
					Arg.build().key(PropertyPerLocaleProperty.LANGUAGE));
		}

		if (StringUtils.isBlank(vo.getValue())) {
			throw ExceptionBuilder.FRAMEWORK_EXCEPTION.build(SystemMessageProperty.FIELD_IS_REQUIRED,
					Arg.build().key(PropertyPerLocaleProperty.VALUE));
		}

		PropertyPerLocale propertyPerLocale = getByUniqueKeys(vo.getLanguageId(), vo.getReferenceKey(),
				vo.getReferenceId());
		vo.setNew(false);
		if (propertyPerLocale == null) {
			vo.setNew(true);
			propertyPerLocale = new PropertyPerLocale();
			propertyPerLocale.setLanguage(get(Language.class, vo.getLanguageId()));
			propertyPerLocale.setReferenceKey(vo.getReferenceKey());
			propertyPerLocale.setReferenceId(vo.getReferenceId());
		}
		propertyPerLocale.setValue(vo.getValue());
		propertyPerLocale.setActive(vo.getActive());

		return (T) saveOrUpdate(propertyPerLocale, vo.isNew());
	}

	@Override
	public PropertyPerLocale getByUniqueKeys(Long languageId, String referenceKey, String referenceId)
			throws FrameworkException {
		return propertyPerLocaleRepository.getByUniqueKeys(languageId, referenceKey, referenceId);
	}

	@Override
	public List<PropertyPerLocale> findAllButProperty() throws FrameworkException {
		return propertyPerLocaleRepository.findAllButProperty();
	}

}
