package software.simple.solutions.framework.core.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.entities.Menu;
import software.simple.solutions.framework.core.entities.View;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.IMenuRepository;
import software.simple.solutions.framework.core.service.IMenuService;
import software.simple.solutions.framework.core.valueobjects.MenuVO;
import software.simple.solutions.framework.core.valueobjects.SuperVO;

@Transactional
@Service
@ServiceRepository(claz = IMenuRepository.class)
public class MenuService extends SuperService implements IMenuService {

	@Autowired
	private IMenuRepository menuRepository;

	@Override
	public <T, R extends SuperVO> T updateSingle(R valueObject) throws FrameworkException {

		MenuVO vo = (MenuVO) valueObject;

		Menu menu = null;
		if (vo.isNew()) {
			menu = new Menu();
		} else {
			menu = get(Menu.class, vo.getId());
		}
		menu.setCode(vo.getCode().toUpperCase());
		menu.setName(vo.getName());
		menu.setActive(vo.getActive());
		menu.setType(vo.getType());
		menu.setView(get(View.class, vo.getViewId()));
		menu.setKey(vo.getKey());

		createKeyifNotExists(vo.getKey());

		return (T) saveOrUpdate(menu, vo.isNew());
	}

	@Override
	public List<Menu> listChildMenus(Long parentId) throws FrameworkException {
		return menuRepository.listChildMenus(parentId);
	}

	@Override
	public List<Menu> listParentMenus() throws FrameworkException {
		return menuRepository.listParentMenus();
	}

	@Override
	public List<Menu> findAuthorizedMenus(Long roleId) throws FrameworkException {
		return menuRepository.findAuthorizedMenus(roleId);
	}

	@Override
	public List<Menu> findTabMenus(Long parentMenuId) throws FrameworkException {
		return menuRepository.findTabMenus(parentMenuId);
	}

	@Override
	public Menu getLookUpByViewClass(Long roleId, String name) throws FrameworkException {
		return menuRepository.getLookUpByViewClass(roleId, name);
	}

}
