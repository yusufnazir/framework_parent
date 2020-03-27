package software.simple.solutions.framework.core.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.entities.Property;
import software.simple.solutions.framework.core.entities.PropertyPerLocale;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.IPropertyRepository;
import software.simple.solutions.framework.core.service.IPropertyService;
import software.simple.solutions.framework.core.valueobjects.PropertyVO;
import software.simple.solutions.framework.core.valueobjects.SuperVO;

@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Service
@ServiceRepository(claz = IPropertyRepository.class)
public class PropertyService extends SuperService implements IPropertyService {

	private static final long serialVersionUID = -7978472874915836137L;

	@Autowired
	private IPropertyRepository propertyRepository;

	@Override
	public Map<Property, Map<String, PropertyPerLocale>> findAll() throws FrameworkException {
		return propertyRepository.findAll();
	}

	@Override
	public <T, R extends SuperVO> T updateSingle(R valueObject) throws FrameworkException {

		PropertyVO vo = (PropertyVO) valueObject;

		Property property = null;
		if (vo.isNew()) {
			property = new Property();
		} else {
			property = get(Property.class, vo.getId());
		}
		property.setKey(vo.getKey());
		property.setActive(vo.getActive());

		return (T) saveOrUpdate(property, vo.isNew());
	}

}
