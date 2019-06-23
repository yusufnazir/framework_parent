package software.simple.solutions.framework.core.web;

import java.util.List;

import com.vaadin.ui.UI;

import software.simple.solutions.framework.core.components.AbstractBaseView;
import software.simple.solutions.framework.core.components.SessionHolder;
import software.simple.solutions.framework.core.components.ViewDetail;
import software.simple.solutions.framework.core.constants.ActionState;
import software.simple.solutions.framework.core.entities.Menu;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.facade.RoleViewPrivilegeServiceFacade;
import software.simple.solutions.framework.core.service.facade.SubMenuServiceFacade;

public class ViewUtil {

	public static AbstractBaseView initView(Class<?> classToBe, Long roleId) throws FrameworkException {
		AbstractBaseView view = null;
		try {
			view = (AbstractBaseView) classToBe.newInstance();
			ViewDetail viewDetail = view.getViewDetail();
			view.setViewDetail(viewDetail);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return view;
	}

	public static AbstractBaseView initView(String className, Long roleId) throws FrameworkException {
		try {
			Class<?> classToBe = Class.forName(className);
			return initView(classToBe, roleId);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static AbstractBaseView initView(SimpleSolutionsMenuItem menuItem, Long roleId) throws FrameworkException {
		return initView(menuItem, false, roleId);
	}

	public static AbstractBaseView initView(SimpleSolutionsMenuItem menuItem, boolean popUpMode, Long roleId)
			throws FrameworkException {
		String viewClassName = menuItem.getMenu().getView().getViewClassName();
		AbstractBaseView view = initView(viewClassName, roleId);
		if (view != null) {
			view.setSearchForward(menuItem.getSearchedEntity());
			view.setPopUpMode(popUpMode);

			List<String> privileges = RoleViewPrivilegeServiceFacade.get(UI.getCurrent())
					.getPrivilegesByViewIdAndRoleId(menuItem.getMenu().getView().getId(), roleId);
			ViewDetail viewDetail = view.getViewDetail();
			viewDetail.setPrivileges(privileges);

			viewDetail.setMenu(menuItem.getMenu());
			viewDetail.setView(menuItem.getMenu().getView());

			SessionHolder sessionHolder = (SessionHolder) UI.getCurrent().getData();
			ActionState actionState = ViewActionStateUtil.createActionState(viewDetail.getPrivileges(),
					menuItem.getMenu().getView().getId(), sessionHolder.getApplicationUser().getId());
			viewDetail.setActionState(actionState);

			List<Menu> subMenus = getSubMenus(menuItem.getMenu().getId());
			viewDetail.setSubMenus(subMenus);

			view.setViewDetail(viewDetail);
			if(menuItem.getParentEntity()!=null){
				view.setParentEntity(menuItem.getParentEntity());
			}
			view.executeBuild();
			if (view instanceof BasicTemplate) {
				((BasicTemplate) view).executePostRenderActions();
			}
		}
		return view;
	}

	private static List<Menu> getSubMenus(Long parentMenuId) throws FrameworkException {
		List<Menu> subMenus = SubMenuServiceFacade.get(UI.getCurrent()).findTabMenus(parentMenuId);
		return subMenus;
	}

}
