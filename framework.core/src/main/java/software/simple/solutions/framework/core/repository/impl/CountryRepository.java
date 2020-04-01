package software.simple.solutions.framework.core.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import software.simple.solutions.framework.core.entities.Country;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.repository.ICountryRepository;

@Repository
public class CountryRepository extends GenericRepository implements ICountryRepository {

	private static final long serialVersionUID = 987656873738702380L;

	@Override
	public <T, R> List<R> getForListing(Class<T> cl, List<Long> ids, Boolean active) throws FrameworkException {
		ConcurrentMap<String, Object> paramMap = createParamMap();
		String query = "from Country where 1=1 ";
		if (ids != null && !ids.isEmpty()) {
			query += " or id in (:ids) ";
			paramMap.put("ids", ids);
		}
		if (active != null) {
			query += " and active=:active ";
			paramMap.put("active", active);
		}

		query += " order by name ";

		List<Country> countries = createListQuery(query, paramMap);
		if (countries != null) {
			List<ComboItem> list = countries.stream().map(p -> new ComboItem(p.getId(), p.getAlpha3(), p.getName()))
					.collect(Collectors.toList());
			return (List<R>) list;
		}
		return new ArrayList<R>();
	}

}
