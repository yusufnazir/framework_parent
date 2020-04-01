package software.simple.solutions.framework.core.repository.impl;

import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import software.simple.solutions.framework.core.entities.RelationType;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.repository.IRelationTypeRepository;

@Repository
public class RelationTypeRepository extends GenericRepository implements IRelationTypeRepository {

	private static final long serialVersionUID = 3650144229621401907L;

	@Override
	public <T, R> List<R> getForListing(Class<T> cl, Boolean active) throws FrameworkException {
		ConcurrentMap<String, Object> paramMap = createParamMap();

		String query = "from RelationType where 1=1 order by id ";

		List<RelationType> list = createListQuery(query, paramMap);
		return (List<R>) list.stream().map(p -> new ComboItem(p.getId(), p.getCode(), p.getName()))
				.collect(Collectors.toList());
	}
}
