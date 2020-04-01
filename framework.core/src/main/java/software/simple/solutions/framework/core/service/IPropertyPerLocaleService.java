package software.simple.solutions.framework.core.service;

import java.util.List;

import software.simple.solutions.framework.core.entities.PropertyPerLocale;
import software.simple.solutions.framework.core.exceptions.FrameworkException;

public interface IPropertyPerLocaleService extends ISuperService {

	public List<PropertyPerLocale> findAll() throws FrameworkException;

	public List<PropertyPerLocale> findAllButProperty() throws FrameworkException;

	public PropertyPerLocale getByUniqueKeys(Long languageId, String referenceKey, String referenceId)
			throws FrameworkException;

}
