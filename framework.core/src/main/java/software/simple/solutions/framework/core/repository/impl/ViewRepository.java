package software.simple.solutions.framework.core.repository.impl;

import java.util.concurrent.ConcurrentMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import software.simple.solutions.framework.core.entities.View;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.IViewRepository;

@Repository
public class ViewRepository extends GenericRepository implements IViewRepository {

	public static final Logger logger = LogManager.getLogger(ViewRepository.class);

	@Override
	public View getByClassName(String className) throws FrameworkException {
		String query = "from View where lower(viewClassName)=lower(:viewClassName)";
		ConcurrentMap<String, Object> paramMap = createParamMap();
		logger.info("ViewClassName [" + className + "]");
		paramMap.put("viewClassName", className);
		return getByQuery(query, paramMap);
	}

	// @Override
	// public String createSearchQuery(Object o, ConcurrentMap<String, Object>
	// paramMap, PagingSetting pagingSetting)
	// throws FrameworkException {
	// ViewVO vo = (ViewVO) o;
	// String query = "select entity from View entity where 1=1 ";
	//
	// query += buildStringFragment(paramMap, vo.getCodeInterval(),
	// "entity.code");
	// query += buildStringFragment(paramMap, vo.getNameInterval(),
	// "entity.name");
	// query += buildStringFragment(paramMap, vo.getViewClassNameInterval(),
	// "entity.viewClassName");
	// query += buildBooleanFragment(paramMap, vo.getActive(), "entity.active");
	//
	// query += getOrderBy(vo.getSortingHelper(), "entity.code",
	// OrderMapBuilder.build("0", "entity.code", "1",
	// "entity.name", "2", "entity.viewClassName", "3", "entity.active"));
	// return query;
	// }

}
