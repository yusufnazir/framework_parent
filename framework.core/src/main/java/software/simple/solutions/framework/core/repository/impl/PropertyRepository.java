package software.simple.solutions.framework.core.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import software.simple.solutions.framework.core.entities.Property;
import software.simple.solutions.framework.core.entities.PropertyPerLocale;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.IPropertyRepository;
import software.simple.solutions.framework.core.util.ClassUtil;

@Repository
public class PropertyRepository extends GenericRepository implements IPropertyRepository {

	private static final long serialVersionUID = 5813281463073452367L;

	@Override
	public Map<Property, Map<String, PropertyPerLocale>> findAll() throws FrameworkException {
		List<Object[]> result = createListQuery("select pr,ppl from Property pr "
				+ "left join PropertyPerLocale ppl on ppl.referenceId=cast(pr.id as string)");
		if (result == null || result.isEmpty()) {
			return new HashMap();
		}
		Map<Property, Map<String, PropertyPerLocale>> map = new HashMap();
		for (Object[] ar : result) {
			Property property = ClassUtil.getEntity(ar, Property.class);
			if (!map.containsKey(property)) {
				map.put(property, new HashMap());
			}
			PropertyPerLocale propertyPerLocale = ClassUtil.getEntity(ar, PropertyPerLocale.class);
			Map<String, PropertyPerLocale> pplMap = map.get(property);
			if (propertyPerLocale != null && !pplMap.containsKey(propertyPerLocale.getLanguage().getCode())) {
				pplMap.put(propertyPerLocale.getLanguage().getCode(), propertyPerLocale);
			}
		}

		return map;
	}

}
