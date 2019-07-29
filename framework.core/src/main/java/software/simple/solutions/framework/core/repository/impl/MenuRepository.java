package software.simple.solutions.framework.core.repository.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Repository;

import software.simple.solutions.framework.core.constants.MenuType;
import software.simple.solutions.framework.core.entities.Menu;
import software.simple.solutions.framework.core.exceptions.Arg;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.SystemMessageProperty;
import software.simple.solutions.framework.core.repository.IMenuRepository;

@Repository
public class MenuRepository extends GenericRepository implements IMenuRepository {

	public static final String LIST_BY_PARENT_ID = "listByParentId";

	// @Override
	// public String createSearchQuery(Object o, ConcurrentMap<String, Object>
	// paramMap, PagingSetting pagingSetting)
	// throws FrameworkException {
	// MenuVO vo = (MenuVO) o;
	// String query = "select entity from Menu entity where 1=1 ";
	//
	// query += buildStringFragment(paramMap, vo.getCodeInterval(),
	// "entity.code");
	// query += buildStringFragment(paramMap, vo.getNameInterval(),
	// "entity.name");
	// query += buildBooleanFragment(paramMap, vo.getActive(), "entity.active");
	//
	// if (vo.getViewVO() != null) {
	// query += buildIdFragment(paramMap, vo.getViewVO().getId(),
	// "entity.view.id");
	// }
	//
	// query += getOrderBy(vo.getSortingHelper(), "entity.code",
	// OrderMapBuilder.build("0", "entity.code", "1", "entity.name", "3",
	// "entity.view.id"));
	// return query;
	// }

	@Override
	public List<Menu> listParentMenus() throws FrameworkException {
		ConcurrentMap<String, Object> paramMap = createParamMap();
		String query = "select entity from Menu entity where entity.type=:type order by entity.index";
		paramMap.put("type", MenuType.HEAD_MENU);
		return createListQuery(query, paramMap);
	}

	@Override
	public List<Menu> listChildMenus(Long id) throws FrameworkException {
		if (id == null) {
			return null;
		}

		ConcurrentMap<String, Object> paramMap = createParamMap();
		String query = "select entity.childMenu from SubMenu entity where entity.parentMenu.id=:id and entity.childMenu.type=:type order by entity.index";
		paramMap.put("id", id);
		paramMap.put("type", MenuType.CHILD);
		return createListQuery(query, paramMap);
	}

	@Override
	public List<Menu> findAuthorizedMenus(Long roleId) throws FrameworkException {
		ConcurrentMap<String, Object> paramMap = createParamMap();
		String query = "select m from Menu m left join RoleView rv on rv.view.id=m.view.id "
				+ "left join RoleViewPrivilege rvp on rvp.roleView.id=rv.id " + "where m.type in (1,2) "
				+ "and rvp.privilege is not null " + "and rv.role.id=:roleId";
		paramMap.put("roleId", roleId);
		List<Menu> menus = createListQuery(query, paramMap);

		Map<Long, Menu> menuMap = new HashMap<Long, Menu>();
		for (Menu menu : menus) {
			menuMap.put(menu.getId(), menu);
		}
		menuMap.putAll(findUpToParent(menuMap));
		return new ArrayList<Menu>(menuMap.values());
	}

	@Override
	public List<Menu> findAuthorizedMenusByUser(Long applicationUserId) throws FrameworkException {
		ConcurrentMap<String, Object> paramMap = createParamMap();
		String query = "select m from Menu m left join RoleView rv on rv.view.id=m.view.id "
				+ "left join RoleViewPrivilege rvp on rvp.roleView.id=rv.id "
				+ "left join UserRole ur on ur.role=rv.role.id " + "where m.type in (1,2) "
				+ "and rvp.privilege is not null " + "and ur.applicationUser.id=:applicationUserId";
		paramMap.put("applicationUserId", applicationUserId);
		List<Menu> menus = createListQuery(query, paramMap);

		Map<Long, Menu> menuMap = new HashMap<Long, Menu>();
		for (Menu menu : menus) {
			menuMap.put(menu.getId(), menu);
		}
		menuMap.putAll(findUpToParent(menuMap));
		return new ArrayList<Menu>(menuMap.values());
	}

