package software.simple.solutions.framework.core.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.entities.Language;
import software.simple.solutions.framework.core.entities.Property;
import software.simple.solutions.framework.core.entities.PropertyPerLocale;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.IPropertyPerLocaleRepository;
import software.simple.solutions.framework.core.service.IPropertyPerLocaleService;
import software.simple.solutions.framework.core.valueobjects.PropertyPerLocaleVO;
import software.simple.solutions.framework.core.valueobjects.SuperVO;

@Transactional
@Service
@ServiceRepository(claz = IPropertyPerLocaleRepository.class)
public class PropertyPerLocaleService extends SuperService implements IPropertyPerLocaleService {

	@Autowired
	private IPropertyPerLocaleRepository propertyPerLocaleRepository;

	@Override
	public List<PropertyPerLocale> findAll() throws FrameworkException {
		return propertyPerLocaleRepository.findAll();
	}

	@Override
	public <T, R extends SuperVO> T updateSingle(R valueObject) throws FrameworkException {

		PropertyPerLocaleVO vo = (PropertyPerLocaleVO) valueObject;

		PropertyPerLocale propertyPerLocale = null;
		if (vo.isNew()) {
			propertyPerLocale = new PropertyPerLocale();
		} else {
			propertyPerLocale = get(PropertyPerLocale.class, vo.getId());
		}
		propertyPerLocale.setLanguage(get(Language.class, vo.getLanguageId()));

		Property property = createKeyifNotExists(vo.getProperty());

		propertyPerLocale.setProperty(property);
		propertyPerLocale.setValue(vo.getValue());
		propertyPerLocale.setActive(vo.getActive());

		return (T) saveOrUpdate(propertyPerLocale, vo.isNew());
	}

}
