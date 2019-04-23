package software.simple.solutions.framework.core.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import software.simple.solutions.framework.core.entities.PropertyPerLocale;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.IPropertyPerLocaleRepository;

@Repository
public class PropertyPerLocaleRepository extends GenericRepository implements IPropertyPerLocaleRepository {

	@Override
	public List<PropertyPerLocale> findAll() throws FrameworkException {
		return createListQuery("from PropertyPerLocale");
	}

}
