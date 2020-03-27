package software.simple.solutions.framework.core.service;

import java.util.Map;

import software.simple.solutions.framework.core.entities.Property;
import software.simple.solutions.framework.core.entities.PropertyPerLocale;
import software.simple.solutions.framework.core.exceptions.FrameworkException;

public interface IPropertyService extends ISuperService {

	public Map<Property, Map<String, PropertyPerLocale>> findAll() throws FrameworkException;

}
