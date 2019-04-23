package software.simple.solutions.framework.core.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.entities.Menu;
import software.simple.solutions.framework.core.entities.SubMenu;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.ISubMenuRepository;
import software.simple.solutions.framework.core.service.ISubMenuService;
import software.simple.solutions.framework.core.valueobjects.SubMenuVO;
import software.simple.solutions.framework.core.valueobjects.SuperVO;

@Transactional
@Service
@ServiceRepository(claz = ISubMenuRepository.class)
public class SubMenuService extends SuperService implements ISubMenuService {

	@Autowired
	private ISubMenuRepository subMenuRepository;

	@Override
	public <T, R extends SuperVO> T updateSingle(R valueObject) throws FrameworkException {
		SubMenuVO vo = (SubMenuVO) valueObject;

		SubMenu subMenu = null;
		if (vo.isNew()) {
			subMenu = new SubMenu();
		} else {
			subMenu = get(SubMenu.class, vo.getId());
		}

		subMenu.setParentMenu(get(Menu.class, vo.getParentMenuId()));

		subMenu.setChildMenu(get(Menu.class, vo.getChildMenuId()));

		subMenu.setActive(vo.getActive());

		return (T) saveOrUpdate(subMenu, vo.isNew());
	}

	@Override
	public <T> List<T> findTabMenus(Long parentMenuId) throws FrameworkException {
		return subMenuRepository.findTabMenus(parentMenuId);
	}

}