	private Map<Long, Menu> findUpToParent(Map<Long, Menu> menus) throws FrameworkException {
		Map<Long, Menu> parents = new HashMap<Long, Menu>();
		List<Long> ids = new ArrayList<Long>();
		for (Menu menu : menus.values()) {
			if (menu.getParentMenu() != null) {
				ids.add(menu.getParentMenu().getId());
			}
		}
		if (ids.size() > 0) {
			String query = " from Menu m where m.id in :ids";
			ConcurrentMap<String, Object> paramMap = createParamMap();
			paramMap.put("ids", ids);
			List<Menu> pmenus = createListQuery(query, paramMap);
			if (pmenus != null && pmenus.size() > 0) {
				for (Menu menu : pmenus) {
					parents.put(menu.getId(), menu);
				}
				parents.putAll(findUpToParent(parents));
			}
		}
		return parents;
	}

	@Override
	public List<Menu> findTabMenus(Long parentMenuId, Long roleId) throws FrameworkException {
		if (parentMenuId == null) {
			return null;
		}
		String query = "SELECT menus FROM Menu menus " + "LEFT JOIN View views ON views.id=menus.view.id "
				+ "LEFT JOIN RoleView roleviews ON roleviews.view.id=views.id " + "WHERE roleviews.role.id=:roleId "
				+ "and menus.parentMenu.id=:parentMenu and menus.type=:type order by menus.index";
		// String query = " from Menu m where m.parentMenu.id=:parentMenu and
		// m.type=:type order by m.index";
		ConcurrentMap<String, Object> paramMap = createParamMap();
		paramMap.put("parentMenu", parentMenuId);
		paramMap.put("roleId", roleId);
		paramMap.put("type", MenuType.TAB);
		return createListQuery(query, paramMap);
	}

	@Override
	public Menu getLookUpByViewClass(Long roleId, String name) throws FrameworkException {
		String query = "select men from Menu men " + "left join RoleView rv on rv.view.id=men.view.id "
				+ "left join View vie on vie.id=rv.view.id " + "where rv.role.id=:roleId and men.type=4 "
				+ "and vie.viewClassName=:className";
		ConcurrentMap<String, Object> paramMap = createParamMap();
		paramMap.put("roleId", roleId);
		paramMap.put("className", name);
		List<Menu> menus = createListQuery(query, paramMap);
		if (menus == null || menus.isEmpty() || menus.size() > 1) {
			throw new FrameworkException(SystemMessageProperty.INVALID_LOOKUP_FIELD_SETUP,
					new Arg().norm(roleId).norm(name));
		}

		return menus.get(0);
	}

	@Override
	public List<Menu> findAuthorizedMenusByType(Long roleId, List<Long> types) throws FrameworkException {
		ConcurrentMap<String, Object> paramMap = createParamMap();
		String query = "select m from Menu m left join RoleView rv on rv.view.id=m.view.id "
				+ "left join RoleViewPrivilege rvp on rvp.roleView.id=rv.id " + "where m.type in (:types) "
				+ "and rvp.privilege is not null " + "and rv.role.id=:roleId";
		paramMap.put("roleId", roleId);
		paramMap.put("types", types);
		List<Menu> menus = createListQuery(query, paramMap);

		Map<Long, Menu> menuMap = new HashMap<Long, Menu>();
		for (Menu menu : menus) {
			menuMap.put(menu.getId(), menu);
		}
		menuMap.putAll(findUpToParent(menuMap));
		return new ArrayList<Menu>(menuMap.values());
	}

	@Override
	public List<Menu> getPossibleHomeViews() throws FrameworkException {
		ConcurrentMap<String, Object> paramMap = createParamMap();
		String query = "select m from Menu m where m.type in (:types) ";
		paramMap.put("types", MenuType.HOME_MENU_OPTIONS);
		return createListQuery(query, paramMap);
	}

	@Override
	public Boolean doesUserHaveAccess(Long applicationUserId, Long roleId, Long menuId) throws FrameworkException {
		ConcurrentMap<String, Object> paramMap = createParamMap();
		String query = "select m from Menu m left join RoleView rv on rv.view.id=m.view.id "
				+ "left join RoleViewPrivilege rvp on rvp.roleView.id=rv.id " + "where rvp.privilege is not null "
				+ "and rv.role.id=:roleId and m.id=:menuId";
		paramMap.put("roleId", roleId);
		paramMap.put("menuId", menuId);
		Menu menu = getByQuery(query, paramMap);
		return menu != null;
	}

}
