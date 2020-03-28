package software.simple.solutions.framework.core.repository.impl;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Repository;

import software.simple.solutions.framework.core.constants.ReferenceKey;
import software.simple.solutions.framework.core.entities.PropertyPerLocale;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.IPropertyPerLocaleRepository;

@Repository
public class PropertyPerLocaleRepository extends GenericRepository implements IPropertyPerLocaleRepository {

	private static final long serialVersionUID = 9083595163788847433L;

	@Override
	public List<PropertyPerLocale> findAll() throws FrameworkException {
		return createListQuery("from PropertyPerLocale");
	}

	@Override
	public List<PropertyPerLocale> findAllButProperty() throws FrameworkException {
		String query = "from PropertyPerLocale where referenceKey!=:referenceKey";
		ConcurrentMap<String, Object> paramMap = createParamMap();
		paramMap.put("referenceKey", ReferenceKey.PROPERTY);
		return createListQuery(query, paramMap);
	}

}
