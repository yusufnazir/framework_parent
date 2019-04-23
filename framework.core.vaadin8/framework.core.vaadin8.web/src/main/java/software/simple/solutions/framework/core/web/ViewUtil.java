package software.simple.solutions.framework.core.web;

import java.util.List;

import com.vaadin.ui.UI;

import software.simple.solutions.framework.core.annotations.SupportedPrivileges;
import software.simple.solutions.framework.core.components.AbstractBaseView;
import software.simple.solutions.framework.core.components.SessionHolder;
import software.simple.solutions.framework.core.components.ViewDetail;
import software.simple.solutions.framework.core.constants.ActionState;
import software.simple.solutions.framework.core.entities.Menu;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.ISubMenuService;
import software.simple.solutions.framework.core.util.ContextProvider;

public class ViewUtil {

	public static AbstractBaseView initView(Class<?> classToBe) throws FrameworkException {
		AbstractBaseView view = null;
		try {
			view = (AbstractBaseView) classToBe.newInstance();
			ViewDetail viewDetail = view.getViewDetail();
			view.setViewDetail(viewDetail);

			if (classToBe.isAnnotationPresent(SupportedPrivileges.class)) {
				SupportedPrivileges supportedPrivileges = classToBe.getAnnotation(SupportedPrivileges.class);
				viewDetail.addPrivileges(supportedPrivileges.privileges());
				viewDetail.addPrivileges(supportedPrivileges.extraPrivileges());
			}

		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return view;
	}

	public static AbstractBaseView initView(String className) throws FrameworkException {
		try {
			Class<?> classToBe = Class.forName(className);
			return initView(classToBe);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static AbstractBaseView initView(SimpleSolutionsMenuItem menuItem) throws FrameworkException {
		return initView(menuItem, false);
	}

	public static AbstractBaseView initView(SimpleSolutionsMenuItem menuItem, boolean popUpMode)
			throws FrameworkException {
		String viewClassName = menuItem.getMenu().getView().getViewClassName();
		AbstractBaseView view = initView(viewClassName);
		if (view != null) {
			view.setSearchForward(menuItem.getSearchedEntity());
			view.setPopUpMode(popUpMode);
			ViewDetail viewDetail = view.getViewDetail();
			viewDetail.setMenu(menuItem.getMenu());
			viewDetail.setView(menuItem.getMenu().getView());

			SessionHolder sessionHolder = (SessionHolder) UI.getCurrent().getData();
			ActionState actionState = ViewActionStateUtil.createActionState(viewDetail.getPrivileges(),
					menuItem.getMenu().getView().getId(), sessionHolder.getApplicationUser().getId());
			viewDetail.setActionState(actionState);

			List<Menu> subMenus = getSubMenus(menuItem.getMenu().getId());
			viewDetail.setSubMenus(subMenus);

			view.setViewDetail(viewDetail);
			view.executeBuild();
			if (view instanceof BasicTemplate) {
				((BasicTemplate) view).executePostRenderActions();
			}
		}
		return view;
	}

	private static List<Menu> getSubMenus(Long parentMenuId) throws FrameworkException {
		ISubMenuService subMenuService = ContextProvider.getBean(ISubMenuService.class);
		List<Menu> subMenus = subMenuService.findTabMenus(parentMenuId);
		return subMenus;
	}

}
