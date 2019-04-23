package software.simple.solutions.framework.core.repository.impl;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Repository;

import software.simple.solutions.framework.core.constants.MenuType;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.PagingSetting;
import software.simple.solutions.framework.core.repository.ISubMenuRepository;
import software.simple.solutions.framework.core.util.OrderMapBuilder;
import software.simple.solutions.framework.core.valueobjects.SubMenuVO;

@Repository
public class SubMenuRepository extends GenericRepository implements ISubMenuRepository {

	@Override
	public String createSearchQuery(Object o, ConcurrentMap<String, Object> paramMap, PagingSetting pagingSetting)
			throws FrameworkException {
		SubMenuVO vo = (SubMenuVO) o;
		String query = "select entity from SubMenu entity where 1=1 ";

		if (vo.getParentMenuId() != null) {
			query += buildIdFragment(paramMap, vo.getParentMenuId(), "entity.parentMenu.id");
		}

		if (vo.getChildMenuId() != null) {
			query += buildIdFragment(paramMap, vo.getChildMenuId(), "entity.childMenu.id");
		}

		query += buildBooleanFragment(paramMap, vo.getActive(), "entity.active");

		query += getOrderBy(vo.getSortingHelper(), "entity.parentMenu.id, entity.childMenu.id",
				OrderMapBuilder.build("1", "entity.parentMenu.id", "2", "entity.childMenu.id", "3", "entity.active"));
		return query;
	}

	@Override
	public <T> List<T> findTabMenus(Long parentMenuId) throws FrameworkException {
		String query = "select sm.childMenu from SubMenu sm where sm.parentMenu.id=:parentMenuId and sm.childMenu.type=:type";
		ConcurrentMap<String, Object> paramMap = createParamMap();
		paramMap.put("parentMenuId", parentMenuId);
		paramMap.put("type", MenuType.TAB);
		return createListQuery(query, paramMap);
	}

}
